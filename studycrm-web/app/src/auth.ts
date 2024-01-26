import NextAuth from "next-auth"
import { userAgent } from "next/server";

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
        // Persist the OAuth access_token to the token right after signin
        if (account) {
          token.accessToken = account.access_token
        }
        return token
      },
      async session({ session, token }) {
        if (token) {
          session.accessToken = token.accessToken
        }
        return session
      }
    },
    debug: true,
  });