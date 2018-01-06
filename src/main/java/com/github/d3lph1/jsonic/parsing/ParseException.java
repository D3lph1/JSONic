package com.github.d3lph1.jsonic.parsing;

import com.github.d3lph1.jsonic.lexing.UnexpectedCharException;

/**
 * @author D3lph1
 */
public class ParseException extends RuntimeException
{
    private int absolutePosition;

    public ParseException(String msg, int absPos)
    {
        super(msg + " in position " + absPos);
        absolutePosition = absPos;
    }

    public ParseException(UnexpectedCharException e)
    {
        super("Parse error in position " + e.getAbsolutePosition());
        absolutePosition = e.getAbsolutePosition();
    }

    public int getAbsolutePosition()
    {
        return absolutePosition;
    }
}
