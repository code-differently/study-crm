package com.codedifferently.studycrm.common.domain;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;

@Service
public class EntityAclManager {

  @Autowired private MutableAclService mutableAclService;

  public EntityAclManager(MutableAclService mutableAclService) {
    this.mutableAclService = mutableAclService;
  }

  @Transactional
  public void addPermission(EntityBase target, Sid recipient, Permission permission) {
    MutableAcl acl;
    ObjectIdentity oid = new ObjectIdentityImpl(target.getClass(), target.getId());

    try {
      acl = (MutableAcl) this.mutableAclService.readAclById(oid);
    } catch (NotFoundException nfe) {
      acl = this.mutableAclService.createAcl(oid);
    }

    acl.insertAce(acl.getEntries().size(), permission, recipient, true);
    this.mutableAclService.updateAcl(acl);
  }
}
