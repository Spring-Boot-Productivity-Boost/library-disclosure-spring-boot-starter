package com.github.sbpb.librarydisclosure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Utility class for tests.
 */
@SuppressWarnings("unused")
public class TestHelper {

  /**
   * Loads the content of a file.
   *
   * @param filePath The path of the file on the classpath
   * @param trim     Whether to trim the file content
   * @return The content of the file of the provided path
   * @throws IOException When the file cannot be read
   */
  public static String getFileContent(String filePath, boolean trim) throws IOException {
    final String resourcePath = Objects.requireNonNull(TestHelper.class.getClassLoader()
                                                                       .getResource(filePath))
                                       .getPath();

    final String fileContent = Files.readString(Path.of(resourcePath));

    return trim ? fileContent.trim() : fileContent;
  }

}
