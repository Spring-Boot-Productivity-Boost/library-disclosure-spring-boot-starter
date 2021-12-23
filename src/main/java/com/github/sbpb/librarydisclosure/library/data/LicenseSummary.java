package com.github.sbpb.librarydisclosure.library.data;

import java.util.List;
import lombok.Data;

/**
 * License report root element.
 */
@Data
public class LicenseSummary {

  private final List<Dependency> dependencies;

}
