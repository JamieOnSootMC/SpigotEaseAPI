package dev.jamieisgeek;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Arguments {
  Argument[] value();
}

@Retention(RetentionPolicy.RUNTIME)
public @interface Argument {
  String Type();
  String[] Options() default {};
}