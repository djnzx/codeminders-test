package org.alexr.kata13.strip.state;

public enum STATE {
  CODE,
  BLOCK,
  STRING;

  public static STATE state(LineState ls) {
    if (ls.inString) return STRING;
    if (ls.inBlock) return  BLOCK;
    return CODE;
  }

}

