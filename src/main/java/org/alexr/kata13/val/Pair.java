package org.alexr.kata13.val;

import lombok.Value;

@Value
public class Pair<A, B> {
  public A a;
  public B b;

  public static <A, B> Pair<A, B> of(A a, B b) {
    return new Pair<>(a, b);
  }
}
