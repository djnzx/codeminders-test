package org.alexr.kata13;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.alexr.kata13.TestEnv.rootFolderAsFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LineCounterTest {

  @Test
  void processParallel() {
    Configuration conf = new Configuration();
    LineCounter lc = new LineCounter(conf::fileFilter, conf.counter()::count);

    assertEquals(Arrays.asList(
        "test-classes : 54",
        "  Strip39.java : 39",
        "  Test3.java : 3",
        "  Test5.java : 5",
        "  Tricky7.java : 7",
        "  zerolines : 0",
        "    ABC.java : 0"
        ),
        lc.processParallel(rootFolderAsFile()).collect(Collectors.toList())
    );
  }

}
