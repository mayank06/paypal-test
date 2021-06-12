package com.paypal.bfs.test.employeeserv.api.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({FIELD, TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = EmployeeValidator.class)
@Documented
public @interface ValidEmployee {

    Class<?>[] groups() default {};

    String message() default "";

    Class<? extends Payload>[] payload() default {};

}
