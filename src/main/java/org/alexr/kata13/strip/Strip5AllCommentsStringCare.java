package org.alexr.kata13.strip;

import org.alexr.kata13.strip.state.LineState;

/**
 * StripAllCommentsStringCare implementation
 * removes line comments
 * removes block comments
 * based on StripAllComments
 */
public final class Strip5AllCommentsStringCare implements Strip, Patterns {

  private boolean isQuote(int op_pos, int ln_pos, int q_pos) {
    return q_pos >= 0
        && (q_pos < op_pos || op_pos < 0)
        && (q_pos < ln_pos || ln_pos < 0);
  }

  private boolean isOpen(int op_pos, int ln_pos, int q_pos) {
    return op_pos >= 0
        && (op_pos < ln_pos || ln_pos < 0)
        && (op_pos <  q_pos ||  q_pos < 0);
  }

  private boolean isLine(int op_pos, int ln_pos, int q_pos) {
    return ln_pos >= 0
        && (ln_pos <  q_pos || q_pos  < 0)
        && (ln_pos < op_pos || op_pos < 0);
  }

  public LineState process(LineState ls) {
    if (ls.inString) {
      final int xq_pos = ls.find(XQUOTE);
      if (xq_pos >= 0) return ls.saveTo(xq_pos + XQUOTE.length());
      final int q_pos = ls.find(QUOTE);
      if (q_pos >= 0) return ls.saveTo(q_pos + QUOTE.length()).swString();
      return ls.saveRest();
    }
    if (ls.inBlock) {
      final int cl_pos = ls.find(CLOSE);
      return cl_pos >= 0 ? ls.moveTo(cl_pos).shift(CLOSE.length()).swBlock() :
                           ls.skipRest();
    }
    final int  q_pos = ls.find(QUOTE);
    final int op_pos = ls.find(OPEN);
    final int ln_pos = ls.find(LINE);
    return
        isQuote(op_pos, ln_pos, q_pos) ? ls.saveTo(q_pos + QUOTE.length()).swString() :
        isOpen (op_pos, ln_pos, q_pos) ? ls.saveTo(op_pos).shift(OPEN.length()).swBlock() :
        isLine (op_pos, ln_pos, q_pos) ? ls.saveTo(ln_pos).skipRest() :
                                         ls.saveRest();
  }

}
