package ua.mohylin.kata13;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaCodeLineTest {

    @ParameterizedTest
    @CsvSource(
            {
                    "something going on here,true,false,true",
                    "some comment ends here */,true,false, false"
            })
    void test(String line, boolean openMultiLineCode, boolean expectHasCode, boolean expectOpenMultiLineComment) {
        JavaCodeLine jcl = new JavaCodeLine(line, openMultiLineCode);
        assertEquals(expectHasCode, jcl.isHasCode(), "hasCode");
        assertEquals(expectOpenMultiLineComment, jcl.isOpenMultiLineComment(), "openMultiLineCode");
    }

}
