package com.jakewharton.resourcefs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

final class SeekableByteArrayChannel implements SeekableByteChannel {
  private final byte[] bytes;
  private int position = 0;
  private boolean open = true;

  SeekableByteArrayChannel(byte[] bytes) {
    this.bytes = bytes;
  }

  @Override public int read(ByteBuffer dst) throws IOException {
    int remaining = bytes.length - position;
    dst.put(bytes, position, remaining);
    position += remaining;
    return remaining;
  }

  @Override public int write(ByteBuffer src) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override public long position() throws IOException {
    return position;
  }

  @Override public SeekableByteChannel position(long newPosition) throws IOException {
    if (newPosition > Integer.MAX_VALUE) {
      throw new IllegalArgumentException("newPosition too large: " + newPosition);
    }
    position = (int) newPosition;
    return this;
  }

  @Override public long size() throws IOException {
    return bytes.length;
  }

  @Override public SeekableByteChannel truncate(long size) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override public boolean isOpen() {
    return open;
  }

  @Override public void close() throws IOException {
    open = false;
  }
}
