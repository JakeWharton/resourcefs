package com.jakewharton.resourcefs;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Collections;
import java.util.Map;

import static com.jakewharton.resourcefs.ResourceFileSystemProvider.SCHEME;

public final class ResourceFileSystems {
  public static FileSystem create() {
    try {
      URI uri = URI.create(SCHEME + ":none");
      Map<String, ?> env = Collections.emptyMap();
      return FileSystems.newFileSystem(uri, env, ResourceFileSystem.class.getClassLoader());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private ResourceFileSystems() {
    throw new AssertionError("No instances.");
  }
}
