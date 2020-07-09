package org.alexr.kata13.util;

import java.io.File;
import java.util.Arrays;

public class Functions {

  public static String indent(int level, int LEVEL_SIZE) {
    byte[] bs = new byte[level * LEVEL_SIZE];
    Arrays.fill(bs, (byte) ' ');
    return new String(bs);
  }

  public static String lastChunk(File f) {
    String[] chunks = f.toPath().toString().split("/");
    return chunks[chunks.length - 1];
  }

}
