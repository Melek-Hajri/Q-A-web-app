package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTOs.SignupDTO;
import com.example.demo.DTOs.userDTO;
import com.example.demo.repository.IUserRepository;
import com.example.demo.services.UserServImp;

@RestController
@RequestMapping("/signup")
public class SignUpController {
	
	@Autowired
	private UserServImp userService;
	
	@Autowired
	private IUserRepository userRepo;
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody SignupDTO signupDTO) {
		
		if(this.userRepo.findByEmail(signupDTO.getEmail()).isPresent()) {
			String msg = "A user with this email: " + signupDTO.getEmail() + " already exists";
			return new ResponseEntity<>(msg, HttpStatus.NOT_ACCEPTABLE);
		}
		if(this.userRepo.findByUsername(signupDTO.getUsername()).isPresent()) {
			String msg = "A user with this username: " + signupDTO.getUsername() + " already exists";
			return new ResponseEntity<>(msg, HttpStatus.NOT_ACCEPTABLE);
		}
			
		userDTO createdUser = this.userService.createUser(signupDTO);
		if(createdUser == null)
			return new ResponseEntity<>("User not created", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
}
