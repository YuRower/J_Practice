package com.nixsolutions.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public void serialize(Date date, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		System.out.println("Date serialze ////////////////////////" + date);
		generator.writeString(formatter.format(date));
		throw new RuntimeException();
	}

}