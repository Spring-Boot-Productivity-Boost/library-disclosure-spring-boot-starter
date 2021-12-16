package com.github.sbpb.librarydisclosure.library.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.sbpb.librarydisclosure.library.data.Dependency;
import com.github.sbpb.librarydisclosure.library.data.License;
import com.github.sbpb.librarydisclosure.library.data.LicenseSummary;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SuppressWarnings("CheckStyle")
@ExtendWith(MockitoExtension.class)
class LibraryProviderDefaultImplTest {

  @InjectMocks
  private LibraryProviderDefaultImpl testee;

  @Mock
  private XmlMapper xmlMapper;

  @Mock
  private Resource resource;

  private static Stream<Arguments> getInputXmlPathAndExpectedLicenseSummary() {
    final LicenseSummary noDependency = new LicenseSummary(Collections.emptyList());

    final LicenseSummary oneDependencyNoLicense = new LicenseSummary(List.of(new Dependency("group1", "artifact1", "1.0.0", null)));

    final LicenseSummary oneDependencyOneLicense = new LicenseSummary(
        List.of(new Dependency("group1", "artifact1", "1.0.0", List.of(new License("name1", "url1", "file1", null, null)))));

    final LicenseSummary oneDependencyMultipleLicenses = new LicenseSummary(List.of(new Dependency("group1", "artifact1", "1.0.0",
        List.of(new License("name1", "url1", "file1", null, null), new License("name2", "url2", "file2", "comment2", "distribution2")))));

    final LicenseSummary multipleDependenciesNoLicense = new LicenseSummary(
        List.of(new Dependency("group1", "artifact1", "1.0.0", null), new Dependency("group2", "artifact2", "2.0.0", null)));

    final LicenseSummary multipleDependenciesOneLicense = new LicenseSummary(
        List.of(new Dependency("group1", "artifact1", "1.0.0", List.of(new License("name1", "url1", "file1", null, null))),
            new Dependency("group2", "artifact2", "2.0.0", List.of(new License("name2", "url2", "file2", "comment2", "distribution2")))));

    final LicenseSummary multipleDependenciesMultipleLicenses = new LicenseSummary(
        List.of(new Dependency("group1", "artifact1", "1.0.0",
                List.of(new License("name1", "url1", "file1", null, null), new License("name2", "url2", "file2", "comment2", "distribution2"))),
            new Dependency("group2", "artifact2", "2.0.0",
                List.of(new License("name3", "url3", "file3", null, null), new License("name4", "url4", "file4", "comment4", "distribution4")))));

    return Stream.<Arguments>builder()
                 .add(Arguments.of("library/provider/no-dependency.xml", noDependency))

                 .add(Arguments.of("library/provider/one-dependency-no-license.xml", oneDependencyNoLicense))
                 .add(Arguments.of("library/provider/one-dependency-one-license.xml", oneDependencyOneLicense))
                 .add(Arguments.of("library/provider/one-dependency-multiple-licenses.xml", oneDependencyMultipleLicenses))

                 .add(Arguments.of("library/provider/multiple-dependencies-no-license.xml", multipleDependenciesNoLicense))
                 .add(Arguments.of("library/provider/multiple-dependencies-one-license.xml", multipleDependenciesOneLicense))
                 .add(Arguments.of("library/provider/multiple-dependencies-multiple-licenses.xml", multipleDependenciesMultipleLicenses))
                 .build();
  }

  @ParameterizedTest
  @MethodSource("getInputXmlPathAndExpectedLicenseSummary")
  void getLicenseSummaryDeserializesXmlContent(String inputPath, LicenseSummary expected) throws IOException {
    testee = new LibraryProviderDefaultImpl(new XmlMapper(), new ClassPathResource(inputPath));

    final LicenseSummary result = testee.getLicenseSummary();

    assertEquals(expected, result);
  }

  @Test
  void getLicenseSummaryCallsXmlMapperOnlyOnce() throws IOException {
    when(resource.getFile()).thenReturn(new File("path"));
    when(xmlMapper.readValue(any(File.class), eq(LicenseSummary.class))).thenReturn(new LicenseSummary(Collections.emptyList()));

    testee.getLicenseSummary();
    testee.getLicenseSummary();

    verify(xmlMapper, times(1)).readValue(any(File.class), eq(LicenseSummary.class));
  }

}
