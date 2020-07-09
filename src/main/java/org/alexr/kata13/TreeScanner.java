package org.alexr.kata13;

import org.alexr.kata13.val.Node;
import org.alexr.kata13.val.RowInfo;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeScanner {
  private final File root;
  private final Function<File, Long> counter;

  public TreeScanner(File root, Function<File, Long> counter) {
    this.root = root;
    this.counter = counter;
  }

  static String last(File f) {
    String[] chunks = f.toPath().toString().split("/");
    return chunks[chunks.length-1];
  }

  public boolean filteredExt(File fl) {
    return fl.isFile() && fl.toString().toLowerCase().endsWith(".java");
  }

  public Node scan(File file, int level) {
    if (file.isFile() && filteredExt(file)) return Node.File(file, level, counter);
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      List<Node> nodes = files == null ? Collections.emptyList() : Arrays.stream(files)
          .map(f -> scan(f, level + 1))
          .collect(Collectors.toList());
      long count = nodes.stream()
          .map(n -> n.count)
          .reduce(Long::sum)
          .orElse(0L);
      return Node.Folder(file, level, count, nodes);
    }
    return Node.Other(file, level);
  }

  public List<RowInfo> represent(Node node, List<RowInfo> items) {
    items.add(new RowInfo(node.level, last(node.file), node.count));
    node.children.stream()
        .filter(n -> n.count > 0)
        .sorted(Comparator.comparing(o -> last(o.file)))
        .forEach(n -> represent(n, items));
    return items;
  }

  public Stream<String> process() {
    return represent(
        scan(root, 0),
        new ArrayList<>()
    ).stream()
        .map(RowInfo::toString);
  }

}
