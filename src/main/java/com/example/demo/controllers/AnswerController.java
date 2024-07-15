package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Answer;
import com.example.demo.entities.exceptions.ImpossibleUpdateException;
import com.example.demo.entities.exceptions.PostSolvedException;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.services.AnswerServImp;

@RestController
@RequestMapping("/answers")
public class AnswerController {
	@Autowired
    private AnswerServImp answerService;

    @PostMapping("/Add")
    public ResponseEntity<Answer> answerAdd(@RequestParam(required = true) Long userId,
                                            @RequestParam(required = true) Long postId,
                                            @RequestParam String body,
                                            @RequestParam List<String> links,
                                            @RequestParam List<byte[]> images) {
        try {
            Answer addedAnswer = answerService.answerAdd(userId, postId, body, links, images);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedAnswer);
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (PostSolvedException e) {
        	System.out.println(e);
        	return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/Find/{answerId}")
    public ResponseEntity<Answer> answerFind(@PathVariable Long answerId) {
        try {
            Answer foundAnswer = answerService.answerFind(answerId);
            return ResponseEntity.ok(foundAnswer);
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/FindByPost/{postId}")
    public ResponseEntity<List<Answer>> answerFindByPost(@PathVariable Long postId) {
        try {
            List<Answer> answers = answerService.answerFindByPost(postId);
            return ResponseEntity.ok(answers);
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/FindByUser/{userId}")
    public ResponseEntity<List<Answer>> answerFindByUser(@PathVariable Long userId) {
        try {
            List<Answer> answers = answerService.answerFindByUser(userId);
            return ResponseEntity.ok(answers);
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/Delete/{answerId}")
    public ResponseEntity<Void> answerDelete(@PathVariable Long answerId) {
        try {
            answerService.answerDelete(answerId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (PostSolvedException e) {
        	System.out.println(e);
        	return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/DeleteByPost/{postId}")
    public ResponseEntity<Void> answerDeleteByPost(@PathVariable Long postId) {
        try {
            answerService.answerDeleteByPost(postId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/DeleteByUser/{userId}")
    public ResponseEntity<Void> answerDeleteByUser(@PathVariable Long userId) {
        try {
            answerService.answerDeleteByUser(userId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/Update/{answerId}")
    public ResponseEntity<Answer> answerUpdate(@PathVariable Long answerId,
                                               @RequestBody Answer updatedAnswer) {
        try {
            Answer updated = answerService.answerUpdate(answerId, updatedAnswer);
            return ResponseEntity.ok(updated);
        } catch (ImpossibleUpdateException e) {
        	System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (PostSolvedException e) {
        	System.out.println(e);
        	return ResponseEntity.badRequest().build();
        }
    }

    @ExceptionHandler(ImpossibleUpdateException.class)
    public ResponseEntity<Void> handleImpossibleUpdateException(ImpossibleUpdateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Void> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
