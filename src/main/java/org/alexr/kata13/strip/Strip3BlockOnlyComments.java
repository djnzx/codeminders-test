package org.alexr.kata13.strip;

import org.alexr.kata13.strip.state.LineState;

/**
 * StripBlockOnlyComments implementation
 * removes block comments ONLY
 */
public final class Strip3BlockOnlyComments implements Strip, Patterns {
  @Override
  public LineState process(LineState ls) {
    if (ls.inBlock) {
      final int cl_pos = ls.find(CLOSE);
      return cl_pos >= 0 ?
          ls.moveTo(cl_pos).shift(CLOSE.length()).swBlock() :
          ls.skipRest();
    }
    final int op_pos = ls.find(OPEN);
    return op_pos >= 0 ?
        ls.saveTo(op_pos).shift(OPEN.length()).swBlock() :
        ls.saveRest();
  }
}
