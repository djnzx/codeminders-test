package org.alexr.kata13.strip;

import org.alexr.kata13.strip.state.LineState;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static org.alexr.kata13.util.Predef.SYNTAX;

public abstract class Token implements Patterns {
  public final int at;

  protected Token(int at) {
    this.at = at;
  }

  private static Stream<BiFunction<String, Integer, Optional<Token>>> possibleTokensByState(LineState ls) {
    switch (ls.state()) {
      case CODE:   return Stream.of(TkQuote::find, TkBlockOp::find, TkLine::find);
      case BLOCK:  return Stream.of(TkBlockCl::find);
      case STRING: return Stream.of(TkQuote::find, TkXQuote::find);
    }
    throw SYNTAX;
  }

  private static Stream<Token> findAll(LineState ls) {
    return possibleTokensByState(ls)
        .map(f -> f.apply(ls.input, ls.pos))
        .flatMap(Optional::stream);
  }

  public static Optional<Token> findFirst(LineState ls) {
    return findAll(ls).min(Comparator.comparingInt(tk -> tk.at));
  }

  public abstract LineState modify(LineState ls);

  // =======================================
  public static class TkQuote extends Token {

    protected TkQuote(int at) {
      super(at);
    }

    private static Optional<Token> find(String where, int from) {
      return Optional.of(where.indexOf(QUOTE, from)).filter(x -> x >= 0).map(TkQuote::new);
    }

    @Override
    public LineState modify(LineState ls) {
      switch (ls.state()) {
        case CODE:
        case STRING: return ls.saveTo(at + QUOTE.length()).swString();
      }
      throw SYNTAX;
    }
  }

  // =======================================
  public static class TkXQuote extends Token {

    protected TkXQuote(int at) {
      super(at);
    }

    private static Optional<Token> find(String where, int from) {
      return Optional.of(where.indexOf(XQUOTE, from)).filter(x -> x >= 0).map(TkXQuote::new);
    }

    @Override
    public LineState modify(LineState ls) {
      switch (ls.state()) {
        case STRING: return ls.saveTo(at + XQUOTE.length());
      }
      throw SYNTAX;
    }

  }

  // =======================================
  public static class TkBlockOp extends Token {
    protected TkBlockOp(int at) {
      super(at);
    }

    private static Optional<Token> find(String where, int from) {
      return Optional.of(where.indexOf(OPEN, from)).filter(x -> x >= 0).map(TkBlockOp::new);
    }

    @Override
    public LineState modify(LineState ls) {
      switch (ls.state()) {
        case CODE:   return ls.saveTo(at).shift(OPEN.length()).swBlock();
      }
      throw SYNTAX;
    }
  }

  // =======================================
  public static class TkBlockCl extends Token {
    protected TkBlockCl(int at) {
      super(at);
    }

    private static Optional<Token> find(String where, int from) {
      return Optional.of(where.indexOf(CLOSE, from)).filter(x -> x >= 0).map(TkBlockCl::new);
    }

    @Override
    public LineState modify(LineState ls) {
      switch (ls.state()) {
        case BLOCK:  return ls.moveTo(at).shift(CLOSE.length()).swBlock();
      }
      throw SYNTAX;
    }
  }

  // =======================================
  public static class TkLine extends Token {
    protected TkLine(int at) {
      super(at);
    }

    private static Optional<Token> find(String where, int from) {
      return Optional.of(where.indexOf(LINE, from)).filter(x -> x >= 0).map(TkLine::new);
    }

    @Override
    public LineState modify(LineState ls) {
      switch (ls.state()) {
        case CODE:   return ls.saveTo(at).skipRest();
      }
      throw SYNTAX;
    }
  }

}
