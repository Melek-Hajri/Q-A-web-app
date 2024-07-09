package com.example.demo.controllers;

import com.example.demo.entities.Comment;
import com.example.demo.entities.exceptions.ImpossibleUpdateException;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.services.CommentServImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentServImp commentService;

    @PostMapping("/Add")
    public ResponseEntity<Comment> addComment(@RequestParam Long userId,
                                              @RequestParam(required = false) Long postId,
                                              @RequestParam(required = false) Long answerId,
                                              @RequestParam String body,
                                              @RequestParam(required = false) List<String> links,
                                              @RequestParam(required = false) List<byte[]> images) {

        try {
            Comment savedComment = commentService.commentAdd(userId, postId, answerId, body, links, images);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // Handle bad request
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle other exceptions
        }
    }

    @GetMapping("/Find/{commentId}")
    public ResponseEntity<Comment> findComment(@PathVariable Long commentId) {
        try {
            Comment comment = commentService.commentFind(commentId);
            return ResponseEntity.ok(comment);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/FindByUser/{userId}")
    public ResponseEntity<List<Comment>> findByUser(@PathVariable Long userId) {
        try {
            List<Comment> comments = commentService.commentFindByUser(userId);
            return ResponseEntity.ok(comments);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/FindByPost/{postId}")
    public ResponseEntity<List<Comment>> findByPost(@PathVariable Long postId) {
        try {
            List<Comment> comments = commentService.commentFindByPost(postId);
            return ResponseEntity.ok(comments);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/FindByAnswer/{answerId}")
    public ResponseEntity<List<Comment>> findByAnswer(@PathVariable Long answerId) {
        try {
            List<Comment> comments = commentService.commentFindByAnswer(answerId);
            return ResponseEntity.ok(comments);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/Delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.commentDelete(commentId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/DeleteByPost/{postId}")
    public ResponseEntity<Void> deleteCommentsByPost(@PathVariable Long postId) {
        try {
            commentService.commentDeleteByPost(postId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/DeleteByAnswer/{answerId}")
    public ResponseEntity<Void> deleteCommentsByAnswer(@PathVariable Long answerId) {
        try {
            commentService.commentDeleteByAnswer(answerId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/Update/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody Comment updatedComment) {
        try {
            Comment comment = commentService.commentUpdate(commentId, updatedComment);
            return ResponseEntity.ok(comment);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ImpossibleUpdateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

