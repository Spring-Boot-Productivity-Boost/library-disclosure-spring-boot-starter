package com.github.sbpb.librarydisclosure.actuator.endpoint;

import com.github.sbpb.librarydisclosure.library.data.LicenseSummary;
import com.github.sbpb.librarydisclosure.library.provider.LibraryProvider;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * Actuator {@link Endpoint} to expose library disclosure information.
 */
@RequiredArgsConstructor
@Endpoint(id = "librarydisclosure")
public class LibraryDisclosureEndpoint {

  private final LibraryProvider libraryProvider;

  /**
   * Exposes library disclosure information.
   *
   * @return Library report along with licenses
   * @throws IOException When resources of the {@link LibraryProvider} cannot be read or parsed
   */
  @ReadOperation
  public LicenseSummary getLicenseSummary() throws IOException {
    return libraryProvider.getLicenseSummary();
  }

}
