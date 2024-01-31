package com.codedifferently.studycrm.organizations.domain;

import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Permission;

public class BitMaskPermissionGrantingStrategy extends DefaultPermissionGrantingStrategy {

    public BitMaskPermissionGrantingStrategy(AuditLogger auditLogger) {
        super(auditLogger);
    }

    @Override
    protected boolean isGranted(AccessControlEntry ace, Permission p) {
        if (ace.isGranting() && p.getMask() != 0) {
            return (ace.getPermission().getMask() & p.getMask()) != 0;
        } else {
            return ace.getPermission().getMask() == p.getMask();
        }
    }

}
