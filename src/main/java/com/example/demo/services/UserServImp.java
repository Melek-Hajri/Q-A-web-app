package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.RoleType;
import com.example.demo.entities.User;
import com.example.demo.entities.exceptions.ResourceAlreadyExistsException;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.repository.IUserRepository;
@Service
public class UserServImp implements IUserService{
	
	@Autowired
    private IUserRepository userRepo;
	
	@Override
	@Transactional
	public User userAdd(String username, String email, RoleType role) {
		// Check if a user with the same email exists
        Optional<User> existingUserByEmail = this.userRepo.findByEmail(email);
        if (existingUserByEmail.isPresent()) {
            throw new ResourceAlreadyExistsException("User with this email already exists");
        }

        // Check if a user with the same username exists
        Optional<User> existingUserByUsername = this.userRepo.findByUsername(username);
        if (existingUserByUsername.isPresent()) {
            throw new ResourceAlreadyExistsException("User with this username already exists");
        }
        
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setRole(role);
		return this.userRepo.save(user);
	}
	
	@Override
	public User userFind(Long userId) {
		return this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}
	
	@Override
	public List<User> userFindAll() {
		return this.userRepo.findAll();
	}
	
	@Override
	@Transactional
	public void userDelete(Long userId) {
		this.userFind(userId);
		this.userRepo.deleteById(userId);
	}
	
	@Override
	@Transactional
	public User userUpdate(Long userId, User updatedUser) {
		User user = this.userFind(userId);
		user.setUsername(updatedUser.getUsername());
		user.setEmail(updatedUser.getEmail());
		user.setRole(updatedUser.getRole());
		return this.userRepo.save(user);
	}
}
