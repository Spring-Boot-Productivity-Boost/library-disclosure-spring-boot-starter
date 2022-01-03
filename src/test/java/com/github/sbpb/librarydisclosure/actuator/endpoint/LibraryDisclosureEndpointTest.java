package com.github.sbpb.librarydisclosure.actuator.endpoint;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.sbpb.librarydisclosure.library.provider.LibraryProvider;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LibraryDisclosureEndpointTest {

  @InjectMocks
  private LibraryDisclosureEndpoint testee;

  @Mock
  private LibraryProvider libraryProvider;

  @Test
  void getLicenseSummaryCallsLibraryProvider() throws IOException {
    testee.getLicenseSummary();

    verify(libraryProvider).getLicenseSummary();
  }

  @Test
  void getLicenseSummaryRethrowsException() throws IOException {
    when(libraryProvider.getLicenseSummary()).thenThrow(IOException.class);

    assertThrows(IOException.class, () -> testee.getLicenseSummary());
  }

}
