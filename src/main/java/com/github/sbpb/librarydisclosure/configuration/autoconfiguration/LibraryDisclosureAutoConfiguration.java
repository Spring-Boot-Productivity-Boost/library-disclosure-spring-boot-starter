package com.github.sbpb.librarydisclosure.configuration.autoconfiguration;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.sbpb.librarydisclosure.configuration.property.LibraryDisclosureProperty;
import com.github.sbpb.librarydisclosure.library.provider.LibraryProvider;
import com.github.sbpb.librarydisclosure.library.provider.LibraryProviderDefaultImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
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
 *   <li>Active, unless deactivated with the feature flag.</li>
 *   <li>Makes use of the {@link LibraryDisclosureProperty} configuration.</li>
 *   <li>When a global {@link XmlMapper} bean is available, this one is used.</li>
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
  @ConditionalOnMissingBean(LibraryProvider.class)
  public LibraryProvider libraryProvider(@Autowired(required = false) XmlMapper xmlMapper, LibraryDisclosureProperty libraryDisclosureProperty) {
    if (xmlMapper == null) {
      xmlMapper = new XmlMapper();
    }

    return new LibraryProviderDefaultImpl(xmlMapper, libraryDisclosureProperty.getLocation());
  }

}
