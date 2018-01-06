package com.github.d3lph1.jsonic;

/**
 * It is an interface for deserializer classes. Such classes are used to build, different
 * from the standard deserialization logic of the object.
 * For example. The deserializer raises the value of the value key to 3 degrees:
 *      <code>
 *          public static class Digit
 *          {
 *              public double value;
 *          }
 *
 *          // ...
 *
 *          Jsonic jsonic = new Jsonic();
 *          jsonic.getDeserializersPool().put(
 *              Digit.class,
 *              (JsonDeserializer<Digit>) el -> {
 *                  Digit digit = new Digit();
 *                  digit.value = Math.pow(el.getAsObject().get("value").getAsPrimitive().getAsInteger(), 3);
 *                  return digit;
 *              }
 *          );
 *          System.out.println(jsonic.fromJson("{\"value\":3}", Digit.class).value);
 *      </code>
 *      Result: 27.0
 *
 * @param <T>
 * @author D3lph1
 */
public interface JsonDeserializer<T>
{
    T deserialize(JsonElement element);
}
