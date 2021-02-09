package com.github.damianwajser.validator.constraint.strings;

import com.github.damianwajser.validator.annotation.strings.Base64;
import com.github.damianwajser.validator.constraint.AbstractConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.tomcat.util.codec.binary.Base64.isBase64;

public class Base64Constraint extends AbstractConstraint implements ConstraintValidator<Base64, Object> {

    @Override
    public void initialize(Base64 field) {
        super.excludes = field.excludes();
        super.isNulleable = field.isNulleable();
    }

    /**
     * This method returns true if any character is not a valid character in the Base64 alphabet.
     * More information of used method could be found here:
     * https://commons.apache.org/proper/commons-codec/apidocs/org/apache/commons/codec/binary/Base64.html#isBase64-java.lang.String-
     */
    @Override
    protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
        return !isBase64(field.toString());
    }
}
