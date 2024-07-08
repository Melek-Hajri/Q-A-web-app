package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.RoleType;
import com.example.demo.entities.User;

public interface IUserService {
	public User userAdd(String username, String email, RoleType role);
	public User userFind(Long userId);
	public List<User> userFindAll();
	public void userDelete(Long userId);
	public User userUpdate(Long userId, User updatedUser);	
}
