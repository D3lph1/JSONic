package com.github.d3lph1.jsonic.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is intended for marking whether the field should be serialized or not. The annotation takes
 * a Boolean value. If this value is three, the field will be serialized. If false - will not be serialized.
 * If there is no annotation, the field will also be serialized.
 * For example:
 *  <code>
 *      class User
 *      {
 *          \@Serialize(true)
 *          public int id;
 *
 *          public String login;
 *
 *          \@Serialize(false)
 *          public String password;
 *      }
 *
 *      // ...
 *
 *      User user = new User();
 *      user.id = 1;
 *      user.login = "D3lph1";
 *      user.password = "123456";
 *
 *      Jsonic jsonic = new Jsonic();
 *      System.out.println(jsonic.toJson(user));
 *  </code>
 *  Result:
 *  {"id":1,"login":"D3lph1"}
 *
 * This annotation has a higher priority than the JSON annotation.
 *
 * @author D3lph1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Serialize
{
    boolean value();
}
