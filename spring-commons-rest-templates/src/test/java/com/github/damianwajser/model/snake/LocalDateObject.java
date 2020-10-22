package com.github.damianwajser.model.snake;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;

public class LocalDateObject {

    @JsonAlias({"someValue", "some_value"})
    private LocalDate someValue;

    public LocalDate getSomeValue() {
        return someValue;
    }

    public void setSomeValue(LocalDate someValue) {
        this.someValue = someValue;
    }
}
