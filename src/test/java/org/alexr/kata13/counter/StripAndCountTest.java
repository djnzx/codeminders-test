package org.alexr.kata13.counter;

import org.alexr.kata13.count.CountNonBlank;
import org.alexr.kata13.strip.Strip5AllCommentsStringCare;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StripAndCountTest {

  private final StripAndCount sac = new StripAndCount(
      new Strip5AllCommentsStringCare(),
      new CountNonBlank()
  );

  @Test
  void fold_file() {
    FileState fs0 = FileState.fresh();
    assertEquals(0, fs0.count);
    assertFalse(fs0.inBlock);

    FileState fs1 = sac.fold_file(fs0, "// comment");
    assertEquals(0, fs1.count);
    assertFalse(fs1.inBlock);

    FileState fs2 = sac.fold_file(fs1, "  int x = 5;");
    assertEquals(1, fs2.count);
    assertFalse(fs2.inBlock);

    FileState fs3 = sac.fold_file(fs2, "  int /* block */ y = 1;");
    assertEquals(2, fs3.count);
    assertFalse(fs3.inBlock);

    FileState fs4 = sac.fold_file(fs3, "  /*int x = 15;");
    assertEquals(2, fs4.count);
    assertTrue(fs4.inBlock);

    FileState fs5 = sac.fold_file(fs4, "  int x = 16;*/");
    assertEquals(2, fs5.count);
    assertFalse(fs5.inBlock);

    FileState fs6 = sac.fold_file(fs5, "  println(x)");
    assertEquals(3, fs6.count);
    assertFalse(fs6.inBlock);
  }

  @Test
  void count_stream() {
    Stream<String> data = Stream.of(
        "// This file contains 3 lines of code",
        "",
        "public interface Dave {",
        "  /**",
        "   * count the number of lines in a file",
        "   */",
        "  int countLines(File inFile); // not the real signature!",
        "}",
        ""
    );
    assertEquals(
        3,
        sac.count(data)
    );
  }

  @Test
  void count_file_ok() {
    Object[][] dataset = {
        {39, "Strip39.java"},
        {3, "Test3.java"},
        {5, "Test5.java"},
        {7, "Tricky7.java"}
    };
    Arrays.stream(dataset).forEach(item ->
      assertEquals(
          (int)item[0],
          sac.count(new File(
              this.getClass().getClassLoader().getResource((String)item[1]).getFile()
          ))
      )
    );
  }

  @Test
  void count_file_ex() {
    assertThrows(
        NoSuchFileException.class,
        () -> sac.count(new File("NonExistent.java"))
    );
  }
}
