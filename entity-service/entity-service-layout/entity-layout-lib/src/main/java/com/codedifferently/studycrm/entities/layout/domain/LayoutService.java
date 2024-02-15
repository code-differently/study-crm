package com.codedifferently.studycrm.entities.layout.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LayoutService {

  @Autowired private LayoutRepository layoutRepository;

  public List<Layout> findAllByTypes(
      UUID organizationId, String entityType, List<String> layoutTypes) {
    return layoutRepository.findAllByTypesForOrganization(organizationId, entityType, layoutTypes);
  }
}
