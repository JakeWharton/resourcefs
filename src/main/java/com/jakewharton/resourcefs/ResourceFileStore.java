package com.jakewharton.resourcefs;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;

import static java.util.Objects.requireNonNull;

final class ResourceFileStore extends FileStore {
  @Override public String name() {
    return "resources";
  }

  @Override public String type() {
    return "resourcefs";
  }

  @Override public boolean isReadOnly() {
    return true;
  }

  @Override public long getTotalSpace() throws IOException {
    return 0;
  }

  @Override public long getUsableSpace() throws IOException {
    return 0;
  }

  @Override public long getUnallocatedSpace() throws IOException {
    return 0;
  }

  @Override public boolean supportsFileAttributeView(Class<? extends FileAttributeView> type) {
    return type == BasicFileAttributeView.class;
  }

  @Override public boolean supportsFileAttributeView(String name) {
    return "basic".equals(name);
  }

  @Override public <V extends FileStoreAttributeView> V getFileStoreAttributeView(Class<V> type) {
    requireNonNull(type);
    return null;
  }

  @Override public Object getAttribute(String attribute) throws IOException {
    throw new UnsupportedOperationException("Unsupported attribute: " + attribute);
  }
}
