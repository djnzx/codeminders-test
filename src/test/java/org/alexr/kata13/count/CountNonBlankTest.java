package org.alexr.kata13.count;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountNonBlankTest {

  private Count counter;

  @BeforeEach
  void setUp() {
    counter = new CountNonBlank();
  }

  @Test
  void count11() {
    assertEquals(1, counter.count("ABC"));
  }

  @Test
  void count12() {
    assertEquals(1, counter.count("  int i;"));
  }

  @Test
  void count13() {
    assertEquals(1, counter.count("\tAtomicReference x;"));
  }

  @Test
  void count01() {
    assertEquals(0, counter.count(""));
  }

  @Test
  void count02() {
    assertEquals(0, counter.count(" "));
  }

  @Test
  void count03() {
    assertEquals(0, counter.count("\t"));
  }

  @Test
  void count04() {
    assertEquals(0, counter.count("\n"));
  }

}
