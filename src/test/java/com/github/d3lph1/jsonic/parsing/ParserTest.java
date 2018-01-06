package com.github.d3lph1.jsonic.parsing;

import com.github.d3lph1.jsonic.JsonArray;
import com.github.d3lph1.jsonic.JsonObject;
import com.github.d3lph1.jsonic.lexing.Tokenizer;
import com.github.d3lph1.jsonic.lexing.readers.StringReader;
import com.github.d3lph1.jsonic.JsonNull;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author D3lph1
 */
public class ParserTest
{
    private Parser parser(java.lang.String src)
    {
        return new Parser(new Tokenizer(new StringReader(src)));
    }

    @Test
    public void integer()
    {
        Parser p = parser("7");
        Assert.assertEquals(Integer.valueOf(7), p.parse().getAsPrimitive().getAsInteger());
    }

    @Test
    public void Double()
    {
        Parser p = parser("7.5");
        Assert.assertEquals(7.5, p.parse().getAsPrimitive().getAsDouble(), 0.0001);
    }

    @Test
    public void string()
    {
        Parser p = parser("\"lorem ipsum\"");
        Assert.assertEquals("lorem ipsum", p.parse().getAsPrimitive().getAsString());
    }

    @Test
    public void bool()
    {
        Parser p = parser("true");
        Assert.assertEquals(true, p.parse().getAsPrimitive().getAsBoolean());
    }

    @Test
    public void Null()
    {
        Parser p = parser("null");
        assertThat(p.parse().getAsNull(), instanceOf(JsonNull.class));
    }

    @Test
    public void array()
    {
        Parser p = parser("[\"str\", 5, -1.1, false, null]");
        JsonArray a = p.parse().getAsArray();
        assertEquals("str", a.get(0).getAsPrimitive().getAsString());
        assertEquals(Integer.valueOf(5), a.get(1).getAsPrimitive().getAsInteger());
        assertEquals(-1.1, a.get(2).getAsPrimitive().getAsDouble(), 0.0001);
        assertEquals(false, a.get(3).getAsPrimitive().getAsBoolean());
        assertThat(a.get(4).getAsNull(), instanceOf(JsonNull.class));
    }

    @Test
    public void nestedArrays()
    {
        Parser p = parser("[4, [5, [\"str\", true]]]");
        JsonArray a = p.parse().getAsArray();
        assertEquals(Integer.valueOf(4), a.get(0).getAsPrimitive().getAsInteger());
        assertEquals(Integer.valueOf(5), a.get(1).getAsArray().get(0).getAsPrimitive().getAsInteger());
        assertEquals("str", a.get(1).getAsArray().get(1).getAsArray().get(0).getAsPrimitive().getAsString());
        assertEquals(true, a.get(1).getAsArray().get(1).getAsArray().get(1).getAsPrimitive().getAsBoolean());
    }

    @Test
    public void object()
    {
        Parser p = parser("{\"key\": \"value\"}");
        JsonObject o = p.parse().getAsObject();
        assertEquals("value", o.get("key").getAsPrimitive().getAsString());
    }

    @Test
    public void nestedObjects()
    {
        Parser p = parser("{\"lorem_ipsum\": {\"key\": \"value\"}, \"lorem_ipsum1\": {\"key\": 4}}");
        JsonObject o = p.parse().getAsObject();
        assertEquals("value", o.get("lorem_ipsum").getAsObject().get("key").getAsPrimitive().getAsString());
        assertEquals(Integer.valueOf(4), o.get("lorem_ipsum1").getAsObject().get("key").getAsPrimitive().getAsInteger());
    }

    @Test
    public void all()
    {
        Parser p = parser("[{\"key\": \"value\", \"key1\": \"value1\"}, [1, 2, 3], {\"key\": false}, {\"key\": [4, 5]}]");
        JsonArray o = p.parse().getAsArray();
        assertEquals("value", o.get(0).getAsObject().get("key").getAsPrimitive().getAsString());
        assertEquals("value1", o.get(0).getAsObject().get("key1").getAsPrimitive().getAsString());
        assertEquals(Integer.valueOf(1), o.get(1).getAsArray().get(0).getAsPrimitive().getAsInteger());
        assertEquals(Integer.valueOf(2), o.get(1).getAsArray().get(1).getAsPrimitive().getAsInteger());
        assertEquals(Integer.valueOf(3), o.get(1).getAsArray().get(2).getAsPrimitive().getAsInteger());
        assertEquals(false, o.get(2).getAsObject().get("key").getAsPrimitive().getAsBoolean());
        assertEquals(Integer.valueOf(4), o.get(3).getAsObject().get("key").getAsArray().get(0).getAsPrimitive().getAsInteger());
        assertEquals(Integer.valueOf(5), o.get(3).getAsObject().get("key").getAsArray().get(1).getAsPrimitive().getAsInteger());
    }

    @Test(expected = ParseException.class)
    public void missingCommaInArray()
    {
        Parser p = parser("[1 2, 3]");
        p.parse();
    }

    @Test(expected = ParseException.class)
    public void missingCommaInObject()
    {
        Parser p = parser("{\"key\": 5 \"key1\": \"value\"}");
        p.parse();
    }

    @Test(expected = ParseException.class)
    public void doubleRootElements1()
    {
        Parser p = parser("[1, 2, 3] 4");
        p.parse();
    }

    @Test(expected = ParseException.class)
    public void doubleRootElements2()
    {
        Parser p = parser("4 [1, 2, 3]");
        p.parse();
    }

    @Test
    public void emptyArray()
    {
        Parser p = parser("[]");
        JsonArray a = p.parse().getAsArray();
        assertEquals(0, a.size());
    }

    @Test
    public void emptyObject()
    {
        Parser p = parser("{}");
        JsonObject o = p.parse().getAsObject();
        assertEquals(0, o.size());
    }

    @Test
    public void emptyArrayAndObject()
    {
        Parser p = parser("[{}, {\"key\":\"value\"}, 5, []]");
        JsonArray a = p.parse().getAsArray();
        assertEquals(0, a.get(0).getAsObject().size());
        assertEquals("value", a.get(1).getAsObject().get("key").getAsPrimitive().getAsString());
        assertEquals(Integer.valueOf(5), a.get(2).getAsPrimitive().getAsInteger());
        assertEquals(0, a.get(3).getAsArray().size());
    }
}
