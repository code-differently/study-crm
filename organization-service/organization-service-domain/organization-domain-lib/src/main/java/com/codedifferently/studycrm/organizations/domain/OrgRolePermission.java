package com.codedifferently.studycrm.organizations.domain;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.CumulativePermission;
import org.springframework.security.acls.model.Permission;

public enum OrgRolePermission {

    READER(new CumulativePermission().set(BasePermission.READ)),

    EDITOR(new CumulativePermission()
            .set(OrgRolePermission.READER.getPermission())
            .set(BasePermission.CREATE)
            .set(BasePermission.WRITE)),

    ADMIN(new CumulativePermission()
            .set(OrgRolePermission.EDITOR.getPermission())
            .set(BasePermission.DELETE)
            .set(BasePermission.ADMINISTRATION));

    private final CumulativePermission cumulativePermission;

    OrgRolePermission(Permission permission) {
        this.cumulativePermission = new CumulativePermission().set(permission);
    }

    OrgRolePermission(BasePermission... permissions) {
        this.cumulativePermission = new CumulativePermission();
        for (Permission permission : permissions) {
            this.cumulativePermission.set(permission);
        }
    }

    public Permission getPermission() {
        return cumulativePermission;
    }
}
