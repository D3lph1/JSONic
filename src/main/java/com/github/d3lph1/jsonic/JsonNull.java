package com.github.d3lph1.jsonic;

/**
 * Program representation of the JSON null.
 *
 * @author D3lph1
 */
public final class JsonNull extends JsonElement
{
    private static JsonNull instance;

    private JsonNull()
    {
    }

    public static JsonNull getInstance()
    {
        if (instance == null) {
            instance = new JsonNull();
        }

        return instance;
    }
}
