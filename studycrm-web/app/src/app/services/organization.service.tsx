import "server-only";

import { Organization } from "../models/organization";
import { getAccessToken } from "@auth0/nextjs-auth0";



export const getOrganizations = async (): Promise<{ organizations: Organization[]}> => {
  const { accessToken } = await getAccessToken();

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

  console.log(res.statusText, res.status)

  if (!res.ok) {
    const json = await res.json();

    return {
      organizations: json.organizations || res.statusText || "Unable to fetch message.",
    };
  }

  return res.json();
};