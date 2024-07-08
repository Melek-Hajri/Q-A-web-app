package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Post;

public interface IPostService {
	public Post postAdd(Post post);
	public Post postFind(Long postId);
	public List<Post> answerFindAll();
	public List<Post> postFindByUser(Long userId);
	public void postDelete(Long postId);
	public void postDeleteByUser(Long userId);
	public Post postUpdate(Long postId, Post updatedPost);
}
