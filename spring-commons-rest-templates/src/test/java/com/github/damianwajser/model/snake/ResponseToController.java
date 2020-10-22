package com.github.damianwajser.model.snake;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ResponseToController {

    public String getSomeValue() {
        return someValue;
    }

    public void setSomeValue(String someValue) {
        this.someValue = someValue;
    }

    @JsonAlias({"someValue", "some_value"})
    private String someValue;

}
