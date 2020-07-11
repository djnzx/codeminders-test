package org.alexr.kata13.strip;

import org.alexr.kata13.strip.state.LineState;

import static org.alexr.kata13.strip.state.STATE.state;
import static org.alexr.kata13.util.Predef.SYNTAX;

public final class Strip5Wisely implements Strip, Patterns {

  private LineState noTokenAction(LineState ls) {
    switch (state(ls)) {
      case BLOCK:  return ls.skipRest();
      case CODE:
      case STRING: return ls.saveRest();
    }
    throw SYNTAX;
  }

  public LineState process(LineState ls) {
    return ls.findFirstToken()
        .map(t -> t.modify(ls))
        .orElseGet(() -> noTokenAction(ls));
  }

}
