package org.alexr.kata13.strip;

import org.alexr.kata13.strip.state.LineState;
import org.alexr.kata13.val.Pair;
import org.alexr.kata13.strip.Token.*;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.alexr.kata13.strip.state.STATE.*;
import static org.alexr.kata13.util.Predef.WRONG_SYNTAX;
import static org.alexr.kata13.util.Predef.WRONG_STATE;

public final class Strip5Wisely implements Strip {

  private interface FLL extends Function<LineState, LineState> {}
  private interface FOTFLL extends Function<Optional<Token>, FLL> {}

  private static Stream<BiFunction<String, Integer, Optional<Token>>> possibleTokensByState(LineState ls) {
    switch (ls.state()) {
      case CODE:   return Stream.of(TkQuote::find, TkBlockOp::find, TkLine::find);
      case BLOCK:  return Stream.of(TkBlockCl::find);
      case STRING: return Stream.of(TkQuote::find, TkXQuote::find);
    }
    throw WRONG_STATE;
  }

  private static Optional<Token> findFirst(LineState ls) {
    return possibleTokensByState(ls)
        .map(f -> f.apply(ls.input, ls.pos))
        .flatMap(Optional::stream)
        .min(Comparator.comparingInt(tk -> tk.at));
  }

  public LineState process(LineState lst) {
    return Stream.of(
        Pair.of(CODE, (FOTFLL) ot -> ot.map((Function<Token, FLL>) t ->
            t instanceof TkBlockOp ? ls -> ls.saveTo(t.at).shift(t.len).swBlock() :
            t instanceof TkLine    ? ls -> ls.saveTo(t.at).skipRest() :
            t instanceof TkQuote   ? ls -> ls.saveTo(t.at + t.len).swString() :
                                     ls -> { throw WRONG_SYNTAX; }
        ).orElseGet(() ->            ls -> ls.saveRest())),
        Pair.of(STRING, (FOTFLL) ot -> ot.map((Function<Token, FLL>) t ->
            t instanceof TkXQuote  ? ls -> ls.saveTo(t.at + t.len) :
            t instanceof TkQuote   ? ls -> ls.saveTo(t.at + t.len).swString() :
                                     ls -> { throw WRONG_SYNTAX; }
        ).orElseGet(() ->            ls -> ls.saveRest())),
        Pair.of(BLOCK, (FOTFLL) ot -> ot.map((Function<Token, FLL>) t ->
            t instanceof TkBlockCl ? ls -> ls.moveTo(t.at).shift(t.len).swBlock() :
                                     ls -> { throw WRONG_SYNTAX; }
        ).orElseGet(() ->            ls -> ls.skipRest()))
    )
        .filter(x -> x.a == lst.state())
        .findFirst()
        .map(x -> x.b.apply(findFirst(lst)))
        .map(f -> f.apply(lst))
        .orElseThrow(() -> WRONG_STATE);
  }

}
