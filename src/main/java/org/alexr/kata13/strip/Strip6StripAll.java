package org.alexr.kata13.strip;

import org.alexr.kata13.strip.state.LineState;

/**
 * StripAll implementation
 * just throws everything
 */
public final class Strip6StripAll implements Strip {
  @Override
  public LineState process(LineState ls) {
    return ls.skipRest();
  }
}
