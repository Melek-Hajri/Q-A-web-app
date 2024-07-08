package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entities.Answer;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Image;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.entities.exceptions.ImpossibleUpdateException;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.repository.IAnswerRepository;
import com.example.demo.repository.ICommentRepository;
import com.example.demo.repository.IImageRepository;
import com.example.demo.repository.IPostRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.repository.IVoteRepository;

public class CommentServImp implements ICommentService {

	@Autowired
	private IAnswerRepository answerRepo;
	
	@Autowired
	private IPostRepository postRepo;
	
	@Autowired
	private IUserRepository userRepo;
	
	@Autowired
	private IImageRepository imageRepo;
	
	@Autowired
	private ICommentRepository commentRepo;
	
	@Autowired
	private IVoteRepository voteRepo;
	
	@Override
	public Comment commentAdd(Long userId, Long postId, Long answerId, String body, List<String> links, List<byte[]> images) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setBody(body);
        comment.setLinks(links);
        comment = this.commentRepo.save(comment);
        
        if (postId != null && answerId == null) {
            Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
            post.getComments().add(comment);
            comment.setPost(post);
        } else if (answerId != null && postId == null) {
            Answer answer = this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
            answer.getComments().add(comment);
            comment.setAnswer(answer);
        } else {
            throw new IllegalArgumentException("Either postId or answerId must be provided");
        }
        comment.setImages(new ArrayList<>());
        
        comment = this.commentRepo.save(comment);
        
        for (byte[] imageData : images) {
            Image image = new Image();
            image.setData(imageData);
            image.setComment(comment);
            imageRepo.save(image);
            
            comment.getImages().add(image);
        }
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
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
		if(comment.getUser() != null) {
			User user = comment.getUser();
			user.getComments().remove(comment);
		}
		if(comment.getPost() != null) {
			Post post = comment.getPost();
			post.getComments().remove(comment);
		}
		if(comment.getAnswer() != null) {
			Answer answer = comment.getAnswer();
			answer.getComments().remove(comment);
		}
		this.imageRepo.imageDeleteByComment(commentId);
		this.voteRepo.voteDeleteByComment(commentId);
		this.commentRepo.deleteById(commentId);
	}
	
	@Override
	public void commentDeleteByPost(Long postId ) {
		this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		for(Comment comment : this.commentRepo.commentFindByPost(postId)) {
			this.commentDelete(comment.getId());
		}
	}
	
	@Override
	public void commentDeleteByAnswer(Long answerId ) {
		this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
		for(Comment comment : this.commentRepo.commentFindByAnswer(answerId)) {
			this.commentDelete(comment.getId());
		}
	}
	
	@Override
	public void commentDeleteByUser(Long userId ) {
		this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		for(Comment comment : this.commentRepo.commentFindByUser(userId)) {
			this.commentDelete(comment.getId());
		}
	}
	
	@Override
	public Comment commentUpdate(Long commentId, Comment updatedComment) {
		Comment comment= this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
		if(!comment.getVotes().isEmpty())
			throw new ImpossibleUpdateException("Impossible to modify this comment beacuse it was already voted");
		comment.setBody(updatedComment.getBody());
		comment.setLinks(updatedComment.getLinks());
		comment.setImages(updatedComment.getImages());
		return this.commentRepo.save(comment);
	}
}
