package ua.mohylin.kata13;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import org.apache.commons.lang3.StringUtils;

/**
 * Parses source line and gives answer to these two questions - if source line contains actually
 * code - whether it leaves open multiline comment
 */
public class JavaSourceLine implements DebugLogger {

  private final String line;
  private final JavaSourceLineIterator lineIterator;
  private boolean hasCode;
  private boolean openMultiLineComment;

  /**
   * Constructs object and also triggers its processing
   *
   * @param line the source code line itself
   * @param openMultiLineComment flag that shows if previous line has open multiline comment
   */
  public JavaSourceLine(String line, boolean openMultiLineComment) {
    this.line = StringUtils.stripStart(line, null);
    this.lineIterator = new JavaSourceLineIterator(this.line);
    this.openMultiLineComment = openMultiLineComment;
    processSourceLine(openMultiLineComment);
  }

  /**
   * Whether this line contains code
   *
   * @return true if code found or false
   */
  public boolean isHasCode() {
    return hasCode;
  }

  /**
   * Whether this line opens a multiline comment
   *
   * @return true if multiline comment is opened, otherwise false
   */
  public boolean isOpenMultiLineComment() {
    return openMultiLineComment;
  }

  private void gotoMultilineCommentEnd() {
    moveTillCondition((chr, prev) -> chr == '/' && prev == '*', this::unsetOpenMultilineComment);
  }

  private boolean unsetOpenMultilineComment(boolean bool) {
    this.openMultiLineComment = false;
    return this.openMultiLineComment;
  }

  private void gotoStringLiteralEnd() {
    moveTillCondition((chr, prev) -> chr == '"' && !(prev == '\\'), null);
  }

  private void moveTillCondition(
      BiFunction<Character, Character, Boolean> condition, UnaryOperator<Boolean> callback) {
    while (lineIterator.hasNext()) {
      lineIterator.next();
      if (condition.apply(lineIterator.getCurrentChar(), lineIterator.getPreviousChar())) {
        if (callback != null) {
          callback.apply(true);
        }
        break;
      }
    }
  }

  private void processSourceLine(boolean inMultiLineComment) {

    // empty lines detection
    if (line.length() == 0) {
      hasCode = false;
      return;
    }

    // line that consists of 1 charachter
    if (line.length() == 1 && !Character.isWhitespace(line.charAt(0))) {
      // this is code unless we are in multiline comment
      if (!inMultiLineComment) {
        this.hasCode = true;
      }
      return;
    }

    if (inMultiLineComment) {
      // search for end of multiline comment
      gotoMultilineCommentEnd();
      if (lineIterator.isAtEnd()) {
        // nothing more to process
        return;
      }
    }

    // move iterator one position further
    // needed for the successful processing
    // lineIterator.next();

    while (lineIterator.hasNext()) {
      lineIterator.next();

      char currentChar = lineIterator.getCurrentChar();
      char previousChar = lineIterator.getPreviousChar();

      if (Character.isWhitespace(currentChar)) {
        // whitespaces don't matter
        continue;
      }

      if (currentChar == '/' && previousChar == '/') {
        // single-line comment starts - skip the rest
        break;
      }

      if (currentChar == '"') {
        hasCode = true;
        gotoStringLiteralEnd();
        continue;
      }

      if (previousChar == '/' && currentChar == '*') {
        openMultiLineComment = true;
        gotoMultilineCommentEnd();
        continue;
      }

      if (!hasCode && currentChar == '/') {
        // postpone decision as it could be comment opening
        continue;
      }

      if (!hasCode) {
        debug("(code starts at " + lineIterator.getOffset() + ")");
      }

      this.hasCode = true;
    }
    debug(String.valueOf(lineIterator.getOffset()));
  }
}
