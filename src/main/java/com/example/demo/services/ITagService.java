package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Tag;

public interface ITagService {
	public Tag tagAdd(Tag tag);
	public Tag tagFind(Long tag_id);
	public List<Tag> tagFindAll();
	public List<Tag> tagFindByPost(Long post_id);
	public void tagDelete(Long tag_id);
	public Tag tagUpdate(Long tag_id, Tag updated_tag);
}
