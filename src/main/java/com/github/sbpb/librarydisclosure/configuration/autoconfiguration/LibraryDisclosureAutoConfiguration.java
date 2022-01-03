package com.github.sbpb.librarydisclosure.configuration.autoconfiguration;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.sbpb.librarydisclosure.actuator.endpoint.LibraryDisclosureEndpoint;
import com.github.sbpb.librarydisclosure.configuration.property.LibraryDisclosureProperty;
import com.github.sbpb.librarydisclosure.library.provider.LibraryProvider;
import com.github.sbpb.librarydisclosure.library.provider.LibraryProviderDefaultImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-configuration for library disclosure.
 *
 * <ul>
 *   <li>Active unless deactivated with the "sbpb.librarydisclosure.enabled" feature flag.</li>
 *   <li>Makes use of the {@link LibraryDisclosureProperty} configuration properties.</li>
 *   <li>When a global {@link XmlMapper} bean is available, this one is used.</li>
 * </ul>
 *
 * Exposes the library disclosure information via an actuator endpoint.
 *
 * <ul>
 *   <li>If the required actuator dependencies are available.</li>
 *   <li>If a {@link LibraryProvider} bean is available.</li>
 *   <li>If not deactivated with the "sbpb.librarydisclosure.actuator.enabled" feature flag.</li>
 * </ul>
 */
@Configuration
@SuppressWarnings("CheckStyle")
@ConditionalOnClass(LibraryProvider.class)
@AutoConfigureAfter(JacksonAutoConfiguration.class)
@EnableConfigurationProperties(LibraryDisclosureProperty.class)
@ConditionalOnProperty(prefix = "sbpb.librarydisclosure", name = "enabled", havingValue = "true", matchIfMissing = true)
public class LibraryDisclosureAutoConfiguration {

  @Bean
  @SuppressWarnings("unused")
  @ConditionalOnMissingBean(LibraryProvider.class)
  public LibraryProvider libraryProvider(@Autowired(required = false) XmlMapper xmlMapper, LibraryDisclosureProperty libraryDisclosureProperty) {
    if (xmlMapper == null) {
      xmlMapper = new XmlMapper();
    }

    return new LibraryProviderDefaultImpl(xmlMapper, libraryDisclosureProperty.getLocation());
  }

  @Bean
  @SuppressWarnings("unused")
  @ConditionalOnClass(Endpoint.class)
  @ConditionalOnBean(LibraryProvider.class)
  @ConditionalOnProperty(prefix = "sbpb.librarydisclosure.actuator", name = "enabled", havingValue = "true", matchIfMissing = true)
  public LibraryDisclosureEndpoint libraryDisclosureEndpoint(LibraryProvider libraryProvider) {
    return new LibraryDisclosureEndpoint(libraryProvider);
  }

}
