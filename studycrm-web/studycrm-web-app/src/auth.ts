import { Temporal } from '@js-temporal/polyfill';
import NextAuth from 'next-auth';
import { JWT } from 'next-auth/jwt';

// Helper functions for cleaner Temporal usage
const nowInSeconds = (): number => Math.floor(Number(Temporal.Now.instant().epochNanoseconds) / 1_000_000_000);
const SESSION_DURATION = Temporal.Duration.from({ hours: 8 });
const REFRESH_BUFFER = Temporal.Duration.from({ minutes: 5 });

export const {
  handlers: { GET, POST },
  auth,
  signIn,
  signOut,
} = NextAuth({
  providers: [
    {
      id: 'studycrm',
      name: 'StudyCRM',
      type: 'oidc',
      clientId: process.env.STUDYCRM_CLIENT_ID,
      clientSecret: process.env.STUDYCRM_CLIENT_SECRET,
      issuer: process.env.STUDYCRM_ISSUER_BASE_URL,
      authorization: {
        params: {
          scope: 'openid profile',
        },
      },
      checks: ['pkce', 'state', 'nonce'],
      // Configure the end session endpoint for proper OIDC logout
      wellKnown: `${process.env.STUDYCRM_ISSUER_BASE_URL}/.well-known/openid-configuration`,
      client: {
        authorization_signed_response_alg: 'RS256',
      },
      profile(profile) {
        const { roles } = profile;
        const organizationIds =
          roles.match(/org:[\w-]+:\w+?/g)?.map((role) => role.split(':')[1]) ??
          [];
        return {
          id: profile.sub,
          name: profile.name,
          firstName: profile.given_name,
          lastName: profile.family_name,
          email: profile.email,
          organizationIds,
        };
      },
    },
  ],
  secret: process.env.NEXTAUTH_SECRET,
  session: {
    strategy: 'jwt',
    maxAge: SESSION_DURATION.total('seconds'), // 8 hours to match refresh token lifetime
  },
  cookies: {
    pkceCodeVerifier: {
      name: 'next-auth.pkce.code_verifier',
      options: {
        httpOnly: true,
        sameSite: 'lax',
        path: '/',
        secure: false, // Set to true in production with HTTPS
      },
    },
  },
  callbacks: {
    async jwt({ token, account, user }) {
      if (account) {
        // Save the access token and refresh token in the JWT on the initial login
        return {
          user,
          accessToken: account.access_token,
          idToken: account.id_token,
          expiresAt: account.expires_in
            ? nowInSeconds() + Number(account.expires_in)
            : 0,
          refreshToken: account.refresh_token,
        } as JWT;
      } 
      
      // Return previous token if the access token has not expired yet
      // Add 5 minute buffer to refresh before expiration
      const refreshBufferMs = REFRESH_BUFFER.total('milliseconds');
      const shouldRefresh = token.expiresAt && Date.now() > (token.expiresAt * 1000 - refreshBufferMs);
      
      if (!shouldRefresh && !token.error) {
        return token;
      }

      // If the access token has expired or is about to expire, try to refresh it
      try {
        const request = getRefreshTokenRequest(token);
        const response = await fetch(
          `${process.env.AUTH_BASE_URL}/oauth2/token`,
          request
        );
        
        if (!response.ok) {
          const error = await response.json();
          console.error('Token refresh failed:', error);
          
          // Handle specific error cases
          if (error.error === 'invalid_grant') {
            console.error('Refresh token is invalid or expired. User needs to re-authenticate.');
            // Clear all token data to force fresh login
            return null;
          }
          
          throw error;
        }
        
        const tokens: any = await response.json();
        
        console.log('Token refresh successful');
        return {
          ...token,
          accessToken: tokens.access_token,
          idToken: tokens.id_token ?? token.idToken,
          expiresAt: nowInSeconds() + Number(tokens.expires_in),
          refreshToken: tokens.refresh_token ?? token.refreshToken,
          error: undefined, // Clear any previous errors
        };
      } catch (error: any) {
        console.error('Failed to refresh access token:', error);
        
        // For network errors or other issues, keep trying with existing tokens
        // but mark as error so UI can handle appropriately
        if (error.error === 'invalid_grant' || error.code === 'invalid_grant') {
          // Refresh token is definitely invalid - clear everything
          return { 
            ...token, 
            accessToken: '',
            refreshToken: '',
            idToken: undefined,
            expiresAt: 0,
            error: 'RefreshAccessTokenError' as const 
          };
        }
        
        // Other errors - return token with error flag
        return { ...token, error: 'RefreshAccessTokenError' as const };
      }
    },
    async session({ session, token }) {
      if (token) {
        // If we have a refresh error and no valid tokens, return minimal session
        if (token.error === 'RefreshAccessTokenError' && (!token.accessToken || token.accessToken === '')) {
          return {
            ...session,
            user: token.user,
            accessToken: undefined,
            idToken: undefined,
            error: token.error,
          };
        }
        
        session.user = token.user;
        session.accessToken = token.accessToken;
        session.idToken = token.idToken;
        session.error = token.error;
      }
      return session;
    },
  },
  pages: {
    signIn: '/auth/signin',
  },
  debug: process.env.NODE_ENV === 'development',
});

function getRefreshTokenRequest(token: JWT) {
  const request = {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
      Authorization: `Basic ${btoa(process.env.STUDYCRM_CLIENT_ID + ':' + process.env.STUDYCRM_CLIENT_SECRET)}`,
    },
    body: new URLSearchParams({
      grant_type: 'refresh_token',
      refresh_token: `${token.refreshToken}`,
    }),
    method: 'POST',
  };
  return request;
}
