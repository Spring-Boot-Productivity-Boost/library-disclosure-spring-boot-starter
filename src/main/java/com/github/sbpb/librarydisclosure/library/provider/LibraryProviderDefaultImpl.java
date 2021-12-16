package com.github.sbpb.librarydisclosure.library.provider;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.sbpb.librarydisclosure.library.data.LicenseSummary;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;

/**
 * Default implementation to load the library and license information from an XML file.
 * The XML must follow the structure of the license-maven-plugin "licenses.xml".
 */
@RequiredArgsConstructor
public class LibraryProviderDefaultImpl implements LibraryProvider {

  private final XmlMapper xmlMapper;
  private final Resource resource;

  private LicenseSummary licenseSummary;

  @Override
  public LicenseSummary getLicenseSummary() throws IOException {
    if (licenseSummary == null) {
      licenseSummary = xmlMapper.readValue(resource.getFile(), LicenseSummary.class);
    }

    return licenseSummary;
  }

}
