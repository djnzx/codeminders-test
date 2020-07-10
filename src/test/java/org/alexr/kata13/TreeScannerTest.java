package org.alexr.kata13;

import org.alexr.kata13.val.Node;
import org.alexr.kata13.val.RowInfo;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TreeScannerTest {

  private final Configuration conf = new Configuration();

  final File rootFolderPath = Paths.get("target", "test-classes").toAbsolutePath().toFile();

  private final TreeScanner ts = new TreeScanner(
      conf.counter()::count,
      conf::fileFilter
  );

  File f1 = fromRes("Strip39.java");
  File f2 = fromRes("Test3.java");
  File f3 = fromRes("Test5.java");
  File f4 = fromRes("Tricky7.java");
  File fz = fromRes("zerolines");
  File fab= fromRes("zerolines/ABC.java");
  Node nfi1 = new Node.NFile(f1, 6, f -> 39L);
  Node nfi2 = new Node.NFile(f2, 6, f -> 3L);
  Node nfi3 = new Node.NFile(f3, 6, f -> 5L);
  Node nfi4 = new Node.NFile(f4, 6, f -> 7L);
  Node nabc = new Node.NFile(fab, 7, f -> 0L);
  Node nzero= new Node.NFolder(fz, 6, Arrays.asList(nabc));
  Node rootNodeExp = new Node.NFolder(rootFolderPath, 5, Arrays.asList(nfi1, nfi2, nfi3, nfi4, nzero));

  private File fromRes(String fname) {
    return new File(this.getClass().getClassLoader().getResource(fname).getFile());
  }

  @Test
  void scan() {
    Node rootNodeReal = ts.scan(rootFolderPath, 5);
    assertEquals(rootNodeExp, rootNodeReal);
  }

  @Test
  void represent() {
    Node real = ts.scan(rootFolderPath, 5);
    Stream<RowInfo> represented = ts.represent(real);
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

  @Test
  void process() {
    assertEquals(Arrays.asList(
        "test-classes : 54",
        "  Strip39.java : 39",
        "  Test3.java : 3",
        "  Test5.java : 5",
        "  Tricky7.java : 7",
        "  zerolines : 0",
        "    ABC.java : 0"
        ),
        ts.process(rootFolderPath).collect(Collectors.toList())
    );
  }

}
