package com.github.damianwajser.model.number;

import com.github.damianwajser.validator.annotation.number.DecimalMin;

import java.math.BigDecimal;

public class DecimalMinInclusiveObject {

    @DecimalMin(value = "0.0", businessCode = "a-400")
    private BigDecimal value;

    public DecimalMinInclusiveObject() {
    }

    public DecimalMinInclusiveObject(BigDecimal value) {
        super();
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
