'use client'

import { signIn } from "next-auth/react";
import { useEffect } from "react";
import { useRouter } from "next/navigation";
import { useSession, getSession } from "next-auth/react";

export default function Signin() {
  const router = useRouter();
  const { status } = useSession();

  useEffect(() => {
    if (status === "unauthenticated") {
      void signIn("studycrm");
    } else if (status === "authenticated") {
      void router.push("/");
    }
  }, [status, router]);

  return (<div></div>);
}