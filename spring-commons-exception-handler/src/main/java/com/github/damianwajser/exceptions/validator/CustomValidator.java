package com.github.damianwajser.exceptions.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class CustomValidator {

    private final Validator validator;

    @Autowired
    public CustomValidator(Validator validator) {
        this.validator = validator;
    }

    public <T> void validate(T object, Class<?>... groups ) {
        Set constraintViolations = validator.validate( object, groups );
        if( !constraintViolations.isEmpty() )
            throw new ConstraintViolationException( constraintViolations );
    }

    public <T> void validate(T object ) {
        Set constraintViolations = validator.validate( object );
        if( !constraintViolations.isEmpty() )
            throw new ConstraintViolationException( constraintViolations );
    }

}
