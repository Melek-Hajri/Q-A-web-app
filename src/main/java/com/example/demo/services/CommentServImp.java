package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entities.Comment;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.repository.IAnswerRepository;
import com.example.demo.repository.ICommentRepository;
import com.example.demo.repository.IPostRepository;
import com.example.demo.repository.IUserRepository;

public class CommentServImp implements ICommentService {
	@Autowired
	private ICommentRepository commentRepo;
	@Autowired
	private IUserRepository userRepo;
	@Autowired
	private IPostRepository postRepo;
	@Autowired
	private IAnswerRepository answerRepo;
	
	@Override
	public Comment commentAdd(Comment comment) {
		return this.commentRepo.save(comment);
	}
	@Override
	public Comment commentFind(Long commentId) {
		return this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
	}
	@Override
	public List<Comment> commentFindByUser(Long userId) {
		this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return this.commentRepo.commentFindByUser(userId);
	}
	@Override
	public List<Comment> commentFindByPost(Long postId) {
		this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		return this.commentRepo.commentFindByPost(postId);
	}
	@Override
	public List<Comment> commentFindByAnswer(Long answerId) {
		this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
		return this.commentRepo.commentFindByAnswer(answerId);
	}
	@Override
	public void commentDelete(Long commentId ) {
		this.commentRepo.deleteById(commentId);
	}
	@Override
	public void commentDeleteByPost(Long postId ) {
		this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		this.commentRepo.commentDeleteByPost(postId);
	}
	@Override
	public void commentDeleteByAnswer(Long answerId ) {
		this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
		this.commentRepo.commentDeleteByAnswer(answerId);
	}
	@Override
	public void commentDeleteByUser(Long userId ) {
		this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		this.commentRepo.commentDeleteByUser(userId);
	}
	@Override
	public Comment commentUpdate(Long commentId, Comment updatedComment) {
		Comment comment= this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
		comment.setUser(updatedComment.getUser());
		comment.setPost(updatedComment.getPost());
		comment.setAnswer(updatedComment.getAnswer());
		comment.setBody(updatedComment.getBody());
		comment.setVotes(null);
		comment.setVotes(updatedComment.getVotes());
		comment.setVoteCount(updatedComment.getVoteCount());
		comment.setCreationDate(updatedComment.getCreationDate());
		return this.commentRepo.save(comment);
	}
}
