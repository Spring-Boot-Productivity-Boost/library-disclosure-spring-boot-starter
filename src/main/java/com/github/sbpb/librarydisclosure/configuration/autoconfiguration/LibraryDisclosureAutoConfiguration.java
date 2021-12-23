package com.github.sbpb.librarydisclosure.configuration.autoconfiguration;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.sbpb.librarydisclosure.configuration.property.LibraryDisclosureProperty;
import com.github.sbpb.librarydisclosure.library.provider.LibraryProvider;
import com.github.sbpb.librarydisclosure.library.provider.LibraryProviderDefaultImpl;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-configuration for library disclosure.
 *
 * <ul>
 *   <li>Makes use of the {@link LibraryDisclosureProperty} configuration.</li>
 *   <li>When a global {@link XmlMapper} bean is available, this one is used.</li>
 * </ul>
 */
@Configuration
@SuppressWarnings("CheckStyle")
@ConditionalOnClass(LibraryProvider.class)
@AutoConfigureAfter(JacksonAutoConfiguration.class)
@EnableConfigurationProperties(LibraryDisclosureProperty.class)
public class LibraryDisclosureAutoConfiguration {

  /**
   * Creates a {@link LibraryProvider} using a local {@link XmlMapper}.
   */
  @Bean("libraryProvider")
  @ConditionalOnMissingBean({XmlMapper.class, LibraryProvider.class})
  public LibraryProvider libraryProviderLocalXmlMapper(LibraryDisclosureProperty libraryDisclosureProperty) {
    return createLibraryProvider(new XmlMapper(), libraryDisclosureProperty);
  }

  /**
   * Creates a {@link LibraryProvider} using a global {@link XmlMapper} bean.
   */
  @Bean("libraryProvider")
  @ConditionalOnBean(XmlMapper.class)
  @ConditionalOnMissingBean(LibraryProvider.class)
  public LibraryProvider libraryProviderGlobalXmlMapperBean(XmlMapper xmlMapper, LibraryDisclosureProperty libraryDisclosureProperty) {
    return createLibraryProvider(xmlMapper, libraryDisclosureProperty);
  }

  private LibraryProvider createLibraryProvider(XmlMapper xmlMapper, LibraryDisclosureProperty libraryDisclosureProperty) {
    return new LibraryProviderDefaultImpl(xmlMapper, libraryDisclosureProperty.getLocation());
  }

}
