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
  private final File root;
  private final Function<File, Long> counter;
  private final Function<File, Boolean> fileFilter;

  public TreeScanner(File root, Function<File, Long> counter, Function<File, Boolean> fileFilter) {
    this.root = root;
    this.counter = counter;
    this.fileFilter = fileFilter;
  }

  public Node scan(File file, int level) {
    if (file.isFile() && fileFilter.apply(file)) return new Node.NFile(file, level, counter);
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      List<Node> nodes = files == null ? Collections.emptyList() : Arrays.stream(files)
          .map(f -> scan(f, level + 1))
          .filter(n ->
              n instanceof Node.NFile ||
              n instanceof Node.NFolder && !n.children.isEmpty())
          .collect(Collectors.toList());
      return new Node.NFolder(file, level, nodes);
    }
    return new Node.NOther(file, level);
  }

  public Stream<RowInfo> represent(Node node) {
    return Stream.concat(
        Stream.of(new RowInfo(node.level, lastChunk(node.file), node.count)),
        node.children.stream()
            .sorted(Comparator.comparing(o -> lastChunk(o.file)))
            .flatMap(this::represent)
    );
  }

  public Stream<String> process() {
    return represent(scan(root, 0))
        .map(RowInfo::toString);
  }

}
