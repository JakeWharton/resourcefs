package com.jakewharton.resourcefs;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import static java.util.concurrent.TimeUnit.SECONDS;

final class ResourceFileAttributes implements BasicFileAttributes {
  private static final FileTime TIME = FileTime.from(0, SECONDS);

  private final Path path;
  private final FileSystem fileSystem;

  ResourceFileAttributes(Path path, FileSystem fileSystem) {
    this.path = path;
    this.fileSystem = fileSystem;
  }

  @Override public FileTime lastModifiedTime() {
    return TIME;
  }

  @Override public FileTime lastAccessTime() {
    return TIME;
  }

  @Override public FileTime creationTime() {
    return TIME;
  }

  @Override public boolean isRegularFile() {
    return !isDirectory();
  }

  @Override public boolean isDirectory() {
    return path.toUri().toString().endsWith(fileSystem.getSeparator());
  }

  @Override public boolean isSymbolicLink() {
    return false;
  }

  @Override public boolean isOther() {
    return false;
  }

  @Override public long size() {
    return 4096; // This value ends up being used for a read buffer size.
  }

  @Override public Object fileKey() {
    return null;
  }
}
