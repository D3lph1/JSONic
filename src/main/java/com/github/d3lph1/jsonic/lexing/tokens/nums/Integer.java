package com.github.d3lph1.jsonic.lexing.tokens.nums;

/**
 * Represents a integer number lexeme.
 *
 * @author D3lph1
 */
public class Integer extends Num
{
    private int value;

    public Integer(int value, int position)
    {
        super(position);
        this.value = value;
    }

    public Integer deepCopy()
    {
        return new Integer(getValue(), getPosition());
    }

    public java.lang.Integer getValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return String.valueOf(getValue());
    }
}
