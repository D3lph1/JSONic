package com.github.d3lph1.jsonic;

/**
 * Abstract program representation of the all JSON elements.
 *
 * @author D3lph1
 */
public abstract class JsonElement
{
    public boolean isPrimitive()
    {
        return this instanceof JsonPrimitive;
    }

    public JsonPrimitive getAsPrimitive()
    {
        if (isPrimitive()) {
            return (JsonPrimitive) this;
        }

        throw new IllegalTypeException(JsonType.PRIMITIVE, this.toString());
    }

    public boolean isArray()
    {
        return this instanceof JsonArray;
    }

    public JsonArray getAsArray()
    {
        if (isArray()) {
            return (JsonArray) this;
        }

        throw new IllegalTypeException(JsonType.ARRAY, this.toString());
    }

    public boolean isObject()
    {
        return this instanceof JsonObject;
    }

    public JsonObject getAsObject()
    {
        if (isObject()) {
            return (JsonObject) this;
        }

        throw new IllegalTypeException(JsonType.OBJECT, this.toString());
    }

    public boolean isNull()
    {
        return this instanceof JsonNull;
    }

    public JsonNull getAsNull()
    {
        if (isNull()) {
            return (JsonNull) this;
        }

        throw new IllegalTypeException(JsonType.NULL, this.toString());
    }
}
