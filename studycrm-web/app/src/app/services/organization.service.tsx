import "server-only";

import { Organization } from "../models/organization";
import { auth, signIn } from "@/auth"


export const getOrganizations = async (): Promise<{ organizations: Organization[]}> => {
  const accessToken = (await auth())?.accessToken;

  if (!accessToken) {
    await signIn();
  }

  const res = await fetch(
    `${process.env.API_SERVER_URL}/organizations`,
    {
      headers: {
        authorization: `Bearer ${accessToken}`,
      },
    },
  );

  if (!res.ok) {
    const json = await res.json();

    return {
      organizations: json.organizations || res.statusText || "Unable to fetch message.",
    };
  }

  return res.json();
};