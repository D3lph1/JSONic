package com.github.d3lph1.jsonic.typing;

import com.github.d3lph1.jsonic.*;
import com.github.d3lph1.jsonic.annotations.Deserialize;
import com.github.d3lph1.jsonic.annotations.Json;
import com.github.d3lph1.jsonic.lexing.Tokenizer;
import com.github.d3lph1.jsonic.lexing.readers.StringReader;
import com.github.d3lph1.jsonic.naming.UnderCaseNamingStrategy;
import com.github.d3lph1.jsonic.parsing.Parser;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author D3lph1
 */
public class TypeConverterTest
{
    private JsonElement element() throws FileNotFoundException
    {
        Jsonic jsonic = new Jsonic();
        return jsonic.fromJson(new FileInputStream(getClass().getResource("/com/d3lph1/jsonic/test/purchase.json").getFile()));
    }

    @Test
    public void purchase() throws ReflectiveOperationException, FileNotFoundException
    {
        JsonElement el = element();
        TypeConverter tc = new TypeConverter(new UnderCaseNamingStrategy());
        Purchase purchase = tc.toType(el, Purchase.class);
        assertEquals(1, purchase.getCode());
        assertEquals("wait", purchase.getStatus());
        assertEquals(true, purchase.isCompleted());
        assertEquals(Integer.valueOf(1), purchase.getUsers()[0].getId());
        assertEquals("D3lph1", purchase.getUsers()[0].getUsername());
        assertEquals(100.5, purchase.getUsers()[0].getBalance(), 0.001);
        // Roles
        assertEquals("USER", purchase.getUsers()[0].getRoles().get(0).getAsString());
        assertEquals("ADMIN", purchase.getUsers()[0].getRoles().get(1).getAsString());

        assertEquals(Integer.valueOf(2), purchase.getUsers()[1].getId());
        assertEquals("example", purchase.getUsers()[1].getUsername());
        assertEquals(4, purchase.getUsers()[1].getBalance(), 0.001);

        // Roles
        assertEquals("USER", purchase.getUsers()[1].getRoles().get(0).getAsString());

        assertNull(purchase.getUsers()[1].getPassword());
    }

    static class Purchase
    {
        private int code;

        private String status;

        private User[] usersList;

        @Json("is_completed")
        private boolean completed;

        int getCode()
        {
            return code;
        }

        String getStatus()
        {
            return status;
        }

        User[] getUsers()
        {
            return usersList;
        }

        boolean isCompleted()
        {
            return completed;
        }
    }

    static class User
    {
        private Integer id;

        private String username;

        @Deserialize(false)
        private String password;

        private double balance;

        private ArrayList<JsonPrimitive> roles;

        Integer getId()
        {
            return id;
        }

        String getUsername()
        {
            return username;
        }

        String getPassword()
        {
            return password;
        }

        double getBalance()
        {
            return balance;
        }

        public ArrayList<JsonPrimitive> getRoles()
        {
            return roles;
        }
    }

    @Test
    public void deserializerTest() throws ReflectiveOperationException
    {
        DeserializersPool pool = new DeserializersPool();
        pool.put(Time.class, (JsonDeserializer<Time>) obj -> {
            Time time = new Time();
            time.delta = obj.getAsObject().get("delta").getAsPrimitive().getAsInteger() - 1;
            return time;
        });

        TypeConverter converter = new TypeConverter(new UnderCaseNamingStrategy());
        converter.setDeserializersPool(pool);

        Time time = new Time();
        time.delta = 7;

        assertEquals(
                time.delta,
                converter.toType(new Parser(new Tokenizer(new StringReader("{\"delta\":8}"))).parse(), Time.class).delta
        );
    }

    private class Time
    {
        public int delta;
    }

    @Test
    public void deserializeAnnotationTest() throws ReflectiveOperationException
    {
        Jsonic jsonic = new Jsonic();
        JsonObject obj = jsonic.fromJson("{\"field_1\": 1, \"field_2\": 2, \"field_3\": 3, \"field_4\": 4}").getAsObject();

        TypeConverter typeConverter = new TypeConverter(new UnderCaseNamingStrategy());
        Annotation annotation = typeConverter.toType(obj, Annotation.class);
        assertEquals(0, annotation.field1);
        assertEquals(0, annotation.field2);
        assertEquals(0, annotation.field3);
        assertEquals(4, annotation.field4);
    }

    public static class Annotation
    {
        @Deserialize(false)
        @Json(value = "field_1")
        public int field1;

        @Deserialize(false)
        public int field2;

        @Json(value = "field_3", deserialize = false)
        public int field3;

        @Json(value = "field_4")
        public int field4;
    }
}
