import { SessionProvider, signIn, useSession } from "next-auth/react";
import { useEffect } from "react";
import { useRouter } from "next/router";

export default function Signin() {
  const router = useRouter();

  useEffect(() => {
    void signIn("studycrm");
  });

  return (<div></div>);
}