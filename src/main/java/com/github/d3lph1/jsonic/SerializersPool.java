package com.github.d3lph1.jsonic;

import java.util.HashMap;

/**
 * The store of registered serializers.
 *
 * @author D3lph1
 */
public class SerializersPool
{
    private HashMap<Class, JsonSerializer> serializers = new HashMap<>();

    /**
     * Add a serializer for a given class to the pool.
     */
    public void put(Class serializableClass, JsonSerializer serializer)
    {
        if (!has(serializableClass)) {
            serializers.put(serializableClass, serializer);
        }
    }

    /**
     * Checks for the presence of a serializer for a given class in the pool.
     */
    public boolean has(Class serializableClass)
    {
        return serializers.containsKey(serializableClass);
    }

    /**
     * Get the serializer for a given class from pool.
     */
    public JsonSerializer get(Class serializableClass)
    {
        return serializers.get(serializableClass);
    }

    /**
     * Remove serializer for a given class from pool.
     */
    public JsonSerializer remove(Class serializableClass)
    {
        return serializers.remove(serializableClass);
    }
}
