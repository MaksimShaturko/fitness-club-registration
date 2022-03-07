package com.epam.fitness_club_registration.annotations;

import com.epam.fitness_club_registration.entity.TypeOfField;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation ValidDataEntering
 * <p>
 * Applies to class fields. While the application is running, it calls to
 * annotation fields for user interaction and verification of data entered
 * by the user
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDataEntering {
    String messageForUser();

    String requiredRegEx();

    TypeOfField typeOfField();

    int max() default 0;

    int min() default 0;

    String nameEnum() default "";

}
