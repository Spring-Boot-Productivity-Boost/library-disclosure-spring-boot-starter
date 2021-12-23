package com.github.sbpb.librarydisclosure.configuration.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Configuration properties for library disclosure.
 */
@Data
@ConfigurationProperties(prefix = "sbpb.librarydisclosure")
public class LibraryDisclosureProperty {

  /**
   * Location of the licenses XML file (typically provided by the license-maven-plugin).
   * The XML file must follow the structure of the licenses.xml of the license-maven-plugin.
   */
  private Resource location = new ClassPathResource("licenses.xml");

}
