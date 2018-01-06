package com.github.d3lph1.jsonic.lexing.tokens;

/**
 * Represents a string literal lexeme.
 *
 * @author D3lph1
 */
public class String extends Token
{
    private java.lang.String value;

    public String(java.lang.String value, int position)
    {
        super(position);
        this.value = value;
    }

    public String deepCopy()
    {
        return new String(getValue(), getPosition());
    }

    public java.lang.String getValue()
    {
        return value;
    }

    @Override
    public java.lang.String toString()
    {
        return value;
    }
}
