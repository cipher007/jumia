package com.jumia.exercise.dto;

/**
 * 
 * @author Hany
 *
 * Enum to obtain code with country and the pattern associated with it
 */
public enum Country {
	CAMEROON("(237)", "Cameroon", "\\(237\\)\\ ?[2368]\\d{7,8}$"), 
	ETHIOPIA("(251)" , "Ethiopia", "\\(251\\)\\ ?[1-59]\\d{8}$"), 
	MOROCCO("(212)", "Morocco", "\\(212\\)\\ ?[5-9]\\d{8}$"), 
	MOZAMBIQUE ("(258)", "Mozambique", "\\(258\\)\\ ?[28]\\d{7,8}$"), 
	UGANDA("(256)", "Uganda", "\\(256\\)\\ ?\\d{9}$");

	private final String code;
	private final String name;
	private final String regex;

	private Country(String code, String name, String regex) {
		this.code = code;
		this.name = name;
		this.regex = regex;
	}

	public final String getName() {
		return name;
	}
	
	public final String getCode() {
		return code;
	}
	
	public final String getRegex() {
		return regex;
	}

	@Override
	public final String toString() {
		return name + " | Country code: " + code;
	}
}
