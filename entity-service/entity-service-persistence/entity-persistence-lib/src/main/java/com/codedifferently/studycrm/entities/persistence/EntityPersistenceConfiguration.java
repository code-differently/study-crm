package com.codedifferently.studycrm.entities.persistence;

import com.codedifferently.studycrm.entities.domain.Entity;
import com.codedifferently.studycrm.entities.domain.EntityRepository;
import com.codedifferently.studycrm.entities.layout.domain.Layout;
import com.codedifferently.studycrm.entities.layout.domain.LayoutRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {EntityRepository.class, LayoutRepository.class})
@EntityScan(basePackageClasses = {Entity.class, Layout.class})
public class EntityPersistenceConfiguration {}
