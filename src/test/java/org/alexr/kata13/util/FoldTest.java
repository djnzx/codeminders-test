package org.alexr.kata13.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FoldTest {

  private List<Integer> data1 = Arrays.asList(1,2,3,4,5);
  private List<String> data2 = Arrays.asList("Hello", " ", "World");

  @Test
  void fold_iterable_1a() {
    assertEquals(
        15,
        Fold.fold(data1, 0, Integer::sum)
    );
  }

  @Test
  void fold_iterable_1b() {
    assertEquals(
        "message: Hello World",
        Fold.fold(data2, "message: ", (s1, s2) -> s1 + s2)
    );
  }

  @Test
  void fold_stream_1a() {
    assertEquals(
        15,
        Fold.fold(
            IntStream.rangeClosed(1,5).boxed()
            , 0, Integer::sum)
    );
  }

  @Test
  void fold_stream_1b() {
    assertEquals(
        "sequence: abcdefghijklmnopqrstuvwxyz",
        Fold.fold(
            IntStream.rangeClosed('a', 'z').mapToObj(x -> String.valueOf((char)x))
            , "sequence: ", (s1, s2) -> s1 + s2)
    );
  }

}
