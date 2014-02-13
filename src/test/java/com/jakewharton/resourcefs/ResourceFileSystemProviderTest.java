package com.jakewharton.resourcefs;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import org.junit.Before;
import org.junit.Test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

public class ResourceFileSystemProviderTest {
  private FileSystem fs;

  @Before public void setUp() throws IOException {
    fs = ResourceFileSystems.create();
  }

  @Test public void rootFile() throws IOException {
    byte[] bytes = Files.readAllBytes(fs.getPath("/hi.txt"));
    String string = new String(bytes, UTF_8);
    assertThat(string).isEqualTo("Hello, world!\n");
  }

  @Test public void fileInFolder() throws IOException {
    byte[] bytes = Files.readAllBytes(fs.getPath("/foo/bar/baz.txt"));
    String string = new String(bytes, UTF_8);
    assertThat(string).isEqualTo("BAZ\n");
  }

  @Test public void relativeFails() throws IOException {
    try {
      Files.readAllBytes(fs.getPath("foo.txt"));
      fail("Relative paths are not allowed.");
    } catch (IllegalArgumentException ignored) {
    }
  }
}
