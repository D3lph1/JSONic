package com.github.d3lph1.jsonic;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * @author D3lph1
 */
public class DeserializersPoolTest
{
    @Test
    public void testPutHasGetRemove()
    {
        DeserializersPool pool = new DeserializersPool();
        pool.put(Date.class, (JsonDeserializer<Date>) obj -> null);
        assertTrue(pool.has(Date.class));
        assertFalse(pool.has(String.class));
        assertThat(pool.get(Date.class), instanceOf(JsonDeserializer.class));
        pool.remove(Date.class);
        assertNull(pool.get(Date.class));
    }

    private static class Date
    {
        //
    }
}
