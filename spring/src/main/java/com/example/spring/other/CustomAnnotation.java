package com.example.spring.other;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.MODULE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomAnnotation {
}
