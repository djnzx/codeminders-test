package org.alexr.kata13.strip.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineStateTest {

  private final LineState freshT = LineState.fresh("public class Item {", true);
  private final LineState freshF = LineState.fresh("public class Item {", false);

  @Test
  void fresh_constructor1() {
    assertTrue(freshT.inBlock);
    assertFalse(freshT.inString);
  }

  @Test
  void fresh_constructor2() {
    assertFalse(freshF.inBlock);
    assertFalse(freshF.inString);
  }

  @Test
  void swBlock1() {
    assertTrue(
        freshF.swBlock().inBlock
    );
  }

  @Test
  void swBlock2() {
    assertFalse(
        freshT.swBlock().inBlock
    );
  }

  @Test
  void swString1() {
    assertTrue(
        freshT.swString().inString
    );
  }

  @Test
  void swString2() {
    assertFalse(
        freshT.swString().swString().inString
    );
  }

  @Test
  void moveTo_and_shift() {
    assertEquals(
        freshT.moveTo(4),
        freshT.shift(2).shift(2)
    );
    assertEquals(
        freshT.moveTo(4),
        freshT.shift(6).shift(-2)
    );
    assertEquals(
        freshT.moveTo(0),
        freshT.shift(6).shift(-6)
    );
  }

  @Test
  void saveTo1() {
    LineState fresh2 = freshT.saveTo(6);
    assertEquals(
        "public",
        fresh2.result()
    );
  }

  @Test
  void saveTo2() {
    LineState fresh2 = freshT.saveTo(6).saveTo(12);
    assertEquals(
        "public class",
        fresh2.result()
    );
  }

  @Test
  void moveTo_saveTo2() {
    LineState fresh2 = freshT.moveTo(7).saveTo(12);
    assertEquals(
        "class",
        fresh2.result()
    );
  }

  @Test
  void skipRest1() {
    LineState state2 = freshT.moveTo(7).saveTo(12).skipRest();
    assertTrue(state2.isDone());
    assertEquals("class", state2.result());
  }

  @Test
  void skipRest2() {
    LineState state2 = freshT.skipRest();
    assertTrue(state2.isDone());
    assertEquals("", state2.result());
  }

  @Test
  void saveRest1() {
    LineState state1 = freshT.saveRest();
    assertTrue(state1.isDone());
    assertEquals("public class Item {", state1.result());
  }

  @Test
  void saveRest2() {
    LineState state1 = freshT.moveTo(7).saveRest();
    assertTrue(state1.isDone());
    assertEquals("class Item {", state1.result());
  }

  @Test
  void find1() {
    assertEquals(7, freshT.find("class"));
    assertEquals(0, freshT.find("public"));
  }

  @Test
  void find2() {
    LineState state = freshT.moveTo(7);
    assertEquals(7, state.find("class"));
    assertEquals(-1, state.find("public"));
  }

  @Test
  void testEquals() {
    assertEquals(
        freshF.swBlock(),
        freshT
    );
  }
}
