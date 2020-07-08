package ua.mohylin.kata13;

import java.io.File;
import java.io.IOException;

public class JavaCodeLinesCountingApplication {

  public static void main(String[] args) throws IOException {
    File fileOrDir = getFileParameter(args);
    if (fileOrDir == null) {
      return;
    }
    new JavaLinesCountingTask(fileOrDir, 1).run();
  }

  static File getFileParameter(String[] args) {
    if (args == null || args.length == 0) {
      System.out.println("ERROR: File/directory path is expected as parameter\nExiting...");
      return null;
    }
    String path = args[0];
    File sourceCodeFile = new File(path);
    if (!sourceCodeFile.exists()) {
      System.out.println(
          String.format("ERROR: Specified path does not exist - %s\nExiting...", path));
      return null;
    }
    return sourceCodeFile;
  }
}
