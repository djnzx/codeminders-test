package org.alexr.kata13.counter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileStateTest {

  private final FileState fs0 = FileState.fresh();

  @Test
  void fresh_constructor() {
    assertEquals(0, fs0.count);
    assertFalse(fs0.inBlock);
  }

  @Test
  void updated_1() {
    assertEquals(1, fs0.updated(1).count);
    assertEquals(1, fs0.updated(1).updated(0).count);
    assertEquals(3, fs0.updated(1).updated(1).updated(1).count);
  }

  @Test
  void updated_2() {
    FileState fs1 = fs0.updated(1, true);
    assertTrue(fs1.inBlock);
    assertEquals(2, fs1.updated(1).count);
  }

  @Test
  void updated_3() {
    FileState fs1 = fs0.updated(1, true).updated(1, false);
    assertFalse(fs1.inBlock);
    assertEquals(2, fs1.count);
  }

  @Test
  void testEquals1() {
    assertEquals(
        fs0.updated(5),
        fs0.updated(2).updated(3)
    );
  }

  @Test
  void testEquals2() {
    assertEquals(
        fs0.updated(5, true),
        fs0.updated(2, true).updated(3)
    );
    assertEquals(
        fs0.updated(5, true),
        fs0.updated(2).updated(3, true)
    );
    assertEquals(
        fs0.updated(5, true).updated(0, false),
        fs0.updated(6, true).updated(-3).updated(2, false)
    );
  }
}
