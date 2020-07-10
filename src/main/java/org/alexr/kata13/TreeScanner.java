package org.alexr.kata13;

import org.alexr.kata13.val.Node;
import org.alexr.kata13.val.RowInfo;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.alexr.kata13.util.Functions.lastChunk;

public class TreeScanner {
  private final Function<File, Long> counter;
  private final Function<File, Boolean> fileFilter;

  public TreeScanner(Function<File, Long> counter, Function<File, Boolean> fileFilter) {
    this.counter = counter;
    this.fileFilter = fileFilter;
  }

  /**
   * scans given folder/file to
   * tree structure Node
   * it returns root node
   */
  public Node scan(File file, int level) {
    if (file.isFile() && fileFilter.apply(file)) return new Node.NFile(file, level, counter);
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      List<Node> nodes = files == null ? Collections.emptyList() : Arrays.stream(files)
          .map(f -> scan(f, level + 1))
          .filter(n ->
              n instanceof Node.NFile ||
              n instanceof Node.NFolder && !n.children.isEmpty())
          .sorted(Comparator.comparing(o -> lastChunk(o.file)))
          .collect(Collectors.toList());
      return new Node.NFolder(file, level, nodes);
    }
    return new Node.NOther(file, level);
  }

  /**
   * flattens given tree structure
   * Stream<RowInfo>
   */
  public Stream<RowInfo> represent(Node node) {
    return Stream.concat(
        Stream.of(new RowInfo(node.level, lastChunk(node.file), node.count)),
        node.children.stream()
            .flatMap(this::represent)
    );
  }

  /**
   * flattens given tree structure
   * Stream<RowInfo>
   */
  public Stream<String> process(File root) {
    return represent(scan(root, 0))
        .map(RowInfo::toString);
  }

}
