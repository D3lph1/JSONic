package com.github.d3lph1.jsonic.building;

import com.github.d3lph1.jsonic.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

/**
 * @author D3lph1
 */
public class JsonPrettyBuilderTest
{
    private String build(JsonElement element)
    {
        JsonBuilder builder = new JsonBuilder(BuilderOptions.PRETTY);
        return builder.build(element);
    }

    @Test
    public void array()
    {
        ArrayList<JsonElement> list = new ArrayList<JsonElement>() {{
            add(new JsonPrimitive(1));
            add(new JsonPrimitive(2));
            add(new JsonPrimitive(3));
        }};

        // [
        //     1,
        //     2,
        //     3
        // ]
        assertEquals("[\n    1,\n    2,\n    3\n]", build(new JsonArray(list)));
    }

    @Test
    public void nestedArray()
    {
        ArrayList<JsonElement> list = new ArrayList<JsonElement>() {{
            add(new JsonPrimitive(1));
            ArrayList<JsonElement> list = new ArrayList<JsonElement>() {{
                add(new JsonPrimitive(2));
                add(new JsonPrimitive("Lorem ipsum"));
                add(new JsonPrimitive(true));
            }};
            add(new JsonArray(list));
            add(new JsonPrimitive(3));
        }};

        // [
        //     1,
        //     [
        //         2,
        //         "Lorem ipsum",
        //         true
        //     ],
        //     3
        // ]
        assertEquals("[\n    1,\n    [\n        2,\n        \"Lorem ipsum\",\n        true\n    ],\n    3\n]", build(new JsonArray(list)));
    }

    @Test
    public void object()
    {
        LinkedHashMap<String, JsonElement> map = new LinkedHashMap<String, JsonElement>() {{
            put("one", new JsonPrimitive(1));
            put("key", new JsonPrimitive("value"));
            put("test", new JsonPrimitive(true));
        }};
        JsonObject obj = new JsonObject(map);

        // {
        //     "one": 1,
        //     "key": "value",
        //     "test": true
        // }
        assertEquals("{\n    \"one\": 1,\n    \"key\": \"value\",\n    \"test\": true\n}", build(obj));
    }

    @Test
    public void nestedObject()
    {
        LinkedHashMap<String, JsonElement> map = new LinkedHashMap<String, JsonElement>() {{
            put("one", new JsonPrimitive(1));
            LinkedHashMap<String, JsonElement> map = new LinkedHashMap<String, JsonElement>() {{
                put("lorem", new JsonPrimitive("ipsum"));
                put("user", JsonNull.getInstance());
            }};
            put("obj", new JsonObject(map));
            put("test", new JsonPrimitive(true));
        }};
        JsonObject obj = new JsonObject(map);

        // {
        //     "one": 1,
        //     "obj": {
        //         "lorem": "ipsum",
        //         "user": null
        //     },
        //     "test": true
        // }
        assertEquals("{\n    \"one\": 1,\n    \"obj\": {\n        \"lorem\": \"ipsum\",\n        \"user\": null\n    },\n    \"test\": true\n}", build(obj));
    }

    @Test
    public void mixed()
    {
        LinkedHashMap<String, JsonElement> map = new LinkedHashMap<String, JsonElement>() {{
            put("one", new JsonPrimitive(1));
            LinkedHashMap<String, JsonElement> map = new LinkedHashMap<String, JsonElement>() {{
                ArrayList<JsonElement> list = new ArrayList<JsonElement>() {{
                    add(new JsonPrimitive(1));
                    add(new JsonPrimitive(2));
                    add(new JsonPrimitive(3));
                }};
                put("list", new JsonArray(list));
                put("bool", new JsonPrimitive(false));
            }};
            put("obj", new JsonObject(map));
        }};
        JsonObject obj = new JsonObject(map);

        // {
        //     "one": 1,
        //     "obj": {
        //         "list": [
        //             1,
        //             2,
        //             3
        //         ],
        //         "bool": false
        //     }
        // }
        assertEquals("{\n    \"one\": 1,\n    \"obj\": {\n        \"list\": [\n            1,\n            2,\n            3\n        ],\n        \"bool\": false\n    }\n}", build(obj));
    }
}
