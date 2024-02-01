package com.codedifferently.studycrm.organizations.web;

import com.codedifferently.studycrm.organizations.domain.BitMaskPermissionGrantingStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity
@EnableCaching
public class AclSecurityConfiguration {
    @Autowired
    DataSource dataSource;

    @Autowired
    CacheManager cacheManager;

    @Bean
    public JdbcMutableAclService aclService() {
        var jdbcMutableAclService = new JdbcMutableAclService(
                dataSource, lookupStrategy(), aclCache());
        jdbcMutableAclService.setClassIdentityQuery("SELECT @@IDENTITY");
        jdbcMutableAclService.setSidIdentityQuery("SELECT @@IDENTITY");
        jdbcMutableAclService.setAclClassIdSupported(true);
        return jdbcMutableAclService;
    }

    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(
                new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy() {
        return new BitMaskPermissionGrantingStrategy(new ConsoleAuditLogger());
    }

    @Bean
    public AclCache aclCache() {
        return new SpringCacheBasedAclCache(
                cacheManager.getCache("aclCache"),
                permissionGrantingStrategy(),
                aclAuthorizationStrategy());
    }

    @Bean
    public LookupStrategy lookupStrategy() {
        var lookupStrategy = new BasicLookupStrategy(
                dataSource,
                aclCache(),
                aclAuthorizationStrategy(),
                new ConsoleAuditLogger());
        lookupStrategy.setAclClassIdSupported(true);
        return lookupStrategy;
    }

}
