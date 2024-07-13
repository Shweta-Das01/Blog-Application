package com.blog.exception;



import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.blog.payloads.Apiresponse;

@ControllerAdvice
public class GlobalExceptionHandeling {
	@ExceptionHandler(ResourceNotFoundException.class)
 public ResponseEntity<Apiresponse> resourcenotfound(ResourceNotFoundException ex){
		String message =ex.getMessage();
		Apiresponse apire =new Apiresponse(message,false);
	return new ResponseEntity<Apiresponse>(apire,HttpStatus.NOT_FOUND);
	 
 }
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>>  handleMethodnotvalid(MethodArgumentNotValidException ex){
		Map<String, String> resp=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
		
	}
}
