package org.alexr.kata13.strip.other;

import org.alexr.kata13.strip.Strip;
import org.alexr.kata13.strip.state.LineState;

/**
 * StripNothing implementation
 * just keeps original text
 */
public final class Strip1Nothing implements Strip {
  @Override
  public LineState process(LineState ls) {
    return ls.saveRest();
  }
}
