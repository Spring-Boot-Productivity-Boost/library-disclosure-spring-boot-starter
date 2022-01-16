<h1 align="center">
    <img src="./.docs/logo/logo.png" width="400"/>
</h1>

<h4 align="center">Library Disclosure Spring Boot Starter</h4>

<p align="center">Disclosure of libraries and licenses</p>

<p align="center">
    <a href="https://github.com/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter/actions/workflows/ci-build.yaml"><img src="https://img.shields.io/github/workflow/status/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter/CI build?logo=github&logoColor=white"></a>
    <a href="https://github.com/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter/tags"><img src="https://img.shields.io/github/v/tag/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter?display_name=tag&logo=github&logoColor=white"></a>
    <a href="https://opensource.org/licenses/mit-license.php"><img src="https://img.shields.io/github/license/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter?logo=Open Source Initiative&logoColor=white"></a>
    <br>
    <a href="https://codebeat.co/projects/github-com-spring-boot-productivity-boost-library-disclosure-spring-boot-starter-develop"><img src="https://codebeat.co/badges/bee3f5ec-31c1-4cd9-bb43-f3d16c203080"></a>
    <a href="https://codecov.io/gh/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter"><img src="https://codecov.io/gh/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter/branch/develop/graph/badge.svg?token=67917LNJ02"></a>
    <br>
    <a href="https://github.com/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter/issues"><img src="https://img.shields.io/github/issues/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter?logo=github&logoColor=white"></a>
    <a href="https://github.com/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter/issues?q=is%3Aissue+is%3Aclosed"><img src="https://img.shields.io/github/issues-closed/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter?logo=github&logoColor=white"></a>
    <a href="https://github.com/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter/pulls"><img src="https://img.shields.io/github/issues-pr/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter?logo=github&logoColor=white"></a>
    <a href="https://github.com/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter/pulls?q=is%3Apr+is%3Aclosed"><img src="https://img.shields.io/github/issues-pr-closed/Spring-Boot-Productivity-Boost/library-disclosure-spring-boot-starter?logo=github&logoColor=white"></a>
</p>

<p align="center">
    <a href="#tldr">TL;DR</a> •
    <a href="#about">About</a> •
    <a href="#requirements">Requirements</a> •
    <a href="#compatibility">Compatibility</a> •
    <a href="#features">Features</a> •
    <a href="#how-to-use">How To Use</a> •
    <a href="#changelog">Changelog</a> •
    <a href="#links-and-resources">Links And Resources</a> •
    <a href="#license">License</a>
</p>

<h1></h1>

## TL;DR

Add the [License Maven Plugin](https://www.mojohaus.org/license-maven-plugin/index.html) to the `<build>` section of the Spring Boot Maven project. Execute it
with the `download-licenses` goal and make sure that the generated `licenses.xml` file is included in the packed JAR.

To disclose project libraries and licenses, additionally put this dependency on the classpath of the Spring Boot project. By default, it loads
the `licenses.xml` file as a resource from `classpath:/BOOT-INF/classes/`.

When the [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html) is on the classpath as well and
the `librarydisclosure` endpoint is exposed, it serves the libraries and licenses as an actuator endpoint at `/actuator/librarydisclosure`. Otherwise,
the `LibraryProvider` bean can be injected in the client code for individual usage.

```yaml
management:
  endpoints:
    web:
      exposure:
        include:
          - librarydisclosure
```

## About

Tbd.

## Requirements

Tbd.

## Compatibility

Tbd.

## Features

Tbd.

## How To Use

Tbd.

Properties

## Changelog

Tbd.

== Changelog

=== https://github.com/pixelstuermer/pixieset-downloader/tree/master[Unreleased] (XXXX-XX-XX)

https://github.com/pixelstuermer/pixieset-downloader/compare/1.0.2..master[Full Changelog]

==== Added

==== Changed

==== Fixed

=== https://github.com/pixelstuermer/pixieset-downloader/tree/1.0.2[1.0.2] (2021-11-23)

https://github.com/pixelstuermer/pixieset-downloader/compare/1.0.1..1.0.2[Full Changelog]

==== Fixed

* Enabled HTTP requests by using `none` as `user-agent` header

=== https://github.com/pixelstuermer/pixieset-downloader/tree/1.0.1[1.0.1] (2021-08-14)

https://github.com/pixelstuermer/pixieset-downloader/compare/1.0.0..1.0.1[Full Changelog]

==== Added

* Readme and automated GitHub pages

==== Fixed

* Minor typos

=== https://github.com/pixelstuermer/pixieset-downloader/tree/1.0.0[1.0.0] (2021-08-14)

==== Added

* Basic functionality and project setup
* Batch download of Pixieset collections
  ** Based on a base url, a collection ID and key, a gallery name and a valid HTTP cookie
  ** With the possibility to specify target file names and a counter separator
  ** With an optional filter for image names via RegEx

## Links And Resources

* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
* [License Maven Plugin](https://www.mojohaus.org/license-maven-plugin/index.html)
* [Intellij Java Google Style](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml)
* [Checkstyle Google Checks](https://github.com/checkstyle/checkstyle/blob/checkstyle-8.30/src/main/resources/google_checks.xml)

## License

[MIT License](https://opensource.org/licenses/mit-license.php) copyright (c) 2021 Spring Boot Productivity Boost.
