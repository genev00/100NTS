package com.a100nts.utils;

import javax.persistence.AttributeConverter;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ofPattern;

public class ListToStringConverter implements AttributeConverter<List<String>, String> {

	public final static DateTimeFormatter DATE_TIME_FORMATTER = ofPattern("dd.MM.yyyy, HH:mm");
	private final static String SPLIT_CHARACTER = ";";

	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		return attribute != null
				? String.join(SPLIT_CHARACTER, attribute)
				: null;
	}

	@Override
	public List<String> convertToEntityAttribute(String dbData) {
		return dbData != null
				? Arrays.stream(dbData.split(SPLIT_CHARACTER))
					.collect(Collectors.toList())
				: Collections.emptyList();
	}

}
