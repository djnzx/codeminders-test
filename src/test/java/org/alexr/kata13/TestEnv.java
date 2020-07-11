package org.alexr.kata13;

import java.io.File;
import java.nio.file.Paths;

public class TestEnv {

  public static File rootFolderAsFile() {
    return Paths.get("target", "test-classes").toAbsolutePath().toFile();
  }

}
