package com.github.damianwajser.controllers;

import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import com.github.damianwajser.model.FooObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/i18n")
public class I18nBadRequestController {

	@PostMapping("/literal")
	private FooObject literal() throws BadRequestException {
		throw new BadRequestException("literal", "badrequest", Optional.empty());
	}

	@PostMapping("/withproperties")
	private FooObject withproperties() throws BadRequestException {
		throw new BadRequestException("withproperties", "{spring.commons.validation.constraints.NotEmpty.message}", Optional.empty());
	}

	@PostMapping("/withproperties/notfound")
	private FooObject withpropertiesNotfound() throws BadRequestException {
		throw new BadRequestException("notfound", "{spring.commons}", Optional.empty());
	}
}
