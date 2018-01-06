package com.github.d3lph1.jsonic;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * @author D3lph1
 */
public class SerializersPoolTest
{
    @Test
    public void testPutHasGetRemove()
    {
        SerializersPool pool = new SerializersPool();
        pool.put(Date.class, (JsonSerializer<Date>) obj -> null);
        assertTrue(pool.has(Date.class));
        assertFalse(pool.has(String.class));
        assertThat(pool.get(Date.class), instanceOf(JsonSerializer.class));
        pool.remove(Date.class);
        assertNull(pool.get(Date.class));
    }

    private static class Date
    {
        //
    }
}
