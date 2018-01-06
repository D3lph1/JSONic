package com.github.d3lph1.jsonic;

import java.util.Map;
import java.util.Set;

/**
 * Program representation of the JSON object.
 *
 * @author D3lph1
 */
public final class JsonObject extends JsonElement
{
    private Map<String, JsonElement> elements;

    public JsonObject(Map<String, JsonElement> elements)
    {
        this.elements = elements;
    }

    /**
     * @see Map#put(Object, Object)
     */
    public JsonElement put(String key, JsonElement value)
    {
        return elements.put(key, value);
    }

    /**
     * @see Map#get(Object)
     */
    public JsonElement get(String key)
    {
        return elements.get(key);
    }

    /**
     * @see Map#size()
     */
    public int size()
    {
        return elements.size();
    }

    /**
     * @see Map#remove(Object)
     */
    public JsonElement remove(String key)
    {
        return elements.remove(key);
    }

    /**
     * @see Map#entry(Object, Object)
     */
    public Set<Map.Entry<String, JsonElement>> entrySet()
    {
        return elements.entrySet();
    }
}
