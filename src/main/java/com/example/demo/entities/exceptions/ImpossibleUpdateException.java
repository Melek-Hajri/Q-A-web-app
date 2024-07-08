package com.example.demo.entities.exceptions;

public class ImpossibleUpdateException extends RuntimeException {
	public ImpossibleUpdateException(String message) {
        super(message);
    }
}
