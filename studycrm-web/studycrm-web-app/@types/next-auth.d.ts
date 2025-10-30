import NextAuth from 'next-auth';
import type { Session } from 'next-auth';

declare module 'next-auth' {
  interface Session {
    user?: User;
    accessToken?: string;
    idToken?: string;
    error?: string;
  }

  interface User {
    id: string;
    firstName: string;
    lastName: string;
    name: string;
    email: string;
    organizationIds: string[];
  }
}

declare module 'next-auth/jwt' {
  interface JWT {
    user: User;
    accessToken: string;
    idToken?: string;
    refreshToken: string;
    expiresAt: number;
    error?: string;
  }
}
