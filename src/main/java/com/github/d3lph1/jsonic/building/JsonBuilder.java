package com.github.d3lph1.jsonic.building;

import com.github.d3lph1.jsonic.*;
import com.github.d3lph1.jsonic.annotations.Json;
import com.github.d3lph1.jsonic.annotations.Serialize;
import com.github.d3lph1.jsonic.utils.Str;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Constructs a json string from the program object. And formats the string.
 *
 * @author D3lph1
 */
public class JsonBuilder
{
    private BuilderOptions options;

    private SerializersPool serializersPool = new SerializersPool();

    /**
     * Nesting level of the current element.
     */
    private int nesting = 0;

    public JsonBuilder(BuilderOptions options)
    {
        this.options = options;
    }

    public JsonBuilder()
    {
        this(BuilderOptions.INLINE);
    }

    /**
     * Constructs a json string from the program object. And formats the string.
     *
     * @param object Serializable object.
     * @return JSON string.
     */
    public String build(Object object)
    {
        try {
            return build(object, new StringBuilder()).toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * {@link #build(Object)}
     *
     * @throws IllegalAccessException If you can not access the field.
     */
    @SuppressWarnings("unchecked")
    private StringBuilder build(Object object, StringBuilder sb) throws IllegalAccessException
    {
        if (object == null || object instanceof JsonNull) {
            return sb.append("null");
        }

        if (object instanceof String) {
            return sb.append("\"").append(object).append("\"");
        }

        if (object instanceof Integer
                || object instanceof Double
                || object instanceof Boolean) {
            return sb.append(object);
        }

        if (object instanceof JsonPrimitive) {
            JsonPrimitive p = (JsonPrimitive) object;
            if (p.isString()) {
                return sb.append("\"").append(p.getAsString()).append("\"");
            }
            if (p.isInteger()) {
                return sb.append(p.getAsInteger());
            }
            if (p.isDouble()) {
                return sb.append(p.getAsDouble());
            }
            if (p.isBoolean()) {
                return sb.append(p.getAsBoolean());
            }
        }

        if (object instanceof JsonArray) {
            JsonArray items = (JsonArray) object;
            sb.append('[');
            nesting++;
            afterLeftArrayBracket(sb);

            int i = 0;
            int size = items.size();
            for (JsonElement item : items) {
                beforeItem(sb);
                build(item, sb);
                if (i != size - 1) {
                    sb.append(',');
                }
                afterArrayItem(sb);
                i++;
            }
            nesting--;
            beforeRightArrayBracket(sb);

            return sb.append(']');
        }

        if (object instanceof JsonObject) {
            JsonObject o = (JsonObject) object;
            sb.append('{');
            nesting++;
            afterLeftObjectBracket(sb);
            int i = 0;
            int size = o.size();
            for (Map.Entry<String, JsonElement> entry : o.entrySet()) {
                beforeItem(sb);
                sb.append("\"")
                        .append(entry.getKey())
                        .append("\"")
                        .append(":");
                afterObjectColon(sb);

                build(entry.getValue(), sb);
                if (i != size - 1) {
                    sb.append(',');
                }
                afterObjectItem(sb);
                i++;
            }
            nesting--;
            beforeRightObjectBracket(sb);

            return sb.append('}');
        }

        // Build array.
        if (object.getClass().getComponentType() != null) {
            sb.append('[');
            nesting++;
            afterLeftArrayBracket(sb);

            int len = Array.getLength(object);
            for (int i = 0; i < len; i++) {
                beforeItem(sb);
                build(Array.get(object, i), sb);
                if (i != len - 1) {
                    sb.append(',');
                }
                afterArrayItem(sb);
            }
            nesting--;
            beforeRightArrayBracket(sb);

            return sb.append(']');
        }

        // Build list.
        if (object instanceof List) {
            List list = (List) object;

            sb.append('[');
            nesting++;
            afterLeftArrayBracket(sb);

            int i = 0;
            for (Object item : list) {
                beforeItem(sb);
                build(item, sb);
                if (i != list.size() - 1) {
                    sb.append(',');
                }
                afterArrayItem(sb);
                i++;
            }
            nesting--;
            beforeRightArrayBracket(sb);

            return sb.append(']');
        }

        if (getSerializersPool().has(object.getClass())) {
            return build(getSerializersPool().get(object.getClass()).serialize(object), sb);
        }

        // Build simple object.

        int i = 0;
        int size = 0;

        // Calculate count of fields without synthetic fields and without fields, marked by annotation Serialize(false).
        for (Field field : object.getClass().getDeclaredFields()) {
            if (!field.isSynthetic()) {
                Serialize serialize = field.getAnnotation(Serialize.class);
                Json jsonAnnotation = field.getAnnotation(Json.class);
                if ((serialize == null && jsonAnnotation == null) ||
                        !(serialize != null && !serialize.value()) &&
                        (serialize != null && serialize.value() ||
                                (jsonAnnotation != null && jsonAnnotation.serialize()))
                        ) {
                    size++;
                }
            }
        }

        sb.append('{');
        nesting++;
        afterLeftObjectBracket(sb);

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isSynthetic()) {
                Serialize serialize = field.getAnnotation(Serialize.class);
                Json jsonAnnotation = field.getAnnotation(Json.class);

                if ((serialize == null && jsonAnnotation == null) ||
                        !(serialize != null && !serialize.value()) &&
                                (serialize != null && serialize.value() ||
                                        (jsonAnnotation != null && jsonAnnotation.serialize()))
                        ) {
                    beforeItem(sb);
                    sb.append("\"")
                            .append(resolveName(field))
                            .append("\"")
                            .append(":");
                    afterObjectColon(sb);

                    build(field.get(object), sb);
                    if (i != size - 1) {
                        sb.append(',');
                    }
                    afterObjectItem(sb);
                    i++;
                }
            }
            field.setAccessible(false);
        }

        nesting--;
        beforeRightObjectBracket(sb);

        sb.append('}');

        return sb;
    }

    public void setSerializersPool(SerializersPool serializersPool)
    {
        this.serializersPool = serializersPool;
    }

    public SerializersPool getSerializersPool()
    {
        return serializersPool;
    }

    private String resolveName(Field field)
    {
        Json annotation = field.getAnnotation(Json.class);
        if (annotation != null) {
            return annotation.value();
        }

        return options.getNamingStrategy().resolve(field.getName());
    }

    // Array

    private void afterLeftArrayBracket(StringBuilder sb)
    {
        if (options.isNewLineAfterLeftArrayBracket()) {
            sb.append('\n');
        }
    }

    private void beforeItem(StringBuilder sb)
    {
        sb.append(Str.repeat(" ", options.getIndent() * nesting));
    }

    private void afterArrayItem(StringBuilder sb)
    {
        if (options.isNewLineAfterArrayItem()) {
            sb.append('\n');
        }
    }

    private void beforeRightArrayBracket(StringBuilder sb)
    {
        sb.append(Str.repeat(" ", options.getIndent() * nesting));
    }

    // Object

    private void afterLeftObjectBracket(StringBuilder sb)
    {
        if (options.isNewLineAfterLeftObjectBracket()) {
            sb.append('\n');
        }
    }

    private void afterObjectItem(StringBuilder sb)
    {
        if (options.isNewLineAfterObjectItem()) {
            sb.append('\n');
        }
    }

    private void afterObjectColon(StringBuilder sb)
    {
        sb.append(Str.repeat(" ", options.getSpacesAfterObjectColon()));
    }

    private void beforeRightObjectBracket(StringBuilder sb)
    {
        sb.append(Str.repeat(" ", options.getIndent() * nesting));
    }
}
