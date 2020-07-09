package org.alexr.kata13.count;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountAllTest {

  private Count counter;

  @BeforeEach
  void setUp() {
    counter = new CountAll();
  }

  @Test
  void count1() {
    assertEquals(1, counter.count("ABC"));
  }

  @Test
  void count2() {
    assertEquals(1, counter.count(""));
  }

  @Test
  void count3() {
    assertEquals(1, counter.count(" "));
  }

}
