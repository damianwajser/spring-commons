package com.github.damianwajser.controllers;

import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import com.github.damianwajser.model.EnumArrayOject;
import com.github.damianwajser.model.EnumModel;
import com.github.damianwajser.model.FooObject;
import com.github.damianwajser.validator.annotation.enums.MatchEnum;
import com.github.damianwajser.validator.annotation.number.Min;
import com.github.damianwajser.validator.constraint.enums.values.Countries;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@RestController
@Validated
public class BadRequestController {

	@PostMapping("/badrequest")
	private FooObject badRequest() throws BadRequestException {
		throw new BadRequestException("400", "badrequest", Optional.empty());
	}

	@PostMapping("/badrequest/message/{one}")
	private FooObject badRequest_message(@PathVariable String one, @Value("${last.argument}") String lastArgument) throws BadRequestException {
		throw new BadRequestException("400", "{spring.commons.message.with.args}", one, new Date(), Instant.now().toEpochMilli(), lastArgument);
	}

	@PostMapping("/badrequest/enum/{country}")
	public FooObject badRequest_enum(@PathVariable @Valid @MatchEnum(message = "hola", businessCode = "code", enumClass = Countries.class) Countries country) throws BadRequestException {
		return null;
	}

	@GetMapping("/badrequest/enum_array")
	public FooObject badRequest_enum_1(@RequestParam @Valid @MatchEnum(message = "hola", businessCode = "code", enumClass = Countries.class, isNulleable = true) Countries[] country) throws BadRequestException {
		return null;
	}

	@GetMapping("/badrequest/enum_array_in_object")
	public FooObject badRequest_enum_1(@Valid EnumArrayOject valid) throws BadRequestException {
		return null;
	}

	@PostMapping("/badrequest/enum")
	public FooObject badRequest_enum(@RequestBody @Valid EnumModel model) throws BadRequestException {
		return null;
	}

	@GetMapping("/badrequest/{age}")
	public Object badRequestWithPath(@PathVariable @Valid @Min(value = 5, businessCode = "as-400", message = "{lala}") Integer age) {
		return String.format("The number is %s", age);
	}
}
