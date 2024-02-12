import { NextPage } from "next";
import React from "react";
import { getOrganizations } from "@/src/app/services/organization.service";

const Profile: NextPage = async (ctx) => { 
  const { organizations } = await getOrganizations();

  return (
    <div className="content-layout">
      <h1 id="page-title" className="content__title">
        Admin Page
      </h1>
      <div className="content__body">
        <p id="page-description">
          <span>
            This page retrieves an <strong>admin message</strong>.
          </span>
          <span>
            <strong>
              Only authenticated users with the <code>read:admin-messages</code>{" "}
              permission should access this page.
            </strong>
          </span>
        </p>
      </div>
    </div>
  );
};

export default Profile;