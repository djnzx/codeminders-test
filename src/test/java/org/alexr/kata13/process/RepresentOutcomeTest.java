package org.alexr.kata13.process;

import org.alexr.kata13.Configuration;
import org.alexr.kata13.val.FNode;
import org.alexr.kata13.val.Node;
import org.alexr.kata13.val.RowInfo;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.alexr.kata13.TestEnv.rootFolderAsFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RepresentOutcomeTest {

  @Test
  void represent() {
    final Configuration conf = new Configuration();
    final FolderCrawler fc = new FolderCrawler(conf::fileFilter);
    final ParCounter lc = new ParCounter(conf.counter()::count);

    FNode tree = fc.scan(rootFolderAsFile());
    Map<File, List<Long>> map = lc.parCount(tree);
    Node data = ParCounter.enrich(tree, 5, map);
    Stream<RowInfo> represented = RepresentOutcome.represent(data);

    assertEquals(
        Arrays.asList(
            new RowInfo(5, "test-classes", 54),
            new RowInfo(6, "Strip39.java", 39),
            new RowInfo(6, "Test3.java", 3),
            new RowInfo(6, "Test5.java", 5),
            new RowInfo(6, "Tricky7.java", 7),
            new RowInfo(6, "zerolines", 0),
            new RowInfo(7, "ABC.java", 0)
        ),
        represented.collect(Collectors.toList())
    );

  }
}
