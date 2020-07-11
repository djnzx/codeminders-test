package org.alexr.kata13.process;

import org.alexr.kata13.Configuration;
import org.alexr.kata13.val.FNode;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.alexr.kata13.TestEnv.rootFolderAsFile;
import static org.alexr.kata13.util.Functions.fileFromRes;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FolderCrawlerTest {

  private final Configuration conf = new Configuration();
  private final FolderCrawler fc = new FolderCrawler(conf::fileFilter);

  private final File f1 = fileFromRes("Strip39.java");
  private final File f2 = fileFromRes("Test3.java");
  private final File f3 = fileFromRes("Test5.java");
  private final File f4 = fileFromRes("Tricky7.java");
  private final File fz = fileFromRes("zerolines");
  private final File fab= fileFromRes("zerolines/ABC.java");
  private final FNode nfi1 = new FNode.FFile(f1);
  private final FNode nfi2 = new FNode.FFile(f2);
  private final FNode nfi3 = new FNode.FFile(f3);
  private final FNode nfi4 = new FNode.FFile(f4);
  private final FNode nabc = new FNode.FFile(fab);
  private final FNode nzero= new FNode.FFolder(fz, Arrays.asList(nabc));

  @Test
  void scan() {
    FNode rootNodeExp = new FNode.FFolder(rootFolderAsFile(), Arrays.asList(nfi1, nfi2, nfi3, nfi4, nzero));
    FNode real = fc.scan(rootFolderAsFile());
    assertEquals(rootNodeExp, real);
  }

  @Test()
  void flatten() {
    List<FNode> exp = Arrays.asList(nfi1, nfi2, nfi3, nfi4, nabc);
    List<FNode> real = FolderCrawler.flatten(fc.scan(rootFolderAsFile())).collect(Collectors.toList());
    assertEquals(exp, real);
  }
}
