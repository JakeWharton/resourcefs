package com.jakewharton.resourcefs;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;

import static java.util.Objects.requireNonNull;

final class ResourcePath implements Path {
  private final FileSystem fileSystem;
  private final URI uri;

  ResourcePath(FileSystem fileSystem, URI uri) {
    this.fileSystem = requireNonNull(fileSystem, "fileSystem");
    this.uri = requireNonNull(uri, "uri");
  }

  @Override public FileSystem getFileSystem() {
    return fileSystem;
  }

  @Override public boolean isAbsolute() {
    return uri.toString().startsWith(fileSystem.getSeparator());
  }

  @Override public Path getRoot() {
    throw new UnsupportedOperationException();
  }

  @Override public Path getFileName() {
    throw new UnsupportedOperationException();
  }

  @Override public Path getParent() {
    throw new UnsupportedOperationException();
  }

  @Override public int getNameCount() {
    throw new UnsupportedOperationException();
  }

  @Override public Path getName(int index) {
    throw new UnsupportedOperationException();
  }

  @Override public Path subpath(int beginIndex, int endIndex) {
    throw new UnsupportedOperationException();
  }

  @Override public boolean startsWith(Path other) {
    throw new UnsupportedOperationException();
  }

  @Override public boolean startsWith(String other) {
    throw new UnsupportedOperationException();
  }

  @Override public boolean endsWith(Path other) {
    throw new UnsupportedOperationException();
  }

  @Override public boolean endsWith(String other) {
    throw new UnsupportedOperationException();
  }

  @Override public Path normalize() {
    throw new UnsupportedOperationException();
  }

  @Override public Path resolve(Path other) {
    throw new UnsupportedOperationException();
  }

  @Override public Path resolve(String other) {
    throw new UnsupportedOperationException();
  }

  @Override public Path resolveSibling(Path other) {
    throw new UnsupportedOperationException();
  }

  @Override public Path resolveSibling(String other) {
    throw new UnsupportedOperationException();
  }

  @Override public Path relativize(Path other) {
    throw new UnsupportedOperationException();
  }

  @Override public URI toUri() {
    return uri;
  }

  @Override public Path toAbsolutePath() {
    return this;
  }

  @Override public Path toRealPath(LinkOption... options) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override public File toFile() {
    return new File(uri);
  }

  @Override public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events,
      WatchEvent.Modifier... modifiers) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override public WatchKey register(WatchService watcher, WatchEvent.Kind<?>... events)
      throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override public Iterator<Path> iterator() {
    throw new UnsupportedOperationException();
  }

  @Override public int compareTo(Path other) {
    return uri.compareTo(other.toUri());
  }

  @Override public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof ResourcePath)) {
      return false;
    }
    ResourcePath other = (ResourcePath) obj;
    return uri.equals(other.uri);
  }

  @Override public int hashCode() {
    return uri.hashCode();
  }
}
