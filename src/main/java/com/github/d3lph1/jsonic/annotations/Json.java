package com.github.d3lph1.jsonic.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation specifies the name that the object's JSON key has and also whether it
 * should be serialized / deserialized.
 * For example:
 *  <code>
 *      class Example
 *      {
 *          \@Json("first_value")
 *          public int firstValue;
 *      }
 *
 *      // ...
 *
 *      Example = new Example();
 *      example.firstValue = 33;
 *      Jsonic jsonic = new Jsonic();
 *      System.out.println(jsonic.toJson(example));
 *
 *      example = jsonic.fromJson("{\"first_value\":12}", Example.class);
 *      System.out.println(example.firstValue);
 *  <code/>
 *  Result:
 *      {"first_value":33}
 *      12
 *
 * More information about serialization: {@link Serialize}
 * More information about deserialization:  {@link Deserialize}
 *
 * @author D3lph1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Json
{
    /**
     * Name of JSON object key.
     */
    String value();

    /**
     * @see Serialize
     */
    boolean serialize() default true;

    /**
     * @see Deserialize
     */
    boolean deserialize() default true;
}
