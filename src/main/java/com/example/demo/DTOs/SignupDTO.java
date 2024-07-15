package com.example.demo.DTOs;

import lombok.Data;

@Data
public class SignupDTO {

	private Long id;
	
	private String username;
	
	private String password;
	
	private String email;
}
