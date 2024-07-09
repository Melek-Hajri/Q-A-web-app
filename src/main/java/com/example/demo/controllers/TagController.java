package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Tag;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.services.ITagService;
import com.example.demo.services.TagServImp;

@RestController
@RequestMapping("/tags")
public class TagController {

	@Autowired
    private TagServImp tagService;

    @PostMapping("/Add")
    public ResponseEntity<Tag> tagAdd(@RequestParam String name) {
        try {
            Tag newTag = tagService.tagAdd(name);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTag);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Conflict if tag with same name exists
        }
    }

    @GetMapping("/Find/{tagId}")
    public ResponseEntity<Tag> tagFind(@PathVariable Long tagId) {
        try {
            Tag tag = tagService.tagFind(tagId);
            return ResponseEntity.ok(tag);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/FindAll")
    public ResponseEntity<List<Tag>> tagFindAll() {
        List<Tag> tags = tagService.tagFindAll();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/FindByPost/{postId}")
    public ResponseEntity<List<Tag>> tagFindByPost(@PathVariable Long postId) {
        try {
            List<Tag> tags = tagService.tagFindByPost(postId);
            return ResponseEntity.ok(tags);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/Delete/{tagId}")
    public ResponseEntity<Void> tagDelete(@PathVariable Long tagId) {
        try {
            tagService.tagDelete(tagId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/Update/{tagId}")
    public ResponseEntity<Tag> tagUpdate(@PathVariable Long tagId, @RequestBody Tag updatedTag) {
        try {
            Tag updated = tagService.tagUpdate(tagId, updatedTag);
            return ResponseEntity.ok(updated);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
