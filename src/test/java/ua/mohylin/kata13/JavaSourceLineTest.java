package ua.mohylin.kata13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class JavaSourceLineTest {

  @ParameterizedTest
  @CsvSource({
    /*line,isInMultilineComment, expectedHasCode, expectedIsInMultilineComment*/
    "something going on here,true,false,true",
    "some comment ends here */,true,false, false",
    "\"this line contains a string literal\" /*, false, true, true"
  })
  void test(
      String line,
      boolean openMultiLineCode,
      boolean expectHasCode,
      boolean expectOpenMultiLineComment) {
    JavaSourceLine jcl = new JavaSourceLine(line, openMultiLineCode);
    assertEquals(expectHasCode, jcl.isHasCode(), "hasCode");
    assertEquals(expectOpenMultiLineComment, jcl.isOpenMultiLineComment(), "openMultiLineCode");
  }
}
