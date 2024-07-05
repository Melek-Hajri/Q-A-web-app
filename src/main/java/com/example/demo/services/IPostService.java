package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Post;

public interface IPostService {
	public Post postAdd(Post post);
	public Post postFind(Long post_id);
	public List<Post> answerFindAll();
	public List<Post> postFindByUser(Long user_id);
	public void postDelete(Long post_id);
	public void postDeleteByUser(Long user_id);
	public Post postUpdate(Long post_id, Post updated_post);
}
