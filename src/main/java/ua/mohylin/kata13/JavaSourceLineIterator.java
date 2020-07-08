package ua.mohylin.kata13;

/**
 * Enables iterating over a source code string, keeps track of current and previous charachter and
 * current index
 */
public class JavaSourceLineIterator {

  private final String line;
  private int offset;
  private char currentChar;
  private char previousChar = 0;

  public JavaSourceLineIterator(String line) {
    this.line = line;
    this.offset = 0;
  }

  /**
   * Whether there is one more charachter to read
   *
   * @return true or false
   */
  public boolean hasNext() {
    return offset < line.length();
  }

  /**
   * Wheter end of line has been reached
   *
   * @return true or false
   */
  public boolean isAtEnd() {
    return !hasNext();
  }

  /**
   * Reads next charachter from line and return it
   *
   * @return the next charachter
   */
  public char next() {
    previousChar = currentChar;
    currentChar = line.charAt(offset++);
    return currentChar;
  }

  /**
   * Returns current offset in line
   *
   * @return current offset, int
   */
  public int getOffset() {
    return offset;
  }

  /**
   * Returns current charachter (i.e. the one which has been last read)
   *
   * @return charachter
   */
  public char getCurrentChar() {
    return currentChar;
  }

  /**
   * Returns previous charachter
   *
   * @return a charachter
   */
  public char getPreviousChar() {
    return previousChar;
  }
}
