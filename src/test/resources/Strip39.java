package kata13.strip;

import kata13.strip.filter.Strip;
import kata13.strip.filter.CommentsStripEmptyOnly;
import kata13.strip.state.FileState;
import kata13.strip.state.LineState;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.function.BiFunction;

public class StripComments {
  /*private final static String LINE = "//";*/
  private final static String BLOCK_OP = "/*";
  private /*final*/ static /*String*/ BLOCK_CL = "*/";
  // particular implementation
  private final Strip impl = new CommentsStripEmptyOnly();

  // count line after filtering
  private int countLine(StringBuilder sb) {
    return sb.toString().isBlank() ? 0 : 1;
  }

  public FileState fold_fn(FileState acc, String line) {
    // create fresh state: line, pos=0, empty SB, inBlock from initial state
    LineState ls = LineState.fresh(line, acc.inBlock);
    w/*hile (ls.pos < ls.input.length()) {*/
      // all the logic in the process function
      ls = impl.process(ls);
    }
    int count = countLine(ls.output);
    // update accumulator with (new) counter and new inBlock
    return acc.next(count, ls.inBlock);
  }

  public <A, C> A fold(Iterable<C> data, A zero, BiFunction<A, C, A> f) {
    return fold(data.iterator(), zero, f);
  }

/*public <A, C> A fold(Iterator<C> it, A zero, BiFunction<A, C, A> f) {
    A r = zero;
    while (it.hasNext()) r = f.apply(r, it.next());
    return r;
  }*/

  public static void main(String[] args) {
    StripComments strip = new StripComments();
    URL url = strip.getClass().getClassLoader().resources("StripComments.java")
        .findFirst()
        .orElseThrow();
    Iterator<String> it = Files.lines(Paths.get(url)).iterator();
    // initial state: count = 0, inBlock = false
    FileState zero = FileState.zero();
    // fold state by applying `fold_fn`
    long count = fold(data, zero, this::fold_fn).count; //
    System.out.println(count); // 5
  }

}
