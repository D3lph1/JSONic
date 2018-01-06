package com.github.d3lph1.jsonic.lexing.tokens.nums;

/**
 * Represents a double number lexeme.
 *
 * @author D3lph1
 */
public class Double extends Num
{
    private double value;

    public Double(double value, int position)
    {
        super(position);
        this.value = value;
    }

    public Double deepCopy()
    {
        return new Double(getValue(), getPosition());
    }

    public java.lang.Double getValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return String.valueOf(getValue());
    }
}
