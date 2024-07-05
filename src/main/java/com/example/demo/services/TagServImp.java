package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Tag;
import com.example.demo.repository.ITagRepository;

@Service
public class TagServImp implements ITagService{
	@Autowired
	private ITagRepository tagRepo;
	
	
	@Override
	public Tag tagAdd(Tag tag) {
		return this.tagRepo.save(tag);
	}
	@Override
	public Tag tagFind(Long tag_id) {
		return this.tagRepo.findById(tag_id).get();
	}
	@Override
	public List<Tag> tagFindAll(){
		return this.tagRepo.findAll();
	}
	@Override
	public List<Tag> tagFindByPost(Long post_id){
		return this.tagRepo.tagFindByPost(post_id);
	}
	@Override
	public void tagDelete(Long tag_id) {
		this.tagRepo.deleteById(tag_id);
	}
	@Override
	public Tag tagUpdate(Long tag_id, Tag updated_tag) {
		Tag tag = this.tagRepo.findById(tag_id).get();
		tag.setName(updated_tag.getName());
		tag.setPosts(null);
		tag.setPosts(updated_tag.getPosts());
		return tag;
	}
}
