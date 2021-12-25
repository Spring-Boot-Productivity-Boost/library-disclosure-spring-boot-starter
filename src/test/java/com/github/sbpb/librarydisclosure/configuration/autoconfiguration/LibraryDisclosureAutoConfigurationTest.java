package com.github.sbpb.librarydisclosure.configuration.autoconfiguration;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.sbpb.librarydisclosure.library.data.LicenseSummary;
import com.github.sbpb.librarydisclosure.library.provider.LibraryProvider;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

@SuppressWarnings("CheckStyle")
class LibraryDisclosureAutoConfigurationTest {

  private final ApplicationContextRunner applicationContextRunner = new ApplicationContextRunner().withConfiguration(
      AutoConfigurations.of(LibraryDisclosureAutoConfiguration.class));

  @Nested
  class LibraryDisclosureAutoConfigurationConditionalOnClassTest {

    @Test
    void libraryProviderBeanCreatedOnAvailableLibraryProviderClass() {
      applicationContextRunner.run(context -> {
        assertThat(context).hasSingleBean(LibraryProvider.class);
        assertThat(context).getBean("libraryProvider")
                           .isSameAs(context.getBean(LibraryProvider.class));
      });
    }

    @Test
    void libraryProviderBeanNotCreatedOnMissingLibraryProviderClass() {
      applicationContextRunner.withClassLoader(new FilteredClassLoader(LibraryProvider.class))
                              .run(context -> assertThat(context).doesNotHaveBean(LibraryProvider.class));
    }

  }

  @Nested
  class LibraryDisclosureAutoConfigurationConditionalOnPropertyTest {

    @Test
    void libraryProviderBeanCreatedOnEnabledFeatureFlag() {
      applicationContextRunner.withPropertyValues("sbpb.librarydisclosure.enabled=true")
                              .run(context -> {
                                assertThat(context).hasSingleBean(LibraryProvider.class);
                                assertThat(context).getBean("libraryProvider")
                                                   .isSameAs(context.getBean(LibraryProvider.class));
                              });
    }

    @Test
    void libraryProviderBeanCreatedOnMissingFeatureFlag() {
      applicationContextRunner.withPropertyValues("sbpb.librarydisclosure.foo=bar")
                              .run(context -> {
                                assertThat(context).hasSingleBean(LibraryProvider.class);
                                assertThat(context).getBean("libraryProvider")
                                                   .isSameAs(context.getBean(LibraryProvider.class));
                              });
    }

    @ParameterizedTest
    @ValueSource(strings = {"false", "", " ", "null", "foobar"})
    void libraryProviderBeanNotCreatedOnDisabledFeatureFlag(String featureFlagValue) {
      applicationContextRunner.withPropertyValues("sbpb.librarydisclosure.enabled=" + featureFlagValue)
                              .run(context -> assertThat(context).doesNotHaveBean(LibraryProvider.class));
    }

  }

  @Nested
  class LibraryDisclosureAutoConfigurationConditionalOnMissingBeanTest {

    @Test
    void libraryProviderBeanCreatedOnMissingLibraryProviderBean() {
      applicationContextRunner.run(context -> {
        assertThat(context).hasSingleBean(LibraryProvider.class);
        assertThat(context).getBean("libraryProvider")
                           .isSameAs(context.getBean(LibraryProvider.class));
      });
    }

    @Test
    void libraryProviderBeanNotCreatedOnAvailableLibraryProviderBean() {
      applicationContextRunner.withBean("anotherLibraryProvider", LibraryProviderTestImpl.class)
                              .run(context -> {
                                assertThat(context).hasSingleBean(LibraryProvider.class);
                                assertThat(context).getBean("anotherLibraryProvider")
                                                   .isSameAs(context.getBean(LibraryProvider.class));
                              });
    }

  }

  @Nested
  class LibraryDisclosureAutoConfigurationXmlMapperBeanTest {

    @Test
    void libraryProviderBeanCreatedWithMissingXmlMapperBean() {
      applicationContextRunner.run(context -> {
        assertThat(context).doesNotHaveBean(XmlMapper.class);
        assertThat(context).hasSingleBean(LibraryProvider.class);
        assertThat(context).getBean("libraryProvider")
                           .isSameAs(context.getBean(LibraryProvider.class));
      });
    }

    @Test
    void libraryProviderBeanCreatedWithAvailableXmlMapperBean() {
      applicationContextRunner.withBean(XmlMapper.class)
                              .run(context -> {
                                assertThat(context).hasSingleBean(XmlMapper.class);
                                assertThat(context).getBean(LibraryProvider.class)
                                                   .extracting("xmlMapper")
                                                   .isSameAs(context.getBean(XmlMapper.class));
                                assertThat(context).hasSingleBean(LibraryProvider.class);
                                assertThat(context).getBean("libraryProvider")
                                                   .isSameAs(context.getBean(LibraryProvider.class));
                              });
    }

  }

  private static class LibraryProviderTestImpl implements LibraryProvider {

    @Override
    public LicenseSummary getLicenseSummary() {
      return null;
    }

  }

}
