package ua.mohylin.kata13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class JavaLinesCountingTaskTest {

  private InputStream getTest(String testFileName) {
    InputStream stream = this.getClass().getResourceAsStream("/" + testFileName);
    assert stream != null;
    return stream;
  }

  @ParameterizedTest
  @CsvSource({"source1_3_lines.java.txt,3", "source2_5_lines.java.txt,5"})
  void test1(String testFileName, int expectedCount) throws IOException {
    InputStream test = getTest(testFileName);
    long countLines = new JavaLinesCountingTask(new File("dummy")).countLinesWithCode(test);
    assertEquals(expectedCount, countLines);
  }
}
