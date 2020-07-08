package ua.mohylin.kata13;

import java.io.*;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class JavaLinesCountingTask implements DebugLogger {

  private final File file;
  private final int level;

  public JavaLinesCountingTask(File file, int level) {
    this.file = file;
    this.level = level;
  }

  public void run() throws IOException {
    long count = countLinesWithCode(new FileInputStream(file));
    String prefix = StringUtils.repeat("  ", level - 1);
    System.out.println(String.format("%s%s : %d", prefix, file.getName(), count));
  }

  public long countLinesWithCode(InputStream inputStream) throws IOException {
    Objects.requireNonNull(inputStream);

    long countOfLinesWithCode = 0;
    long lineNo = 1;
    // we need to carry over knowledge about unclosed multiline comment
    boolean inMultilineComment = false;

    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    while (reader.ready()) {
      debug(String.format("%d: of - ", lineNo++));
      JavaSourceLine line = new JavaSourceLine(reader.readLine(), inMultilineComment);
      if (line.isHasCode()) {
        ++countOfLinesWithCode;
      }
      inMultilineComment = line.isOpenMultiLineComment();
      debug(
          String.format(
              ", code - %s, mlc - %s\n", line.isHasCode(), line.isOpenMultiLineComment()));
    }

    return countOfLinesWithCode;
  }
}
