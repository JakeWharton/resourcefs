package com.jakewharton.resourcefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public final class ResourceFileSystemProvider extends FileSystemProvider {
  static final String SCHEME = "resource";

  private final FileSystem fileSystem = new ResourceFileSystem(this);
  private final BasicFileAttributes fileAttributes = new ResourceFileAttributes();
  final FileStore fileStore = new ResourceFileStore();

  private void checkExists(Path path) throws FileNotFoundException {
    if (!path.isAbsolute()) {
      throw new IllegalArgumentException("Only absolute paths allowed: " + path.toUri().toString());
    }
    if (getClass().getResource(path.toUri().toString()) == null) {
      throw new FileNotFoundException(path.toUri().toString());
    }
  }

  @Override public String getScheme() {
    return SCHEME;
  }

  @Override public FileSystem newFileSystem(URI uri, Map<String, ?> env) throws IOException {
    return fileSystem;
  }

  @Override public FileSystem getFileSystem(URI uri) {
    return fileSystem;
  }

  @Override public Path getPath(URI uri) {
    if (!uri.getScheme().equalsIgnoreCase(SCHEME)) {
      throw new IllegalArgumentException("Scheme mismatch: " + uri.toString());
    }
    return new ResourcePath(fileSystem, uri);
  }

  @Override public InputStream newInputStream(Path path, OpenOption... options) throws IOException {
    checkExists(path);
    return getClass().getResourceAsStream(path.toUri().toString());
  }

  @Override public FileSystem newFileSystem(Path path, Map<String, ?> env) throws IOException {
    return fileSystem;
  }

  @Override public OutputStream newOutputStream(Path path, OpenOption... options)
      throws IOException {
    throw readOnly();
  }

  @Override public FileChannel newFileChannel(Path path, Set<? extends OpenOption> options,
      FileAttribute<?>... attrs) throws IOException {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public SeekableByteChannel newByteChannel(final Path path, Set<? extends OpenOption> options,
      FileAttribute<?>... attrs) throws IOException {
    try (InputStream is = newInputStream(path)) {
      byte[] bytes = Streams.toByteArray(is);
      return new SeekableByteArrayChannel(bytes);
    }
  }

  @Override public DirectoryStream<Path> newDirectoryStream(Path dir,
      DirectoryStream.Filter<? super Path> filter) throws IOException {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
    throw readOnly();
  }

  @Override public void delete(Path path) throws IOException {
    throw readOnly();
  }

  @Override public void copy(Path source, Path target, CopyOption... options) throws IOException {
    throw readOnly();
  }

  @Override public void move(Path source, Path target, CopyOption... options) throws IOException {
    throw readOnly();
  }

  @Override public boolean isSameFile(Path path, Path path2) throws IOException {
    return path.equals(path2);
  }

  @Override public boolean isHidden(Path path) throws IOException {
    checkExists(path);
    return false;
  }

  @Override public FileStore getFileStore(Path path) throws IOException {
    checkExists(path);
    return fileStore;
  }

  @Override public void checkAccess(Path path, AccessMode... modes) throws IOException {
    checkExists(path);
    for (AccessMode mode : modes) {
      if (mode == AccessMode.WRITE) {
        throw readOnly();
      } else if (mode == AccessMode.EXECUTE) {
        throw new AccessDeniedException("Resources are not executable.");
      }
    }
  }

  @Override public <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> type,
      LinkOption... options) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override public <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> type,
      LinkOption... options) throws IOException {
    if (type != BasicFileAttributes.class) {
      throw new IllegalArgumentException("Unsupported attributes: " + type.getCanonicalName());
    }
    checkExists(path);
    return type.cast(fileAttributes);
  }

  @Override
  public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options)
      throws IOException {
    checkExists(path);
    return Collections.emptyMap(); // TODO
  }

  @Override
  public void setAttribute(Path path, String attribute, Object value, LinkOption... options)
      throws IOException {
    throw readOnly();
  }

  private static IOException readOnly() throws IOException {
    throw new UnsupportedOperationException("Resources are read only.");
  }
}
