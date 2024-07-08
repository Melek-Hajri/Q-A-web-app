package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.User;

public interface IUserService {
	public User userAdd(User user);
	public User userFind(Long userId);
	public List<User> userFindAll();
	public void userDelete(Long userId);
	public User userUpdate(Long userId, User updatedUser);	
}
