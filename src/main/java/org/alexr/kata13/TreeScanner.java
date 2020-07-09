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
  private final Function<File, Boolean> fileFilter;

  public TreeScanner(File root, Function<File, Long> counter, Function<File, Boolean> fileFilter) {
    this.root = root;
    this.counter = counter;
    this.fileFilter = fileFilter;
  }

  static String last(File f) {
    String[] chunks = f.toPath().toString().split("/");
    return chunks[chunks.length-1];
  }

  public Node scan(File file, int level) {
    if (file.isFile() && fileFilter.apply(file)) return new Node.NFile(file, level, counter);
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      List<Node> nodes = files == null ? Collections.emptyList() : Arrays.stream(files)
          .map(f -> scan(f, level + 1))
          .filter(n -> !(n instanceof Node.NOther))
          .filter(n -> !(n instanceof Node.NFolder && n.children.isEmpty()))
          .collect(Collectors.toList());
      long count = nodes.stream()
          .map(n -> n.count)
          .reduce(Long::sum)
          .orElse(0L);
      return new Node.NFolder(file, level, count, nodes);
    }
    return new Node.NOther(file, level);
  }

  public List<RowInfo> represent(Node node, List<RowInfo> items) {
    items.add(new RowInfo(node.level, last(node.file), node.count));
    node.children.stream()
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
