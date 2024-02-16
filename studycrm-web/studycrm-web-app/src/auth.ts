import NextAuth from 'next-auth';
import { JWT } from 'next-auth/jwt';

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
          scope: 'openid',
          response_mode: 'form_post',
        },
      },
      checks: ['pkce', 'state', 'nonce'],
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
  },
  callbacks: {
    async jwt({ token, account, user }) {
      if (account) {
        // Save the access token and refresh token in the JWT on the initial login
        return {
          user,
          accessToken: account.access_token,
          expiresAt: account.expires_in
            ? Math.floor(Date.now() / 1000 + account.expires_in!)
            : 0,
          refreshToken: account.refresh_token,
        };
      } else if (!token.expiresAt || Date.now() < token.expiresAt * 1000) {
        // If the access token has not expired yet, return it
        return token;
      } else {
        // If the access token has expired, try to refresh it
        try {
          const request = getRefreshTokenRequest(token);
          const response = await fetch(
            `${process.env.AUTH_BASE_URL}/oauth2/token`,
            request
          );
          const tokens: JWT = await response.json();
          if (!response.ok) throw tokens;
          return {
            ...token,
            accessToken: tokens.access_token,
            expiresAt: Math.floor(Date.now() / 1000 + tokens.expires_in),
            refreshToken: tokens.refresh_token ?? token.refreshToken,
          };
        } catch (error) {
          return { ...token, error: 'RefreshAccessTokenError' as const };
        }
      }
    },
    async session({ session, token, user }) {
      if (token) {
        session.user = token.user;
        session.accessToken = token.accessToken;
      }
      return session;
    },
  },
  pages: {
    signIn: '/auth/signin',
  },
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
