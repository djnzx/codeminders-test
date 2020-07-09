package org.alexr.kata13.strip;

import org.alexr.kata13.strip.state.LineState;

public interface Strip {
  LineState process(LineState ls);
}
