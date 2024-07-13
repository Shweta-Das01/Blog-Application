package com.blog.services;

import java.util.List;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import com.blog.payloads.UserDto;

public interface UserService  {
  UserDto  createUser(UserDto user);
  UserDto updateUser(UserDto user,Integer userid);
  UserDto getUserById(Integer userid);
  List<UserDto> getAllusers();
  void deleteuser(Integer userid);
}
