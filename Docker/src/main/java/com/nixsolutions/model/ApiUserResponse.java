package com.nixsolutions.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlSeeAlso({ ArrayList.class, User.class })
@XmlType(name = "")
public class ApiUserResponse<T> {
	@XmlElement
	private int status;
	@XmlElement
	private String message;
	@XmlAnyElement
	private Object result;

	public ApiUserResponse(int status, String message, Object result) {
		this.status = status;
		this.message = message;
		this.result = result;
	}

	public ApiUserResponse() {
	}

}
