package org.alexr.kata13.val;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RowInfoTest {

  @Test
  void testToString1() {
    assertEquals(
        "home : 123",
        new RowInfo(0, "home", 123).toString()
    );
  }

  @Test
  void testToString2() {
    assertEquals(
        "  alexr : 10000000",
        new RowInfo(1, "alexr", 10_000_000).toString()
    );
  }

  @Test
  void testToString3() {
    assertEquals(
        "    movies : 0",
        new RowInfo(2, "movies", 0).toString()
    );
  }

}
