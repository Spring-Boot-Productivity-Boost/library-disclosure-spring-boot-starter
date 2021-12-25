package com.github.sbpb.librarydisclosure.library.data;

import java.util.List;
import lombok.Data;

/**
 * License report dependency section.
 */
@Data
public class Dependency {

  private final String groupId;
  private final String artifactId;
  private final String version;
  private final List<License> licenses;

}
