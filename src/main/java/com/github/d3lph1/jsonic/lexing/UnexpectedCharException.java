package com.github.d3lph1.jsonic.lexing;

/**
 * @author D3lph1
 */
public class UnexpectedCharException extends RuntimeException
{
    private int absPos;

    private int line;

    private int linePos;

    public UnexpectedCharException(int absPos, int line, int linePos)
    {
        super(String.format("Can not tokenize JSON: unexpected char in line %d position %d (absolute position: %d)", line, linePos, absPos));

        this.absPos = absPos;
        this.line = line;
        this.linePos = linePos;
    }

    public UnexpectedCharException(int absPos, int line, int linePos, Class expectedTokenClass)
    {
        this(absPos, line, linePos, expectedTokenClass.getName());
    }

    public UnexpectedCharException(int absPos, int line, int linePos, String expectedTokenClass)
    {
        super(String.format(
                "Can not tokenize JSON: unexpected char in line %d position %d (absolute position: %d), expected token: %s",
                line,
                linePos,
                absPos,
                expectedTokenClass
        ));
        this.absPos = absPos;
        this.line = line;
        this.linePos = linePos;
    }

    public UnexpectedCharException(int absPos, int line, int linePos, Class[] expectedTokenClasses)
    {
        super(String.format(
                "Can not tokenize JSON: unexpected char in line %d position %d (absolute position: %d), expected tokens: %s",
                line,
                linePos,
                absPos,
                format(expectedTokenClasses)
        ));

        this.absPos = absPos;
        this.line = line;
        this.linePos = linePos;
    }

    private static String format(Class[] expectedTokenClasses)
    {
        String[] classes = new String[expectedTokenClasses.length];
        for (int i = 0; i < classes.length; i++) {
            classes[i] = expectedTokenClasses[i].getName();
        }

        return String.join(", ", classes);
    }

    public int getAbsolutePosition()
    {
        return absPos;
    }

    public int getLine()
    {
        return line;
    }

    public int getLinePosition()
    {
        return linePos;
    }
}
