package com.github.damianwajser.controllers;

import com.github.damianwajser.exceptions.impl.internalservererror.InternalServerErrorException;
import com.github.damianwajser.model.FooObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class InternalServerErrorController {

    @PostMapping("/internalservererror")
    private FooObject internalServerError() throws InternalServerErrorException {
        throw new InternalServerErrorException("2020", "internalServerError", Optional.empty());
    }
}
