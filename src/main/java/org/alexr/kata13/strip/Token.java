package org.alexr.kata13.strip;

import org.alexr.kata13.strip.state.LineState;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static org.alexr.kata13.util.Predef.WRONG_STATE;

public abstract class Token implements Patterns {
  public final int at;
  public final int len;

  protected Token(int at, int len) {
    this.at = at;
    this.len = len;
  }

  private static Stream<BiFunction<String, Integer, Optional<Token>>> possibleTokensByState(LineState ls) {
    switch (ls.state()) {
      case CODE:   return Stream.of(TkQuote::find, TkBlockOp::find, TkLine::find);
      case BLOCK:  return Stream.of(TkBlockCl::find);
      case STRING: return Stream.of(TkQuote::find, TkXQuote::find);
    }
    throw WRONG_STATE;
  }

  public static Optional<Token> findFirst(LineState ls) {
    return possibleTokensByState(ls)
        .map(f -> f.apply(ls.input, ls.pos))
        .flatMap(Optional::stream)
        .min(Comparator.comparingInt(tk -> tk.at));
  }

  public static class TkQuote extends Token {
    protected TkQuote(int at) {
      super(at, QUOTE.length());
    }
    private static Optional<Token> find(String where, int from) {
      return Optional.of(where.indexOf(QUOTE, from)).filter(x -> x >= 0).map(TkQuote::new);
    }
  }

  public static class TkXQuote extends Token {
    protected TkXQuote(int at) {
      super(at, XQUOTE.length());
    }
    private static Optional<Token> find(String where, int from) {
      return Optional.of(where.indexOf(XQUOTE, from)).filter(x -> x >= 0).map(TkXQuote::new);
    }
  }

  public static class TkBlockOp extends Token {
    protected TkBlockOp(int at) {
      super(at, OPEN.length());
    }
    private static Optional<Token> find(String where, int from) {
      return Optional.of(where.indexOf(OPEN, from)).filter(x -> x >= 0).map(TkBlockOp::new);
    }
  }

  public static class TkBlockCl extends Token {
    protected TkBlockCl(int at) {
      super(at, CLOSE.length());
    }
    private static Optional<Token> find(String where, int from) {
      return Optional.of(where.indexOf(CLOSE, from)).filter(x -> x >= 0).map(TkBlockCl::new);
    }
  }

  public static class TkLine extends Token {
    protected TkLine(int at) {
      super(at, LINE.length());
    }
    private static Optional<Token> find(String where, int from) {
      return Optional.of(where.indexOf(LINE, from)).filter(x -> x >= 0).map(TkLine::new);
    }
  }

}
