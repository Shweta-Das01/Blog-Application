package com.blog.services.impl;

import java.util.List;

import java.util.stream.Collectors;

import com.blog.exception.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.payloads.UserDto;
import com.blog.repository.UserRepository;
import com.blog.services.UserService;
@Service
public class UserServiceImpl implements UserService {
   @Autowired

	private UserRepository userRepo;
   @Autowired
   private ModelMapper modelMap;
   
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User savedUser=this.userRepo.save(user);
		return this.UserTodto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userid) {
		User user=this.userRepo.findById(userid)
				.orElseThrow(()->new ResourceNotFoundException("User","id",userid));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updateduser = this.userRepo.save(user);
		UserDto userDto1=this.UserTodto(updateduser);
		return userDto1;
	}
		

	@Override
	public UserDto getUserById(Integer userid) {
		User user=this.userRepo.findById(userid)
				.orElseThrow(()->new ResourceNotFoundException("User","id",userid));
		return this.UserTodto(user);
	}

	@Override
	public List<UserDto> getAllusers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.UserTodto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteuser(Integer userid) {
		User user=this.userRepo.findById(userid).orElseThrow(()->new ResourceNotFoundException(
				"User", "id", userid));
		
		this.userRepo.delete(user);

	}
 public User dtoToUser(UserDto userDto) {
	 User user=this.modelMap.map(userDto, User.class);
	 //user.setUser_id(userDto.getUser_id());
	 //user.setName(userDto.getName());
	 //user.setEmail(userDto.getEmail());
	 //user.setPassword(userDto.getPassword());
	//user.setAbout(userDto.getAbout());
	return user;
	 
 }
 public UserDto UserTodto(User user) {
	 UserDto userDto=this.modelMap.map(user, UserDto.class);
	 //userDto.setUser_id(user.getUser_id());
	 //userDto.setName(user.getName());
	 //userDto.setEmail(user.getEmail());
	 //userDto.setPassword(user.getPassword());
	 //userDto.setAbout(user.getAbout());
	return userDto;
	 
 }
}
