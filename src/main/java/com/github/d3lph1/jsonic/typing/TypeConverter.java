package com.github.d3lph1.jsonic.typing;

import com.github.d3lph1.jsonic.*;
import com.github.d3lph1.jsonic.annotations.Deserialize;
import com.github.d3lph1.jsonic.annotations.Json;
import com.github.d3lph1.jsonic.naming.NamingStrategy;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Performs type conversion.
 *
 * @author D3lph1
 */
public class TypeConverter
{
    private DeserializersPool deserializersPool = new DeserializersPool();

    private NamingStrategy namingStrategy;

    public TypeConverter(NamingStrategy namingStrategy)
    {
        this.namingStrategy = namingStrategy;
    }

    /**
     * Converts a json element to an object of a given type.
     * Conversion occurs automatically. If a deserializer for a given type is found in the {@link #deserializersPool,
     * then it is used.
     * For example:
     *      JsonObject: {
     *          "mark": "Lamborghini",
     *          "model": "Aventador",
     *          "license_plate": "111a"
     *      }
     * Converts to type:
     * class Car
     * {
     *     private String mark = "Lamborghini";
     *     private String model = "Aventador";
     *     private String licensePlate = "111a";
     * }
     * It is possible to transfer classes for built-in types:
     *      JsonPrimitive: "lorem ipsum"
     * Converts to type:
     * String "lorem ipsum";
     *
     * @param element Convertible item.
     * @param newType Type to convert to.
     * @param <T> Object of type to convert to.
     * @return Object of type to convert to.
     * @throws ReflectiveOperationException If you can not get declared type constructors.
     */
    @SuppressWarnings("unchecked")
    public <T> T toType(JsonElement element, Class<T> newType) throws ReflectiveOperationException
    {
        if (getDeserializersPool().has(newType)) {
            return (T) getDeserializersPool().get(newType).deserialize(element);
        }

        T obj = newType.getDeclaredConstructor().newInstance();

        for (Field field : newType.getDeclaredFields()) {
            field.setAccessible(true);
            Deserialize deserialize = field.getAnnotation(Deserialize.class);
            if (!(deserialize == null || deserialize.value())) {
                field.setAccessible(false);
                continue;
            }
            Json jsonAnnotation = field.getAnnotation(Json.class);
            if (!(jsonAnnotation == null || jsonAnnotation.deserialize())) {
                field.setAccessible(false);
                continue;
            }

            JsonElement el = element.getAsObject().get(resolveName(field, element.getAsObject()));

            if (el != null) {
                Class type = field.getType();

                if (type == JsonElement.class) {
                    field.set(obj, el);
                } else if (type == JsonArray.class) {
                    field.set(obj, el.getAsArray());
                } else if (type == JsonObject.class) {
                    field.set(obj, el.getAsObject());
                } else if (type == JsonNull.class) {
                    field.set(obj, el.getAsNull());
                } else if (type == JsonPrimitive.class) {
                    field.set(obj, el.getAsPrimitive());
                } else if (type == Integer.class || type == int.class) {
                    field.set(obj, el.getAsPrimitive().getAsInteger());
                } else if (type == Double.class || type == double.class) {
                    field.set(obj, el.getAsPrimitive().getAsDouble());
                } else if (type == Boolean.class || type == boolean.class) {
                    field.set(obj, el.getAsPrimitive().getAsBoolean());
                } else if (type == String.class) {
                    field.set(obj, el.getAsPrimitive().getAsString());
                } else if (List.class.isAssignableFrom(type)) {
                    ArrayList<JsonElement> list = new ArrayList<>();
                    for (JsonElement item : el.getAsArray()) {
                        list.add(item);
                    }
                    field.set(obj, list);
                } else if (field.getType().getComponentType() != null) {
                    Object arr = Array.newInstance(field.getType().getComponentType(), el.getAsArray().size());
                    int i = 0;
                    for (JsonElement item : el.getAsArray()) {
                        Array.set(arr, i++, getType(item, field.getType().getComponentType()));
                    }
                    field.set(obj, arr);
                } else {
                    field.set(obj, toType(el, field.getType()));
                }
            }
            field.setAccessible(false);
        }

        return obj;
    }

    private Object getType(JsonElement element, Class<?> type) throws ReflectiveOperationException
    {
        if (type == JsonElement.class) {
            return element;
        } else if (type == JsonArray.class) {
            return element.getAsArray();
        } else if (type == JsonObject.class) {
            return element.getAsObject();
        } else if (type == JsonNull.class) {
            return element.getAsNull();
        } else if (type == JsonPrimitive.class) {
            return element.getAsPrimitive();
        } else if (type == Integer.class || type == int.class) {
            return element.getAsPrimitive().getAsInteger();
        } else if (type == Double.class || type == double.class) {
            return element.getAsPrimitive().getAsDouble();
        } else if (type == Boolean.class || type == boolean.class) {
            return element.getAsPrimitive().getAsBoolean();
        } else if (type == String.class) {
            return element.getAsPrimitive().getAsString();
        }

        return toType(element, type);
    }

    private String resolveName(Field field, JsonObject object)
    {
        Json annotation = field.getAnnotation(Json.class);
        if (annotation != null) {
            return annotation.value();
        }

        String uc = namingStrategy.resolve(field.getName());
        if (object.get(uc) != null) {
            return uc;
        }

        return field.getName();
    }

    public DeserializersPool getDeserializersPool()
    {
        return deserializersPool;
    }

    public void setDeserializersPool(DeserializersPool deserializersPool)
    {
        this.deserializersPool = deserializersPool;
    }
}
