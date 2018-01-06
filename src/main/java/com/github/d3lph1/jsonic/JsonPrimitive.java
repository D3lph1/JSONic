package com.github.d3lph1.jsonic;

/**
 * Program representation of the JSON primitive values: integer, double, boolean, string literal.
 *
 * @author D3lph1
 */
public final class JsonPrimitive extends JsonElement
{
    private Object value;

    public JsonPrimitive(int value)
    {
        init(value);
    }

    public JsonPrimitive(double value)
    {
        init(value);
    }

    public JsonPrimitive(boolean value)
    {
        init(value);
    }

    public JsonPrimitive(String value)
    {
        init(value);
    }

    private void init(Object value)
    {
        this.value = value;
    }

    public boolean isBoolean()
    {
        return value instanceof Boolean;
    }

    public Boolean getAsBoolean()
    {
        return (Boolean) value;
    }

    public boolean isInteger()
    {
        return value instanceof Integer;
    }

    public Integer getAsInteger()
    {
        return (Integer) value;
    }

    public boolean isDouble()
    {
        return value instanceof Double;
    }

    public Double getAsDouble()
    {
        if (isDouble()) {
            return (Double) value;
        }

        if (isInteger()) {
            return (double) (int) value;
        }

        if (isString()) {
            return Double.valueOf(getAsString());
        }

        throw new ClassCastException("Can not cast " + value + " to " + Double.class.getName());
    }

    public boolean isString()
    {
        return value instanceof String;
    }

    public String getAsString()
    {
        return (String) value;
    }
}
