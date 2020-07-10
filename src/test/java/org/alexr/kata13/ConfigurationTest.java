package org.alexr.kata13;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

  private Configuration conf = new Configuration();

  @Test
  void counter_test() {
    assertEquals(
        39,
        conf.counter().count(new File("./src/test/resources/Strip39.java"))
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
        conf.fileFilter(new File("./src/test/resources/Strip39.java"))
    );
  }
}
