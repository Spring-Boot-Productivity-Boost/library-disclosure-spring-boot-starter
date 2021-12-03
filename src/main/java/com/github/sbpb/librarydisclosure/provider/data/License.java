package com.github.sbpb.librarydisclosure.provider.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * License report license section.
 */
@Data
@Setter(AccessLevel.NONE)
public class License {

  private final String name;
  private final String url;
  private final String file;
  private final String comments;
  private final String distribution;

}
