package org.alexr.kata13.util;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.alexr.kata13.util.Functions.*;

class FunctionsTest {

  @Test
  void indent0a() {
    assertEquals(
        "",
        indent(5, 0)
    );
  }

  @Test
  void indent0b() {
    assertEquals(
        "",
        indent(0, 5)
    );
  }

  @Test
  void indent10a() {
    assertEquals(
        "          ",
        indent(5, 2)
    );
  }

  @Test
  void indent15a() {
    assertEquals(
        "               ",
        indent(5, 3)
    );
  }

  @Test
  void indent2a() {
    assertEquals(
        "  ",
        indent(1, 2)
    );
  }

  @Test
  void indent2b() {
    assertEquals(
        "    ",
        indent(2, 2)
    );
  }

  @Test
  void indent2c() {
    assertEquals(
        "      ",
        indent(3, 2)
    );
  }

  @Test
  void lastChunk1() {
    assertEquals(
        "file1.java",
        lastChunk(new File("file1.java"))
    );
  }

  @Test
  void lastChunk2() {
    assertEquals(
        "file2.java",
        lastChunk(new File("a/b/file2.java"))
    );
  }

  @Test
  void lastChunk3() {
    assertEquals(
        "src",
        lastChunk(new File("/usr/src"))
    );
  }

}
