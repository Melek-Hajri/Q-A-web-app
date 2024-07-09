package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Tag;

public interface ITagService {
	public Tag tagAdd(String name);
	public Tag tagFind(Long tagId);
	public List<Tag> tagFindAll();
	public List<Tag> tagFindByPost(Long postId);
	public void tagDelete(Long tagId);
	public Tag tagUpdate(Long tagId, Tag updatedTag);
}
