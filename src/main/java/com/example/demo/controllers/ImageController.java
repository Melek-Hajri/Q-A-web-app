package com.example.demo.controllers;

import com.example.demo.entities.Image;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.services.ImageServImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageServImp imageService;

    @PostMapping("/Add")
    public ResponseEntity<Image> uploadImage(@RequestParam("file") MultipartFile file,
                                             @RequestParam(value = "postId", required = false) Long postId,
                                             @RequestParam(value = "answerId", required = false) Long answerId,
                                             @RequestParam(value = "commentId", required = false) Long commentId) throws IOException {

        try {
            Image savedImage = imageService.imageAdd(file.getBytes(), postId, answerId, commentId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/Find/{imageId}")
    public ResponseEntity<byte[]> imageFind(@PathVariable Long imageId) {
        try {
            Image image = imageService.imageFind(imageId);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "image/jpeg"); // or other content type
            return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/FindAll")
    public ResponseEntity<List<Image>> imageFindAll() {
        List<Image> images = imageService.imageFindAll();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/FindByPost/{postId}")
    public ResponseEntity<List<Image>> imageFindByPost(@PathVariable Long postId) {
        try {
            List<Image> images = imageService.imageFindByPost(postId);
            return ResponseEntity.ok(images);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/FindByAnswer/{answerId}")
    public ResponseEntity<List<Image>> imageFindByAnswer(@PathVariable Long answerId) {
        try {
            List<Image> images = imageService.imageFindByAnswer(answerId);
            return ResponseEntity.ok(images);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/FindByComment/{commentId}")
    public ResponseEntity<List<Image>> imageFindByComment(@PathVariable Long commentId) {
        try {
            List<Image> images = imageService.imageFindByComment(commentId);
            return ResponseEntity.ok(images);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/Delete/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        try {
            imageService.imageDelete(imageId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/DeleteByPost/{postId}")
    public ResponseEntity<Void> deleteImagesByPost(@PathVariable Long postId) {
        try {
            imageService.imageDeleteByPost(postId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/DeleteByAnswer/{answerId}")
    public ResponseEntity<Void> deleteImagesByAnswer(@PathVariable Long answerId) {
        try {
            imageService.imageDeleteByAnswer(answerId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/DeleteByComment/{commentId}")
    public ResponseEntity<Void> deleteImagesByComment(@PathVariable Long commentId) {
        try {
            imageService.imageDeleteByComment(commentId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}