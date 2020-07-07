package ua.mohylin.kata13;

import org.apache.commons.lang3.StringUtils;

public class JavaCodeLine {


    private final String line;
    private boolean hasCode;
    private boolean openMultiLineComment;

    public JavaCodeLine(String line, boolean openMultiLineComment) {
        this.line = StringUtils.stripStart(line, null);
        this.openMultiLineComment = openMultiLineComment;
        parseMultiLineComments(openMultiLineComment);
    }

    public boolean isHasCode() {
        return hasCode;
    }

    public boolean isOpenMultiLineComment() {
        return openMultiLineComment;
    }

    public boolean isEmptyLine() {
        return line.isEmpty();
    }

    public boolean isSingleLineComment() {
        return line.charAt(0) == '/' && line.charAt(1) == '/' ;
    }

    public boolean isMultiLineCommentStart() {
        return line.charAt(0) == '/' && line.charAt(1) == '*' ;
    }

    private int gotoMultilineCommentEnd(int startOffset) {
        int i = startOffset;
        char prev = 0, chr = 0;
        while(i < line.length()) {
            prev = chr; chr = line.charAt(i++);
            if (chr == '/' && prev == '*') {
                this.openMultiLineComment = false;
                break;
            }
        }
        return i;
    }

    /**/
    /*/*/
    private void parseMultiLineComments(boolean inMultiLineComment) {

        if (line.isEmpty()) {
            hasCode = false;
        }
        int startOffset = 0;
        if (inMultiLineComment) {
            startOffset = gotoMultilineCommentEnd(0);
            if (startOffset >= line.length()) {
                this.hasCode = false;
            }
        }

        char prev = 0, chr = 0;
        boolean inStringLiteral = false;
        int i = startOffset;
        while(i < line.length()) {
            prev = chr; chr = line.charAt(i++);
            if (chr == '"') {
                if (inStringLiteral && prev == '\\') {
                    continue;
                }
                inStringLiteral = !inStringLiteral;
            }
            if (inStringLiteral) {
                continue;
            }
            if (chr == '*' && prev == '/' && !inMultiLineComment) {

            }
        }
    }

}
