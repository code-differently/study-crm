import NextAuth from "next-auth"

export const { handlers, auth, signIn, signOut } = NextAuth({
    providers: [{
      id: "studycrm",
      name: "StudyCRM",
      type: "oidc",
      clientId: process.env.STUDYCRM_CLIENT_ID,
      clientSecret: process.env.STUDYCRM_CLIENT_SECRET,
      issuer: process.env.STUDYCRM_ISSUER_BASE_URL,
      wellKnown: `${process.env.STUDYCRM_ISSUER_BASE_URL}/.well-known/openid-configuration`,
      authorization: { 
        url: `${process.env.STUDYCRM_ISSUER_BASE_URL}/oauth2/authorize`,
        params: { 
          scope: "openid profile",
          response_mode: 'form_post'
        },
      },
      token: `${process.env.STUDYCRM_ISSUER_BASE_URL}/oauth2/token`,
      checks: ["state"],
      profile(profile) {
          return {
              id: profile.sub,
              name: profile.name,
              email: profile.email,
              image: profile.picture,
          }
      },
    }],
    debug: true,
  });