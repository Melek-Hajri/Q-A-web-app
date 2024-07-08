package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Tag;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.repository.IPostRepository;
import com.example.demo.repository.ITagRepository;

@Service
public class TagServImp implements ITagService{
	@Autowired
	private ITagRepository tagRepo;
	@Autowired
	private IPostRepository postRepo;
	
	@Override
	public Tag tagAdd(Tag tag) {
		return this.tagRepo.save(tag);
	}
	@Override
	public Tag tagFind(Long tagId) {
		return this.tagRepo.findById(tagId).get();
	}
	@Override
	public List<Tag> tagFindAll(){
		return this.tagRepo.findAll();
	}
	@Override
	public List<Tag> tagFindByPost(Long postId){
		this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		return this.tagRepo.tagFindByPost(postId);
	}
	@Override
	public void tagDelete(Long tagId) {
		this.tagRepo.deleteById(tagId);
	}
	@Override
	public Tag tagUpdate(Long tagId, Tag updatedTag) {
		Tag tag = this.tagRepo.findById(tagId).get();
		tag.setName(updatedTag.getName());
		tag.setPosts(null);
		tag.setPosts(updatedTag.getPosts());
		this.tagRepo.save(tag);
		return tag;
	}
}
