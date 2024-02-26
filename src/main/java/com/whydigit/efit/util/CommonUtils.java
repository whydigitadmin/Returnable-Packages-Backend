package com.whydigit.efit.util;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {
	public static String trimLastCharacter(String input) {
		return Optional.ofNullable(input).filter(s -> !s.isEmpty()).map(s -> s.substring(0, s.length() - 1))
				.orElse(input);
	}
}
