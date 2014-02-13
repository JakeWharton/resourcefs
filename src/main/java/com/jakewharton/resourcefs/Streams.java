package com.jakewharton.resourcefs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

final class Streams {
  static byte[] toByteArray(InputStream is) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buffer = new byte[4096];
    int count;
    while ((count = is.read(buffer)) != -1) {
      baos.write(buffer, 0, count);
    }
    return baos.toByteArray();
  }

  private Streams() {
    throw new AssertionError("No instances.");
  }
}
