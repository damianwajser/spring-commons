package com.github.damianwajser.validator.constraint.global;

import com.github.damianwajser.validator.annotation.global.OneNotNull;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

@Component
public class OneNotNullValidator extends AbstractConstraint implements ConstraintValidator<OneNotNull, Object> {

    private String[] fields;

    @Override
    public void initialize(final OneNotNull field) {
        super.initialize(field.excludes(), field.onlyIn(), field.isNulleable());
        fields = field.fields();
    }

    @Override
    protected boolean hasError(Object obj, ConstraintValidatorContext cxt) {
        final BeanWrapperImpl beanWrapper = new BeanWrapperImpl(obj);

        return Arrays.stream(fields)
                .map(beanWrapper::getPropertyValue)
                .filter(Objects::isNull)
                .count()
                == fields.length;
    }
}
