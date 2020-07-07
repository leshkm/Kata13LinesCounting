package ua.mohylin.kata13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class JavaLinesCountingTask {

  private static final String ALL_WHITESPACE = null;

  public long countLines(InputStream inputStream) throws IOException {
    Objects.requireNonNull(inputStream);

    long count = 0;
    boolean inMultilineComment = false;

    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    while (reader.ready()) {
      JavaCodeLine line = new JavaCodeLine(reader.readLine(), false);
      if (line.isEmptyLine()) {
        continue;
      }
      if (!inMultilineComment && line.isSingleLineComment()) {
        continue;
      }
      ++count;
    }

    return count;
  }

  private boolean isSingleLineComment(String line) {
    return line.charAt(0) == '/' && line.charAt(1) == '/' ;
  }

}
