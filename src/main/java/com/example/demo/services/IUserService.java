package com.example.demo.services;

import java.util.List;

import com.example.demo.DTOs.SignupDTO;
import com.example.demo.DTOs.userDTO;
import com.example.demo.entities.RoleType;
import com.example.demo.entities.User;

public interface IUserService {
	public userDTO createUser(SignupDTO signupDTO);
	public User userAdd(String username, String email, RoleType role);
	public User userFind(Long userId);
	public List<User> userFindAll();
	public void userDelete(Long userId);
	public User userUpdate(Long userId, User updatedUser);	
}
