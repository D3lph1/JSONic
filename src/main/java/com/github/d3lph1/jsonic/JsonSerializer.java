package com.github.d3lph1.jsonic;

/**
 * It is an interface for the serializer classes. Such classes are used to build, different
 * from the standard serialization logic of the object.
 * For example. The serializer extracts the cube root from the field:
 *  <code>
 *   public static class Digit
 *   {
 *       public double value;
 *   }
 *
 *   // ...
 *
 *   Jsonic jsonic = new Jsonic();
 *   jsonic.getSerializersPool().put(
 *       Digit.class,
 *       (JsonSerializer<Digit>) obj -> new JsonPrimitive(Math.pow(obj.value, 1. / 3))
 *   );
 *
 *   Digit digit = new Digit();
 *   digit.value = 27;
 *
 *   System.out.println(jsonic.toJson(digit));
 *  <code/>
 *      Result: 3.0
 *
 * @param <T>
 * @author D3lph1
 */
public interface JsonSerializer<T>
{
    JsonElement serialize(T obj);
}
