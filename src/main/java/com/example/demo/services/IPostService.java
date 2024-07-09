package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Post;
import com.example.demo.entities.StatusType;

public interface IPostService {
	public Post postAdd(Long userId, String title, String body, List<String> links, List<byte[]> images, StatusType status);
	public void setPostTags(Long postId, List<Long> tagIds);
	public Post postFind(Long postId);
	public List<Post> postFindAll();
	public List<Post> postFindByUser(Long userId);
	public void postDelete(Long postId);
	public void postDeleteByUser(Long userId);
	public Post postUpdate(Long postId, Post updatedPost);
}
