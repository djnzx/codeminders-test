package org.alexr.kata13.val;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class Node {
  public final File file;
  public final int level;
  public final long count;
  public final List<Node> children;

  private Node(File file, int level, long count, List<Node> children) {
    this.file = file;
    this.level = level;
    this.count = count;
    this.children = children;
  }

  public static Node File(File file, int level, Function<File, Long> counter) {
    return new Node(file, level, counter.apply(file), Collections.emptyList()) {};
  }

  public static Node Folder(File file, int level, long count, List<Node> children) {
    return new Node(file, level, count, children) {};
  }

  public static Node Other(File file, int level) {
    return new Node(file, level, 0, Collections.emptyList()) {};
  }

}

