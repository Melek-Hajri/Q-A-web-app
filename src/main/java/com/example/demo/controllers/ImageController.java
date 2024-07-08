package com.example.demo.controllers;

import com.example.demo.entities.Answer;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Image;
import com.example.demo.entities.Post;
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
                                             @RequestParam("post") Post post,
                                             @RequestParam("answer") Answer answer,
                                             @RequestParam("comment") Comment comment) throws IOException {
        Image image = new Image();
        image.setData(file.getBytes());

        // Set post, answer, or comment based on the provided IDs
        if (post != null) {
            image.setPost(post);
        }
        if (answer != null) {
            image.setAnswer(answer);
        }
        if (comment != null) {
            image.setComment(comment);
        }

        Image savedImage = imageService.imageAdd(image);
        return new ResponseEntity<>(savedImage, HttpStatus.CREATED);
    }

    @GetMapping("/Find/{imageId}")
    public ResponseEntity<byte[]> imageFind(@PathVariable Long imageId) {
        Image image = imageService.imageFind(imageId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/jpeg"); // or other content type
        return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
    }

    @GetMapping("/FindAll")
    public ResponseEntity<List<Image>> imageFindAll() {
        List<Image> images = imageService.imageFindAll();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
    @GetMapping("/FindByPost/{postId}")
    public ResponseEntity<List<Image>> imageFindByPost(@PathVariable Long postId) {
        List<Image> images = imageService.imageFindByPost(postId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
    @GetMapping("/FindByAnswer/{answerId}")
    public ResponseEntity<List<Image>> imageFindByAnswer(@PathVariable Long answerId) {
        List<Image> images = imageService.imageFindByAnswer(answerId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
    @GetMapping("/FindByComment/{commentId}")
    public ResponseEntity<List<Image>> imageFindByComment(@PathVariable Long commentId) {
        List<Image> images = imageService.imageFindByComment(commentId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
    @DeleteMapping("/Delete/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imageService.imageDelete(imageId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/DeleteByPost/{postId}")
    public ResponseEntity<Void> deleteImagesByPost(@PathVariable Long postId) {
        imageService.imageDeleteByPost(postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/DeleteByAnswer/{answerId}")
    public ResponseEntity<Void> deleteImagesByAnswer(@PathVariable Long answerId) {
        imageService.imageDeleteByAnswer(answerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/DeleteByComment/{commentId}")
    public ResponseEntity<Void> deleteImagesByComment(@PathVariable Long commentId) {
        imageService.imageDeleteByComment(commentId);
        return ResponseEntity.noContent().build();
    }
}

