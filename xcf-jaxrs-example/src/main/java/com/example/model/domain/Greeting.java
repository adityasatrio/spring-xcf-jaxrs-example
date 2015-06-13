package com.example.model.domain;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Greeting extends ResourceSupport {

	private String content;

	public Greeting() {

	}

	@JsonCreator
	public Greeting(@JsonProperty("content") String content) {
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}

}
