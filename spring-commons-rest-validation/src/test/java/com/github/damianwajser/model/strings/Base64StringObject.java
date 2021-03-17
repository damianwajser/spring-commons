package com.github.damianwajser.model.strings;

import com.github.damianwajser.validator.annotation.strings.Base64;

public class Base64StringObject {

    @Base64(message = "error", businessCode = "a-400")
    private String value;

    public Base64StringObject() {
    }

    public Base64StringObject(String value) {
        super();
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
