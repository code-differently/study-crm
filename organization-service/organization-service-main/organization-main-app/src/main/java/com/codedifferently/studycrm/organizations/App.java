package com.codedifferently.studycrm.organizations;

import com.codedifferently.studycrm.StudyCrmPackageMarker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(scanBasePackageClasses = StudyCrmPackageMarker.class)
@Configuration
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
