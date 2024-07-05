package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.User;

public interface IUserService {
	public User userAdd(User user);
	public User userFind(Long user_id);
	public List<User> userFindAll();
	public void userDelete(Long user_id);
	public User userUpdate(Long user_id, User updated_user);	
}
