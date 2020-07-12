package org.alexr.kata13.strip;

import org.alexr.kata13.strip.state.LineState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Strip5AllCommentsStringCareTest {

  private final Strip strip = new Strip5Wisely();

  private final LineState s1 = LineState.fresh("// This file contains 3 lines of code", false);
  private final LineState s2 = LineState.fresh("public interface Dave {", false);
  private final LineState s3 = LineState.fresh("  /**", false);
  private final LineState s4 = LineState.fresh("   * count the number of lines in a file", true);
  private final LineState s5 = LineState.fresh("   */", true);
  private final LineState s6 = LineState.fresh("  int countLines(File inFile); // not the real signature!", false);
  private final LineState s7 = LineState.fresh("}", false);

  @Test
  void process1() {
    LineState s1a = strip.apply(s1);
    assertEquals("", s1a.result());
    assertFalse(s1a.inBlock);
    assertFalse(s1a.inString);
    assertTrue(s1a.isDone());
  }

  @Test
  void process2() {
    LineState s2a = strip.apply(s2);
    assertEquals("public interface Dave {", s2a.result());
    assertFalse(s2a.inBlock);
    assertFalse(s2a.inString);
    assertTrue(s2a.isDone());
  }

  @Test
  void process3() {
    LineState s3a = strip.apply(s3);
    assertEquals("  ", s3a.result());
    assertTrue(s3a.inBlock);
    assertFalse(s3a.inString);
    assertFalse(s3a.isDone());
  }

  @Test
  void process4() {
    LineState s4a = strip.apply(s4);
    assertEquals("", s4a.result());
    assertTrue(s4a.inBlock);
    assertFalse(s4a.inString);
    assertTrue(s4a.isDone());
  }

  @Test
  void process5() {
    LineState s5a = strip.apply(s5);
    assertEquals("", s5a.result());
    assertTrue(s5a.isDone());
    assertFalse(s5a.inBlock);
    assertFalse(s5a.inString);
  }

  @Test
  void process6() {
    LineState s6a = strip.apply(s6);
    assertEquals("  int countLines(File inFile); ", s6a.result());
    assertTrue(s6a.isDone());
    assertFalse(s6a.inBlock);
    assertFalse(s6a.inString);
  }

  @Test
  void process7() {
    LineState s7a = strip.apply(s7);
    assertEquals("}", s7a.result());
    assertTrue(s7a.isDone());
    assertFalse(s7a.inBlock);
    assertFalse(s7a.inString);
  }

  @Test
  void processX10() {
    LineState s10 = LineState.fresh("String s = \"hello/*\"", false);
    assertEquals("", s10.result());
    assertFalse(s10.isDone());
    assertFalse(s10.inBlock);
    assertFalse(s10.inString);

    LineState s10a = strip.apply(s10);
    assertEquals("String s = \"", s10.result());
    assertFalse(s10a.isDone());
    assertFalse(s10a.inBlock);
    assertTrue(s10a.inString);

    LineState s10b = strip.apply(s10a);
    assertEquals("String s = \"hello/*\"", s10b.result());
    assertTrue(s10b.isDone());
    assertFalse(s10b.inBlock);
    assertFalse(s10b.inString);
  }

  @Test
  void processX11() {
    LineState s10 = LineState.fresh("String s = \"hell\\\"o/*\"", false);
    // initial state
    assertEquals("", s10.result());
    assertFalse(s10.isDone());
    assertFalse(s10.inBlock);
    assertFalse(s10.inString);

    // 1-st pass
    LineState s10a = strip.apply(s10);
    assertEquals("String s = \"", s10.result());
    assertFalse(s10a.isDone());
    assertFalse(s10a.inBlock);
    assertTrue(s10a.inString);

    // 2-nd pass
    LineState s10b = strip.apply(s10a);
    assertEquals("String s = \"hell\\\"", s10b.result());
    assertFalse(s10b.isDone());
    assertFalse(s10b.inBlock);
    assertTrue(s10b.inString);

    // 3-rd pass
    LineState s10c = strip.apply(s10b);
    assertEquals("String s = \"hell\\\"o/*\"", s10c.result());
    assertTrue(s10c.isDone());
    assertFalse(s10c.inBlock);
    assertFalse(s10c.inString);
  }

  @Test
  void processX12() {
    LineState s10 = LineState.fresh("String s = \"wrong syntax", false);
    // initial state
    assertEquals("", s10.result());
    assertFalse(s10.isDone());
    assertFalse(s10.inBlock);
    assertFalse(s10.inString);

    // 1-st pass
    LineState s10a = strip.apply(s10);
    assertEquals("String s = \"", s10.result());
    assertFalse(s10a.isDone());
    assertFalse(s10a.inBlock);
    assertTrue(s10a.inString);

    // 2-nd pass
    LineState s10b = strip.apply(s10a);
    assertEquals("String s = \"wrong syntax", s10b.result());
    assertTrue(s10b.isDone());
    assertFalse(s10b.inBlock);
    assertTrue(s10b.inString);
  }
}
