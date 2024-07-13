package com.blog.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	String resourceName;
	  String field;
	  long fieldvalue;
	public ResourceNotFoundException(String resourceName, String field, long fieldvalue) {
		super(String.format("%s is not found with %s :%s", resourceName,field,fieldvalue));
		this.resourceName = resourceName;
		this.field = field;
		this.fieldvalue = fieldvalue;

}
}
