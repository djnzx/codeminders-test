package org.alexr.kata13.val;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeTest {

  private final File f1 = new File("file1.java");
  private final File f2 = new File("file2.java");
  private final File f3 = new File("file3.java");
  private final File f5 = new File("link");
  private final File fld = new File("src");

  private final Node nfi1 = new Node.NFile(f1, 3, f -> 1000L);
  private final Node nfi2 = new Node.NFile(f2, 3, f -> 200L);
  private final Node nfi3 = new Node.NFile(f3, 3, f -> 300L);
  private final List<Node> children = Arrays.asList(nfi1, nfi2, nfi3);
  private final Node nfo = new Node.NFolder(fld, 2, children);
  private final Node no = new Node.NOther(f5, 4);

  @Test
  public void test_other_constructor() {
    assertEquals(no.file, f5);
    assertEquals(no.level, 4);
    assertEquals(no.count, 0L);
    assertEquals(no.children, Collections.emptyList());
  }

  @Test
  public void test_file_constructor() {
    assertEquals(nfi1.file, f1);
    assertEquals(nfi1.level, 3);
    assertEquals(nfi1.count, 1000L);
    assertEquals(nfi1.children, Collections.emptyList());
  }

  @Test
  public void test_folder_constructor() {
    assertEquals(nfo.file, fld);
    assertEquals(nfo.level, 2);
    assertEquals(nfo.count, 1500L);
    assertEquals(nfo.children, children);
  }

}
