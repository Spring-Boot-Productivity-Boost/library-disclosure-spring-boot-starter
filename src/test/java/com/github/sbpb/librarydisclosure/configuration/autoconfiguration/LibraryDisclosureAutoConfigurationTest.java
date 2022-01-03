package com.github.sbpb.librarydisclosure.configuration.autoconfiguration;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.sbpb.librarydisclosure.actuator.endpoint.LibraryDisclosureEndpoint;
import com.github.sbpb.librarydisclosure.library.data.LicenseSummary;
import com.github.sbpb.librarydisclosure.library.provider.LibraryProvider;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

@SuppressWarnings("CheckStyle")
class LibraryDisclosureAutoConfigurationTest {

  private final ApplicationContextRunner applicationContextRunner = new ApplicationContextRunner().withConfiguration(
      AutoConfigurations.of(LibraryDisclosureAutoConfiguration.class));

  @Nested
  class LibraryProviderTest {

    @Nested
    class ConditionalOnClassTest {

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
    class ConditionalOnPropertyTest {

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
    class ConditionalOnMissingBeanTest {

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
    class XmlMapperBeanTest {

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

  }

  @Nested
  class LibraryDisclosureEndpointTest {

    @Nested
    class ConditionalOnClassTest {

      @Test
      void libraryDisclosureEndpointBeanCreatedOnAvailableLibraryProviderClass() {
        applicationContextRunner.run(context -> {
          assertThat(context).hasSingleBean(LibraryDisclosureEndpoint.class);
          assertThat(context).getBean("libraryDisclosureEndpoint")
                             .isSameAs(context.getBean(LibraryDisclosureEndpoint.class));
        });
      }

      @Test
      void libraryDisclosureEndpointBeanNotCreatedOnMissingLibraryProviderClass() {
        applicationContextRunner.withClassLoader(new FilteredClassLoader(LibraryProvider.class))
                                .run(context -> assertThat(context).doesNotHaveBean(LibraryDisclosureEndpoint.class));
      }

      @Test
      void libraryDisclosureEndpointBeanCreatedOnAvailableEndpointClass() {
        applicationContextRunner.run(context -> {
          assertThat(context).hasSingleBean(LibraryDisclosureEndpoint.class);
          assertThat(context).getBean("libraryDisclosureEndpoint")
                             .isSameAs(context.getBean(LibraryDisclosureEndpoint.class));
        });
      }

      @Test
      void libraryDisclosureEndpointBeanNotCreatedOnMissingEndpointClass() {
        applicationContextRunner.withClassLoader(new FilteredClassLoader(Endpoint.class))
                                .run(context -> assertThat(context).doesNotHaveBean(LibraryDisclosureEndpoint.class));
      }

    }

    @Nested
    class ConditionalOnPropertyTest {

      @Test
      void libraryDisclosureEndpointBeanCreatedOnEnabledFeatureFlag() {
        applicationContextRunner.withPropertyValues("sbpb.librarydisclosure.enabled=true")
                                .run(context -> {
                                  assertThat(context).hasSingleBean(LibraryDisclosureEndpoint.class);
                                  assertThat(context).getBean("libraryDisclosureEndpoint")
                                                     .isSameAs(context.getBean(LibraryDisclosureEndpoint.class));
                                });
      }

      @Test
      void libraryDisclosureEndpointBeanCreatedOnMissingFeatureFlag() {
        applicationContextRunner.withPropertyValues("sbpb.librarydisclosure.foo=bar")
                                .run(context -> {
                                  assertThat(context).hasSingleBean(LibraryDisclosureEndpoint.class);
                                  assertThat(context).getBean("libraryDisclosureEndpoint")
                                                     .isSameAs(context.getBean(LibraryDisclosureEndpoint.class));
                                });
      }

      @ParameterizedTest
      @ValueSource(strings = {"false", "", " ", "null", "foobar"})
      void libraryDisclosureEndpointBeanNotCreatedOnDisabledFeatureFlag(String featureFlagValue) {
        applicationContextRunner.withPropertyValues("sbpb.librarydisclosure.enabled=" + featureFlagValue)
                                .run(context -> assertThat(context).doesNotHaveBean(LibraryDisclosureEndpoint.class));
      }

      @Test
      void libraryDisclosureEndpointBeanCreatedOnEnabledEndpointFeatureFlag() {
        applicationContextRunner.withPropertyValues("sbpb.librarydisclosure.actuator.enabled=true")
                                .run(context -> {
                                  assertThat(context).hasSingleBean(LibraryDisclosureEndpoint.class);
                                  assertThat(context).getBean("libraryDisclosureEndpoint")
                                                     .isSameAs(context.getBean(LibraryDisclosureEndpoint.class));
                                });
      }

      @Test
      void libraryDisclosureEndpointBeanCreatedOnMissingEndpointFeatureFlag() {
        applicationContextRunner.withPropertyValues("sbpb.librarydisclosure.actuator.foo=bar")
                                .run(context -> {
                                  assertThat(context).hasSingleBean(LibraryDisclosureEndpoint.class);
                                  assertThat(context).getBean("libraryDisclosureEndpoint")
                                                     .isSameAs(context.getBean(LibraryDisclosureEndpoint.class));
                                });
      }

      @ParameterizedTest
      @ValueSource(strings = {"false", "", " ", "null", "foobar"})
      void libraryDisclosureEndpointBeanNotCreatedOnDisabledEndpointFeatureFlag(String featureFlagValue) {
        applicationContextRunner.withPropertyValues("sbpb.librarydisclosure.actuator.enabled=" + featureFlagValue)
                                .run(context -> assertThat(context).doesNotHaveBean(LibraryDisclosureEndpoint.class));
      }

    }

    @Nested
    class ConditionalOnBeanTest {

      @Test
      void libraryDisclosureEndpointBeanCreatedOnAvailableLibraryProviderBean() {
        applicationContextRunner.run(context -> {
          assertThat(context).hasSingleBean(LibraryDisclosureEndpoint.class);
          assertThat(context).getBean("libraryDisclosureEndpoint")
                             .isSameAs(context.getBean(LibraryDisclosureEndpoint.class));
        });
      }

      @Test
      void libraryDisclosureEndpointBeanCreatedOnAnotherLibraryProviderBean() {
        applicationContextRunner.withBean("anotherLibraryProvider", LibraryProviderTestImpl.class)
                                .run(context -> {
                                  assertThat(context).hasSingleBean(LibraryDisclosureEndpoint.class);
                                  assertThat(context).getBean("libraryDisclosureEndpoint")
                                                     .isSameAs(context.getBean(LibraryDisclosureEndpoint.class));
                                });
      }

      @Test
      void libraryDisclosureEndpointBeanNotCreatedOnMissingLibraryProviderBean() {
        applicationContextRunner.withAllowBeanDefinitionOverriding(true)
                                .withBean("libraryProvider", String.class)
                                .run(context -> assertThat(context).doesNotHaveBean(LibraryDisclosureEndpoint.class));
      }

    }

  }

  private static class LibraryProviderTestImpl implements LibraryProvider {

    @Override
    public LicenseSummary getLicenseSummary() {
      return null;
    }

  }

}
