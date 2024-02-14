package com.codedifferently.studycrm.entities.layout.domain;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LayoutService {

  @Autowired private LayoutRepository layoutRepository;

  public List<Layout> findAllByEntityType(String entityType) {
    return layoutRepository.findAllByEntityType(entityType);
  }
}
