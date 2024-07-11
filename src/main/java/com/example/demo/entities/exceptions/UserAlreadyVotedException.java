package com.example.demo.entities.exceptions;

public class UserAlreadyVotedException extends RuntimeException {
	public UserAlreadyVotedException(String message) {
        super(message);
    }
}
