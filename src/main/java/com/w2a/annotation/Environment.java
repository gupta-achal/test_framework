package com.w2a.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Environment {
    Level value(); // This should match how you use it

    enum Level {
        PROD,
        STAGE,
        DEV,
        QA
    }
}
