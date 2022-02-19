package com.github.damianwajser.model;

import com.github.damianwajser.validator.annotation.enums.MatchEnum;
import com.github.damianwajser.validator.constraint.enums.values.Countries;

public class EnumArrayOject {
	@MatchEnum(message = "hola", businessCode = "code_333", enumClass = Countries.class, isNulleable = false) Countries[] country;
}
