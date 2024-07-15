package com.example.demo.entities.exceptions;

public class PostSolvedException extends RuntimeException {
	public PostSolvedException() {
        super("This post is already solved. Operation prohibited");
    }

}
