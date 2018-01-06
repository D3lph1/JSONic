package com.github.d3lph1.jsonic;

/**
 * @author D3lph1
 */
public class IllegalTypeException extends IllegalStateException
{
    public IllegalTypeException(JsonType type, String target)
    {
        super("Not a JSON " + type + ": " + target);
    }
}
