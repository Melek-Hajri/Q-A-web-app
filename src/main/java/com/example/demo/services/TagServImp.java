package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Post;
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
	@Transactional
    public Tag tagAdd(String name) {
        Optional<Tag> existingTag = this.tagRepo.findByName(name);
        if (existingTag.isPresent()) {
            throw new IllegalArgumentException("Tag with the same name already exists");
        }
        Tag tag = new Tag();
        tag.setName(name);
        return this.tagRepo.save(tag);
    }
	
	@Override
	public Tag tagFind(Long tagId) {
		return this.tagRepo.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
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
	@Transactional
	public void tagDelete(Long tagId) {
		Tag tag = this.tagRepo.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
		if(tag.getPosts() != null) {
			for(Post post : tag.getPosts()) {
				post.getTags().remove(tag);
			}
		}
		this.tagRepo.deleteById(tagId);
	}
	
	@Override
	@Transactional
	public Tag tagUpdate(Long tagId, Tag updatedTag) {
		Tag tag = this.tagRepo.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
		tag.setName(updatedTag.getName());
		this.tagRepo.save(tag);
		return tag;
	}
}
