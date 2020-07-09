package org.alexr.kata13.val;

import org.alexr.kata13.util.Functions;

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

  @Override
  public String toString() {
    return String.format("%s%s : %d",
        Functions.indent(level, LEVEL_SIZE), name, count);
  }
}

