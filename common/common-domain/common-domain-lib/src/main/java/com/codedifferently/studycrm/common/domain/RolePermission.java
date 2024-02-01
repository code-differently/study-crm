package com.codedifferently.studycrm.common.domain;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.CumulativePermission;
import org.springframework.security.acls.model.Permission;

public enum RolePermission {
  READER(new CumulativePermission().set(BasePermission.READ)),

  EDITOR(
      new CumulativePermission()
          .set(RolePermission.READER.getPermission())
          .set(BasePermission.CREATE)
          .set(BasePermission.WRITE)),

  ADMIN(
      new CumulativePermission()
          .set(RolePermission.EDITOR.getPermission())
          .set(BasePermission.DELETE)
          .set(BasePermission.ADMINISTRATION));

  private final Permission cumulativePermission;

  RolePermission(Permission permission) {
    this.cumulativePermission = new CumulativePermission().set(permission);
  }

  RolePermission(Permission... permissions) {
    var cumulativePermission = new CumulativePermission();
    for (Permission permission : permissions) {
      cumulativePermission.set(permission);
    }
    this.cumulativePermission = cumulativePermission;
  }

  public Permission getPermission() {
    return cumulativePermission;
  }
}
