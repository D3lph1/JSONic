package com.github.d3lph1.jsonic;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Program representation of the JSON array.
 *
 * @author D3lph1
 */
public final class JsonArray extends JsonElement implements Iterable<JsonElement>
{
    private List<JsonElement> elements;

    public JsonArray(List<JsonElement> elements)
    {
        this.elements = elements;
    }

    /**
     * @see List#add(Object)
     */
    public boolean add(Boolean element)
    {
        return elements.add(new JsonPrimitive(element));
    }

    /**
     * @see List#add(Object)
     */
    public boolean add(Integer element)
    {
        return elements.add(new JsonPrimitive(element));
    }

    /**
     * @see List#add(Object)
     */
    public boolean add(Double element)
    {
        return elements.add(new JsonPrimitive(element));
    }

    /**
     * @see List#add(Object)
     */
    public boolean add(String element)
    {
        return elements.add(new JsonPrimitive(element));
    }

    /**
     * @see List#add(Object)
     */
    public boolean add(JsonElement element)
    {
        return elements.add(element);
    }

    /**
     * @see List#addAll(Collection)
     */
    public boolean addAll(Collection<? extends JsonElement> all)
    {
        return elements.addAll(all);
    }

    /**
     * @see List#contains(Object)
     */
    public boolean contains(JsonElement element)
    {
        return elements.contains(element);
    }

    /**
     * @see List#set(int, Object)
     */
    public JsonElement set(int index, JsonElement element)
    {
        return elements.set(index, element);
    }

    /**
     * @see List#size()
     */
    public int size()
    {
        return elements.size();
    }

    /**
     * @see List#remove(int)
     */
    public JsonElement remove(int index)
    {
        return elements.remove(index);
    }

    /**
     * @see List#remove(Object)
     */
    public boolean remove(JsonElement element)
    {
        return elements.remove(element);
    }

    /**
     * @see List#get(int)
     */
    public JsonElement get(int index)
    {
        return elements.get(index);
    }

    public Iterator<JsonElement> iterator()
    {
        return elements.iterator();
    }
}
