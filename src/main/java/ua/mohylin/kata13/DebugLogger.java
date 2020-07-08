package ua.mohylin.kata13;

/** Simple addon to dumb debug into console with ability to disable it alltogether */
public interface DebugLogger {

  boolean DEBUG_ON = false;

  default void debug(String message) {
    if (!DEBUG_ON) {
      return;
    }
    System.out.print(message);
  }
}
