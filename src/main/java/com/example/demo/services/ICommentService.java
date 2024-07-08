package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Comment;

public interface ICommentService {
	public Comment commentAdd(Comment comment);
	public Comment commentFind(Long commentId);
	public List<Comment> commentFindByUser(Long userId);
	public List<Comment> commentFindByPost(Long postId);
	public List<Comment> commentFindByAnswer(Long answerId);
	public void commentDelete(Long commentId);
	public void commentDeleteByUser(Long userId);
	public void commentDeleteByPost(Long postId);
	public void commentDeleteByAnswer(Long answerId);
	public Comment commentUpdate(Long commentId, Comment updatedComment);
}
