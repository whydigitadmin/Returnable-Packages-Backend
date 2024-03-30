package com.whydigit.efit.util;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.whydigit.efit.common.CommonConstant;

@Component
public class CommonUtils {
	public static String trimLastCharacter(String input) {
		return Optional.ofNullable(input).filter(s -> !s.isEmpty()).map(s -> s.substring(0, s.length() - 1))
				.orElse(input);
	}
	
	public static String constructUniqueFileName(String originalFilename, String type, int fileCount, String date) {
		String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
		return new StringBuilder(type).append(CommonConstant.UNDERSCORE).append(date).append(CommonConstant.UNDERSCORE)
				.append(fileCount).append(extension).toString();
	}
}
