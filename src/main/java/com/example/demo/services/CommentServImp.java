package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Answer;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Image;
import com.example.demo.entities.Post;
import com.example.demo.entities.StatusType;
import com.example.demo.entities.User;
import com.example.demo.entities.exceptions.ImpossibleUpdateException;
import com.example.demo.entities.exceptions.PostSolvedException;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.repository.ICommentRepository;
import com.example.demo.repository.IImageRepository;

@Service
public class CommentServImp implements ICommentService {

	@Autowired
	private AnswerServImp answerService;
	
	@Autowired
	private PostServImp postService;
	
	@Autowired
	private UserServImp userService;
	
	@Autowired
	private IImageRepository imageRepo;
	
	@Autowired
	private ICommentRepository commentRepo;
	
	@Override
	public Comment commentAdd(Long userId, Long postId, Long answerId, String body, List<String> links, List<byte[]> images) {
		User user = this.userService.userFind(userId);
        
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setBody(body);
        comment.setLinks(links);
        comment = this.commentRepo.save(comment);
        
        if (postId != null && answerId == null) {
            Post post = this.postService.postFind(postId);
            if(post.getStatus() == StatusType.Solved)
        		throw new PostSolvedException();
            post.getComments().add(comment);
            comment.setPost(post);
        } else if (answerId != null && postId == null) {
            Answer answer = this.answerService.answerFind(answerId);
            if(answer.getPost().getStatus() == StatusType.Solved) {
    			throw new PostSolvedException();
    		}
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
		this.userService.userFind(userId);
		return this.commentRepo.commentFindByUser(userId);
	}
	
	@Override
	public List<Comment> commentFindByPost(Long postId) {
		this.postService.postFind(postId);
		return this.commentRepo.commentFindByPost(postId);
	}
	
	@Override
	public List<Comment> commentFindByAnswer(Long answerId) {
		this.answerService.answerFind(answerId);
		return this.commentRepo.commentFindByAnswer(answerId);
	}
	
	@Override
	public void commentDelete(Long commentId ) {
		Comment comment = this.commentFind(commentId);
		if(comment.getUser() != null) {
			User user = comment.getUser();
			user.getComments().remove(comment);
		}
		if(comment.getPost() != null) {
			Post post = comment.getPost();
			if(post.getStatus() == StatusType.Solved) {
				throw new PostSolvedException();
			}
			post.getComments().remove(comment);
		}
		if(comment.getAnswer() != null) {
			Answer answer = comment.getAnswer();
			if(answer.getPost().getStatus() == StatusType.Solved) {
				throw new PostSolvedException();
			}
			answer.getComments().remove(comment);
		}
		this.commentRepo.deleteById(commentId);
	}
	
	@Override
	public void commentDeleteByPost(Long postId ) {
		for(Comment comment : this.commentFindByPost(postId)) {
			this.commentDelete(comment.getId());
		}
	}
	
	@Override
	public void commentDeleteByAnswer(Long answerId ) {
		for(Comment comment : this.commentFindByAnswer(answerId)) {
			this.commentDelete(comment.getId());
		}
	}
	
	@Override
	public void commentDeleteByUser(Long userId ) {
		for(Comment comment : this.commentFindByUser(userId)) {
			this.commentDelete(comment.getId());
		}
	}
	
	@Override
	public Comment commentUpdate(Long commentId, Comment updatedComment) {
		Comment comment= this.commentFind(commentId);
		if(comment.getPost().getStatus() == StatusType.Solved) {
			throw new PostSolvedException();
		} else if(!comment.getVotes().isEmpty())
			throw new ImpossibleUpdateException("Impossible to modify this comment beacuse it was already voted");
		comment.setBody(updatedComment.getBody());
		comment.setLinks(updatedComment.getLinks());
		if(updatedComment.getImages() != null) {
			if(comment.getImages() == null) {
				comment.setImages(new ArrayList<>());
			}
			comment.getImages().clear();
			comment.setImages(updatedComment.getImages());
		}
		return this.commentRepo.save(comment);
	}
}
