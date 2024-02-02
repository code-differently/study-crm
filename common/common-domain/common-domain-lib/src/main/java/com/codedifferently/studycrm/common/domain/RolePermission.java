package com.codedifferently.studycrm.common.domain;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.CumulativePermission;
import org.springframework.security.acls.model.Permission;

public enum RolePermission {
  READER(BasePermission.READ),

  EDITOR(RolePermission.READER.getPermission(), BasePermission.CREATE, BasePermission.WRITE),

  ADMIN(
      RolePermission.EDITOR.getPermission(), BasePermission.DELETE, BasePermission.ADMINISTRATION);

  private final Permission cumulativePermission;

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
