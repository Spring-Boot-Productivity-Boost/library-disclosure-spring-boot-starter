package com.github.sbpb.librarydisclosure.provider.data;

import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * License report dependency section.
 */
@Data
@Setter(AccessLevel.NONE)
public class Dependency {

  private final String groupId;
  private final String artifactId;
  private final String version;
  private final List<License> licenses;

}
