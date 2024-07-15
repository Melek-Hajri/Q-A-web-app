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
import com.example.demo.services.UserServImp;

@RestController
@RequestMapping("/signup")
public class SignUpController {
	
	@Autowired
	private UserServImp userService;
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody SignupDTO signupDTO) {
		
		userDTO createdUser = this.userService.createUser(signupDTO);
		if(createdUser == null)
			return new ResponseEntity<>("User not created", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
}
