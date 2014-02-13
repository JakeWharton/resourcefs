package com.jakewharton.resourcefs;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.Set;

import static java.util.Objects.requireNonNull;

final class ResourceFileSystem extends FileSystem {
  private final ResourceFileSystemProvider provider;

  ResourceFileSystem(ResourceFileSystemProvider provider) {
    this.provider = requireNonNull(provider, "provider");
  }

  @Override public FileSystemProvider provider() {
    return provider;
  }

  @Override public void close() throws IOException {
  }

  @Override public boolean isOpen() {
    return true;
  }

  @Override public boolean isReadOnly() {
    return true;
  }

  @Override public String getSeparator() {
    return "/";
  }

  @Override public Iterable<Path> getRootDirectories() {
    return Collections.<Path>singletonList(new ResourcePath(this, URI.create("/")));
  }

  @Override public Iterable<FileStore> getFileStores() {
    return null; // TODO
  }

  @Override public Set<String> supportedFileAttributeViews() {
    return Collections.singleton("basic");
  }

  @Override public Path getPath(String first, String... more) {
    StringBuilder builder = new StringBuilder(first);
    for (String m : more) {
      builder.append(getSeparator()).append(m);
    }
    return new ResourcePath(this, URI.create(builder.toString()));
  }

  @Override public PathMatcher getPathMatcher(String syntaxAndPattern) {
    return null; // TODO
  }

  @Override public UserPrincipalLookupService getUserPrincipalLookupService() {
    return null; // TODO
  }

  @Override public WatchService newWatchService() throws IOException {
    return null; // TODO
  }
}
