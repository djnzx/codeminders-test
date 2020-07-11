package org.alexr.kata13;

import org.alexr.kata13.process.ParCounter;
import org.alexr.kata13.process.FolderCrawler;
import org.alexr.kata13.val.FNode;
import org.alexr.kata13.val.Node;
import org.alexr.kata13.val.RowInfo;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.alexr.kata13.process.RepresentOutcome.represent;

public class LineCounter {
  private final FolderCrawler treeBuilder;
  private final ParCounter counter;

  public LineCounter(Function<File, Boolean> fileFilter, Function<File, Long> countFn) {
    this.treeBuilder = new FolderCrawler(fileFilter);
    this.counter = new ParCounter(countFn);
  }

  public Stream<String> processParallel(File root) {
    FNode tree = treeBuilder.scan(root);
    Map<File, List<Long>> data = counter.parCount(tree);
    Node enriched = ParCounter.enrich(tree, 0, data);
    return represent(enriched)
        .map(RowInfo::toString);
  }

}
