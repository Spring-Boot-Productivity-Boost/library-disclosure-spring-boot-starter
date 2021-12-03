package com.github.sbpb.librarydisclosure.provider.data;

import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * License report root element.
 */
@Data
@Setter(AccessLevel.NONE)
public class LicenseSummary {

  private final List<Dependency> dependencies;

}
