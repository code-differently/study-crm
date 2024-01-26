import NextAuth from "next-auth";
import type { Session } from "next-auth";

declare module "next-auth" {

  interface Session {
    accessToken?: string;
  }

}