package org.alexr.kata13.process;

import org.alexr.kata13.Configuration;
import org.alexr.kata13.val.FNode;
import org.alexr.kata13.val.Node;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import static org.alexr.kata13.TestEnv.rootFolderAsFile;
import static org.alexr.kata13.util.Functions.fileFromRes;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParCounterTest {

  private final Configuration conf = new Configuration();
  private final FolderCrawler fc = new FolderCrawler(conf::fileFilter);
  private final ParCounter lc = new ParCounter(conf.counter()::count);

  File f1 = fileFromRes("Strip39.java");
  File f2 = fileFromRes("Test3.java");
  File f3 = fileFromRes("Test5.java");
  File f4 = fileFromRes("Tricky7.java");
  File fz = fileFromRes("zerolines");
  File fab= fileFromRes("zerolines/ABC.java");
  Node nfi1 = new Node.NFile(f1, 6, f -> 39L);
  Node nfi2 = new Node.NFile(f2, 6, f -> 3L);
  Node nfi3 = new Node.NFile(f3, 6, f -> 5L);
  Node nfi4 = new Node.NFile(f4, 6, f -> 7L);
  Node nabc = new Node.NFile(fab, 7, f -> 0L);
  Node nzero= new Node.NFolder(fz, 6, Arrays.asList(nabc));
  Node rootNodeExp = new Node.NFolder(rootFolderAsFile(), 5, Arrays.asList(nfi1, nfi2, nfi3, nfi4, nzero));

  private static List<Long> list(long val) {
    return new ArrayList<>(1) {{ add(val); }};
  }

  @Test
  public void parCount() {
    Map<File, List<Long>> exp = new HashMap<>();
    exp.put(f1, list(39L));
    exp.put(f2, list(3L));
    exp.put(f3, list(5L));
    exp.put(f4, list(7L));
    exp.put(fab, list(0L));
    Map<File, List<Long>> real = lc.parCount(fc.scan(rootFolderAsFile()));

    assertEquals(exp, real);
  }

  @Test
  public void enrich() {
    FNode tree = fc.scan(rootFolderAsFile());
    Map<File, List<Long>> map = lc.parCount(tree);
    Node enriched_real = ParCounter.enrich(tree, 5, map);

    assertEquals(rootNodeExp, enriched_real);
  }

}
