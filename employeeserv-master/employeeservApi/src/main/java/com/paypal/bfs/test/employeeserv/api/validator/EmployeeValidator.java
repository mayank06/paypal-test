package com.paypal.bfs.test.employeeserv.api.validator;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidator implements ConstraintValidator<ValidEmployee, Employee> {

    @Override
    public void initialize(ValidEmployee constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Employee value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();
        if (value == null || value.getFirstName() == null || value.getLastName() == null || value.getAddress() == null) {
            context.buildConstraintViolationWithTemplate("Employee object is not present/invalid").addConstraintViolation();
            return false;
        }
        return true;
    }
}
