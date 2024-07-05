package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Comment;

public interface ICommentService {
	public Comment commentAdd(Comment comment);
	public Comment commentFind(Long comment_id);
	public List<Comment> commentFindAll();
	public List<Comment> commentFindByUser(Long user_id);
	public List<Comment> commentFindByPost(Long post_id);
	public List<Comment> commentFindByAnswer(Long answer_id);
	public void commentDelete(Long comment_id);
	public void commentDeleteByUser(Long user_id);
	public void commentDeleteByPost(Long post_id);
	public void commentDeleteByAnswer(Long answer_id);
	public Comment commentUpdate(Long comment_id, Comment updated_comment);
}
