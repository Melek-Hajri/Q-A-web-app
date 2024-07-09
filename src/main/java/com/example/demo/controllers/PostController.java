package com.example.demo.controllers;

import com.example.demo.entities.Post;
import com.example.demo.entities.RoleType;
import com.example.demo.entities.StatusType;
import com.example.demo.entities.exceptions.ImpossibleUpdateException;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.services.PostServImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostServImp postService;

    @PostMapping("/Add")
    public ResponseEntity<Post> addPost(@RequestParam Long userId,
                                        @RequestParam String title,
                                        @RequestParam String body,
                                        @RequestParam(required = false) List<String> links,
                                        @RequestParam(required = false) List<byte[]> images,
                                        @RequestParam int statusValue) {

        try {
        	StatusType status = StatusType.fromValue(statusValue);
            Post savedPost = postService.postAdd(userId, title, body, links, images, status);
            return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/SetTags/{postId}")
    public ResponseEntity<Void> setPostTags(@PathVariable Long postId, @RequestBody List<Long> tagIds) {
        try {
            postService.setPostTags(postId, tagIds);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Find/{postId}")
    public ResponseEntity<Post> findPost(@PathVariable Long postId) {
        try {
            Post post = postService.postFind(postId);
            return ResponseEntity.ok(post);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/FindByUser/{userId}")
    public ResponseEntity<List<Post>> findPostByUser(@PathVariable Long userId) {
        try {
            List<Post> posts = postService.postFindByUser(userId);
            return ResponseEntity.ok(posts);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/FindAll")
    public ResponseEntity<List<Post>> findAllPosts() {
        List<Post> posts = postService.postFindAll();
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/Delete/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        try {
            postService.postDelete(postId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/DeleteByUser/{userId}")
    public ResponseEntity<Void> deletePostsByUser(@PathVariable Long userId) {
        try {
            postService.postDeleteByUser(userId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/Update/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post updatedPost) {
        try {
            Post post = postService.postUpdate(postId, updatedPost);
            return ResponseEntity.ok(post);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ImpossibleUpdateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
