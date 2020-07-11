package org.alexr.kata13.process;

import org.alexr.kata13.val.Node;
import org.alexr.kata13.val.RowInfo;

import java.util.stream.Stream;

import static org.alexr.kata13.util.Functions.lastChunk;

public class RepresentOutcome {
  /**
   * represent - lazy
   */
  public static Stream<RowInfo> represent(Node node) {
    return Stream.concat(
        Stream.of(new RowInfo(node.level, lastChunk(node.file), node.count)),
        node.children.stream()
            .flatMap(RepresentOutcome::represent)
    );
  }
}
