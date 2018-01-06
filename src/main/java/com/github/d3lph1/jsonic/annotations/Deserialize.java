package com.github.d3lph1.jsonic.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is intended for marking whether the field should be deserialized or not. The annotation takes
 * a Boolean value. If this value is three, the field will be deserialized. If false - will not be deserialized.
 * If there is no annotation, the field will also be deserialized.
 * For example:
 *  <code>
 *      class User
 *      {
 *          public int id;
 *
 *          \@Deserialize(false)
 *          public String login;
 *
 *          public String password;
 *      }
 *
 *      // ...
 *
 *      Jsonic jsonic = new Jsonic();
 *      User user = jsonic.fromJson("{\"id\":1,\"login\":\"D3lph1\",\"password\":\"123456\"}", User.class);
 *      System.out.println(user.id);
 *      System.out.println(user.login);
 *  </code>
 *  Result:
 *      1
 *      D3lph1
 *
 * This annotation has a higher priority than the JSON annotation.
 *
 * @author D3lph1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Deserialize
{
    boolean value();
}
