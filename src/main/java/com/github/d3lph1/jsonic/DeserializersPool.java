package com.github.d3lph1.jsonic;

import java.util.HashMap;

/**
 * The store of registered deserializers.
 *
 * @author D3lph1
 */
public class DeserializersPool
{
    private HashMap<Class, JsonDeserializer> deserializers = new HashMap<>();

    /**
     * Add a deserializer for a given class to the pool.
     */
    public void put(Class deserializableClass, JsonDeserializer deserializer)
    {
        if (!has(deserializableClass)) {
            deserializers.put(deserializableClass, deserializer);
        }
    }

    /**
     * Checks for the presence of a deserializer for a given class in the pool.
     */
    public boolean has(Class deserializableClass)
    {
        return deserializers.containsKey(deserializableClass);
    }

    /**
     * Get the deserializer for a given class from pool.
     */
    public JsonDeserializer get(Class deserializableClass)
    {
        return deserializers.get(deserializableClass);
    }

    /**
     * Remove deserializer for a given class from pool.
     */
    public JsonDeserializer remove(Class deserializableClass)
    {
        return deserializers.remove(deserializableClass);
    }
}
