package com.github.d3lph1.jsonic.building;

import com.github.d3lph1.jsonic.*;
import com.github.d3lph1.jsonic.annotations.Json;
import com.github.d3lph1.jsonic.annotations.Serialize;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * @author D3lph1
 */
public class JsonBuilderTest
{
    private String build(Object object)
    {
        return new JsonBuilder().build(object);
    }

    @Test
    public void string()
    {
        assertEquals("\"lorem ipsum\"", build("lorem ipsum"));
    }

    @Test
    public void integer()
    {
        assertEquals("5", build(5));
    }

    @Test
    public void Double()
    {
        assertEquals("-1.6", build(-1.6));
    }

    @Test
    public void bool()
    {
        assertEquals("true", build(true));
    }

    @Test
    public void jsonNull()
    {
        assertEquals("null", build(JsonNull.getInstance()));
    }

    @Test
    public void Null()
    {
        assertEquals("null", build(null));
    }

    @Test
    public void primitiveString()
    {
        assertEquals("\"lorem ipsum\"", build(new JsonPrimitive("lorem ipsum")));
    }

    @Test
    public void primitiveInteger()
    {
        assertEquals("7", build(new JsonPrimitive(7)));
    }

    @Test
    public void primitiveDouble()
    {
        assertEquals("8.45", build(new JsonPrimitive(8.45)));
    }

    @Test
    public void primitiveBool()
    {
        assertEquals("false", build(new JsonPrimitive(false)));
    }

    @Test
    public void array()
    {
        ArrayList<JsonElement> a = new ArrayList<>();
        a.add(new JsonPrimitive("1"));
        a.add(new JsonPrimitive(2));
        a.add(new JsonPrimitive(3));

        assertEquals("[\"1\",2,3]", build(new JsonArray(a)));
    }

    @Test
    public void nestedArray()
    {
        ArrayList<JsonElement> a = new ArrayList<>();
        a.add(new JsonPrimitive("1"));
        a.add(new JsonPrimitive(2));
        ArrayList<JsonElement> b = new ArrayList<>();
        b.add(new JsonPrimitive(false));
        b.add(JsonNull.getInstance());
        b.add(new JsonPrimitive("lorem ipsum"));
        a.add(new JsonArray(b));

        assertEquals("[\"1\",2,[false,null,\"lorem ipsum\"]]", build(new JsonArray(a)));
    }

    @Test
    public void emptyArray()
    {
        assertEquals("[]", build(new JsonArray(new ArrayList<>())));
    }

    @Test
    public void object()
    {
        HashMap<String, JsonElement> map = new HashMap<>();
        map.put("key1", new JsonPrimitive("value1"));
        map.put("key2", new JsonPrimitive("value2"));
        map.put("key3", new JsonPrimitive(3));

        assertEquals("{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":3}", build(new JsonObject(map)));
    }

