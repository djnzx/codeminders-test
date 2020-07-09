package org.alexr.kata13.val;

import java.util.Arrays;

public final class RowInfo {
  public final int level;
  public final String name;
  public final long count;

  private final int LEVEL_SIZE = 2;

  public RowInfo(int level, String name, long count) {
    this.level = level;
    this.name = name;
    this.count = count;
  }

  public String indent() {
    byte[] bs = new byte[level * LEVEL_SIZE];
    Arrays.fill(bs, (byte) ' ');
    return new String(bs);
  }

  @Override
  public String toString() {
    return String.format("%s%s : %d",
        indent(), name, count);
  }
}

