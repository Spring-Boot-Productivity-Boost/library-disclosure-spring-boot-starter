package com.github.sbpb.librarydisclosure.library.provider;

import com.github.sbpb.librarydisclosure.library.data.LicenseSummary;
import java.io.IOException;

/**
 * Provision of library and license information.
 */
public interface LibraryProvider {

  /**
   * Discloses libraries and licenses.
   *
   * @return Disclosed library report with license information
   * @throws IOException When resources cannot be read or parsed
   */
  LicenseSummary getLicenseSummary() throws IOException;

}