    @Test
    public void nestedObject()
    {
        HashMap<String, JsonElement> map = new HashMap<>();
        map.put("key1", new JsonPrimitive("value1"));
        map.put("key2", new JsonPrimitive("value2"));
        HashMap<String, JsonElement> map1 = new HashMap<>();
        map1.put("nkey1", new JsonPrimitive(true));
        map1.put("nkey2", new JsonPrimitive(-1));
        map.put("key3", new JsonObject(map1));

        assertEquals("{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":{\"nkey1\":true,\"nkey2\":-1}}", build(new JsonObject(map)));
    }

    @Test
    public void emptyObject()
    {
        assertEquals("{}", build(new JsonObject(new HashMap<>())));
    }

    @Test
    public void classObject()
    {
        class Example
        {
            private int id;

            private String name;

            @Serialize(false)
            private double balance;

            public int getId()
            {
                return id;
            }

            public void setId(int id)
            {
                this.id = id;
            }

            public String getName()
            {
                return name;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public double getBalance()
            {
                return balance;
            }

            public void setBalance(double balance)
            {
                this.balance = balance;
            }
        }

        Example obj = new Example();
        obj.setId(2);
        obj.setName("Lorem ipsum");
        obj.setBalance(4.51);

        assertEquals("{\"id\":2,\"name\":\"Lorem ipsum\"}", build(obj));
    }

    @Test
    public void nestedClassObject()
    {
        First first = new First();
        Second second = new Second();
        second.setIntVal(-5);
        second.setBoolVal(false);
        first.setSecond(second);

        assertEquals("{\"second\":{\"int_val\":-5,\"bool_val\":false}}", build(first));
    }

    @Test
    public void nestedClassObjectArray()
    {
        Third third = new Third();
        Second second1 = new Second();
        second1.setIntVal(-5);
        second1.setBoolVal(false);
        Second second2 = new Second();
        second2.setIntVal(12);
        second2.setBoolVal(true);
        third.setSeconds(new Second[]{second1, second2});

        assertEquals("{\"seconds\":[{\"int_val\":-5,\"bool_val\":false},{\"int_val\":12,\"bool_val\":true}]}", build(third));
    }

    @Test
    public void nestedClassObjectList()
    {
        Fifth fifth = new Fifth();
        Second second1 = new Second();
        second1.setIntVal(-5);
        second1.setBoolVal(false);
        Second second2 = new Second();
        second2.setIntVal(12);
        second2.setBoolVal(true);

        ArrayList<Second> list = new ArrayList<>();
        list.add(second1);
        list.add(second2);

        fifth.setSeconds(list);

        assertEquals("{\"seconds\":[{\"int_val\":-5,\"bool_val\":false},{\"int_val\":12,\"bool_val\":true}]}", build(fifth));
    }

    private class First
    {
        private Second second;

        public Second getSecond()
        {
            return second;
        }

        public void setSecond(Second second)
        {
            this.second = second;
        }
    }

    private class Second
    {
        private int intVal;

        private boolean boolVal;

        public int getIntVal()
        {
            return intVal;
        }

        public void setIntVal(int intVal)
        {
            this.intVal = intVal;
        }

        public boolean isBoolVal()
        {
            return boolVal;
        }

        public void setBoolVal(boolean boolVal)
        {
            this.boolVal = boolVal;
        }
    }

    private class Third
    {
        private Second[] seconds;

        public Second[] getSeconds()
        {
            return seconds;
        }

        public void setSeconds(Second[] seconds)
        {
            this.seconds = seconds;
        }
    }

    private class Fifth
    {
        private ArrayList<Second> seconds;

        public ArrayList<Second> getSeconds()
        {
            return seconds;
        }

        public void setSeconds(ArrayList<Second> seconds)
        {
            this.seconds = seconds;
        }
    }

    @Test
    public void serializerTest()
    {
        SerializersPool pool = new SerializersPool();
        pool.put(Time.class, (JsonSerializer<Time>) obj -> new JsonPrimitive(obj.delta + 1));

        Time time = new Time();
        time.delta = 7;

        JsonBuilder builder = new JsonBuilder();
        builder.setSerializersPool(pool);
        assertEquals(String.valueOf(7 + 1), builder.build(time));
    }

    private class Time
    {
        public int delta;
    }

    @Test
    public void serializeAnnotationTest()
    {
        Annotation annotation = new Annotation();
        annotation.field1 = 3;
        annotation.field2 = 4;
        annotation.field3 = 5;
        annotation.field4 = 6;

        JsonBuilder builder = new JsonBuilder();
        String json = builder.build(annotation);
        assertEquals("{\"field_4\":6}", json);
    }

    public static class Annotation
    {
        @Serialize(false)
        @Json(value = "field_1")
        public int field1;

        @Serialize(false)
        public int field2;

        @Json(value = "field_3", serialize = false)
        public int field3;

        @Json("field_4")
        public int field4;
    }
}
