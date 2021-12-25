package com.github.sbpb.librarydisclosure.library.data;

import lombok.Data;

/**
 * License report license section.
 */
@Data
public class License {

  private final String name;
  private final String url;
  private final String file;
  private final String comments;
  private final String distribution;

}
