package com.github.damianwajser.generators;

import javax.servlet.http.HttpServletRequest;

public interface RequestIdGenerator {
	String getRequestId(HttpServletRequest request);
}
