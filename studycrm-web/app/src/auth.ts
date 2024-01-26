import NextAuth from "next-auth"
import { JWT } from "next-auth/jwt";

export const { handlers, auth, signIn, signOut } = NextAuth({
    providers: [{
      id: "studycrm",
      name: "StudyCRM",
      type: "oidc",
      clientId: process.env.STUDYCRM_CLIENT_ID,
      clientSecret: process.env.STUDYCRM_CLIENT_SECRET,
      issuer: process.env.STUDYCRM_ISSUER_BASE_URL,
      authorization: { 
        params: { 
          scope: "openid profile",
          response_mode: 'form_post'
        },
      },
      checks: ["pkce", "state", "nonce"],
    }],
    session: {
      strategy: "jwt",
    },
    callbacks: {
      async jwt({ token, account }) {
        if (account) {
          // Save the access token and refresh token in the JWT on the initial login
          return {
            access_token: account.access_token,
            expires_at: Math.floor(Date.now() / 1000 + account.expires_in!),
            refresh_token: account.refresh_token,
          }
        } else if (Date.now() < (token.expires_at * 1000)) {
          // If the access token has not expired yet, return it
          return token
        } else {
          // If the access token has expired, try to refresh it
          try {
            const request = getRefreshTokenRequest(token);
            const response = await fetch(`${process.env.AUTH_BASE_URL}/oauth2/token`, request);
            const tokens: JWT = await response.json()
            if (!response.ok) throw tokens
            return {
              ...token,
              access_token: tokens.access_token,
              expires_at: Math.floor(Date.now() / 1000 + tokens.expires_in),
              refresh_token: tokens.refresh_token ?? token.refresh_token,
            }
          } catch (error) {
            console.error("Error refreshing access token", error)
            return { ...token, error: "RefreshAccessTokenError" as const }
          }
        }
      },
      async session({ session, token }) {
        if (token) {
          session.accessToken = token.access_token;
        }
        return session
      }
    },
    debug: true,
  });

  function getRefreshTokenRequest(token: JWT) {
    const request = {
      headers: { 
        "Content-Type": "application/x-www-form-urlencoded",
        "Authorization": `Basic ${btoa(process.env.STUDYCRM_CLIENT_ID + ":" + process.env.STUDYCRM_CLIENT_SECRET)}`
      },
      body: new URLSearchParams({
        grant_type: 'refresh_token',
        refresh_token: `${token.refresh_token}`,
      }),
      method: "POST",
    };
    return request;
  }