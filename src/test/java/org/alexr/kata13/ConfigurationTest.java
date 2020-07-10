package org.alexr.kata13;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

  private Configuration conf = new Configuration();

  private final File file = new File(
      this.getClass().getClassLoader().getResource("Strip39.java").getFile()
  );

  @Test
  void counter_test() {
    assertEquals(
        39,
        conf.counter().count(file)
    );
  }

  @Test
  void fileFilterF1_must_have_proper_ext() {
    assertFalse(
        conf.fileFilter(new File("App.scala"))
    );
  }

  @Test
  void fileFilterF2_must_exist() {
    assertFalse(
        conf.fileFilter(new File("App.java"))
    );
  }

  @Test
  void fileFilterT() {
    assertTrue(
        conf.fileFilter(file)
    );
  }
}
