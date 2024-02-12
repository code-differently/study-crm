package com.codedifferently.studycrm.auth.persistence;

import com.codedifferently.studycrm.auth.domain.User;
import com.codedifferently.studycrm.auth.domain.UserAuthority;
import com.codedifferently.studycrm.auth.domain.UserRepository;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@EnableJpaRepositories(basePackageClasses = {UserRepository.class})
@EntityScan(basePackageClasses = {User.class, UserAuthority.class})
public class AuthPersistenceConfiguration {
  @Bean
  public DataSourceInitializer dataSourceInitializer(
      @Qualifier("dataSource") final DataSource dataSource) {
    Objects.requireNonNull(dataSource, "dataSource must not be null");
    var resourceDatabasePopulator = new ResourceDatabasePopulator();
    resourceDatabasePopulator.addScript(
        new ClassPathResource(
            "org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql"));
    resourceDatabasePopulator.addScript(
        new ClassPathResource(
            "org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql"));
    resourceDatabasePopulator.addScript(
        new ClassPathResource(
            "org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql"));
    var dataSourceInitializer = new DataSourceInitializer();
    dataSourceInitializer.setDataSource(dataSource);
    dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
    return dataSourceInitializer;
  }
}
