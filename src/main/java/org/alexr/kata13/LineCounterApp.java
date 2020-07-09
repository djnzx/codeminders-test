package org.alexr.kata13;

import org.alexr.kata13.count.CountAll;
import org.alexr.kata13.count.CountNonBlank;
import org.alexr.kata13.counter.LineCounter;
import org.alexr.kata13.strip.Strip1Nothing;
import org.alexr.kata13.strip.Strip2LineOnlyComments;
import org.alexr.kata13.strip.Strip3BlockOnlyComments;
import org.alexr.kata13.strip.Strip4AllComments;

import java.io.File;

public class LineCounterApp {

  public static void exit(String message) {
    System.err.println(message);
    System.exit(-1);
  }

  private static File validate(String[] args) {
    if (args.length == 0) exit("file or folder name is expected as a parameter to run the app");
    File path = new File(args[0]);
    if (!path.exists()) exit("valid file or folder name is expected as a parameter to run the app");
    return path;
  }

  /**
   * particular implementations
   * we are interested in
   */
  private static LineCounter buildCounter() {
    return new LineCounter(
//        new Strip1Nothing(), new CountAll()                // 61
//        new Strip1Nothing(), new CountNonBlank()           // 52 = 61 - 9 blank
//        new Strip2LineOnlyComments(), new CountNonBlank()  // 45 = 52 - 7 single lines
//        new Strip3BlockOnlyComments(), new CountNonBlank() // 46 = 52 - 6 block
        new Strip4AllComments(), new CountNonBlank()       // 39 = 52 - 6 - 7
    );
  }

  public static void main(String[] args) {
    new TreeScanner(validate(args), buildCounter()::run)
        .process()
        .forEach(System.out::println);
  }
}
