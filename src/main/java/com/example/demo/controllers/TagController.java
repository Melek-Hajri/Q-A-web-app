package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Tag;
import com.example.demo.services.TagServImp;

@RestController
@RequestMapping("/tags")
public class TagController {
	@Autowired
	private TagServImp tagServ;
	
	@PostMapping(value = "/Add")
	public Tag tagAdd(@RequestBody Tag tag) {
		return this.tagServ.tagAdd(tag);
	}
	@GetMapping(value = "/Find/{tagId}")
	public Tag tagFind(@PathVariable Long tagId) {
		return this.tagServ.tagFind(tagId);
	}
	@GetMapping(value = "/FindAll")
	public List<Tag> tagFindAll() {
		return this.tagServ.tagFindAll();
	}
	@GetMapping(value = "/FindByPost/{postId}")
	public List<Tag> tagFindByPost(@PathVariable Long postId) {
		return this.tagServ.tagFindByPost(postId);
	}
	@DeleteMapping(value = "/Delete/{tagId}")
	public void tagDelete(@PathVariable Long tagId) {
		this.tagServ.tagDelete(tagId);
	}
	@PutMapping(value = "/Update/{tagId}")
	public Tag tagUpdate(@PathVariable Long tagId, @RequestBody Tag updatedTag) {
		return this.tagServ.tagUpdate(tagId, updatedTag);
	}
}
