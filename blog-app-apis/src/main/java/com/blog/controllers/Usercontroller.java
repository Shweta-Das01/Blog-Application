package com.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.Apiresponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/users")
public class Usercontroller {
	@Autowired
	private UserService userservice;
	//post create User
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto= this.userservice.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable Integer userId){
		UserDto updateUser = this.userservice.updateUser(userDto, userId);
		return ResponseEntity.ok(updateUser);
		
	}
	@DeleteMapping("/{userId}")
	public ResponseEntity<Apiresponse> deleteUser(@RequestBody UserDto userDto,@PathVariable Integer userId){
		 this.userservice.deleteuser(userId);
		return new ResponseEntity<Apiresponse>(new Apiresponse("user deleted successfully",true),HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAlluser() {
		return ResponseEntity.ok(this.userservice.getAllusers());
	}
	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getSingleuser(@PathVariable Integer userid) {
		return ResponseEntity.ok(this.userservice.getUserById(userid));
	}

}
