package ua.mohylin.kata13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class JavaLinesCountingTask implements DebugLogger {

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
