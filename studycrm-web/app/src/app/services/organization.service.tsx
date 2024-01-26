import "server-only";

import { Organization } from "../models/organization";
import { auth } from "@/auth"


export const getOrganizations = auth(async (req): Promise<{ organizations: Organization[]}> => {
  const accessToken = '123';


  if (!accessToken) {
    throw new Error(`Requires authorization`);
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
});