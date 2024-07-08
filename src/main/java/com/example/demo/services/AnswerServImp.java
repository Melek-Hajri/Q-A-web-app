package com.example.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Answer;
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

@Service
public class AnswerServImp implements IAnswerService{
	
	@Autowired
	private IAnswerRepository answerRepo;
	
	@Autowired
	private IPostRepository postRepo;
	
	@Autowired
	private IUserRepository userRepo;
	
	@Autowired
	private IImageRepository imageRepo;
	
	@Autowired
	private CommentServImp commentService;
	
	@Autowired
	private IVoteRepository voteRepo;
	
	@Override
	public Answer answerAdd(Long userId, Long postId, String body, List<String> links, List<byte[]> images) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Answer answer = new Answer();
        answer.setUser(user);
        answer.setPost(post);
        answer.setBody(body);
        answer.setLinks(links);
        answer.setVoteCount(0);
        answer.setCreationDate(new Date());
        
        answer.setImages(new ArrayList<>());
        
        answer = this.answerRepo.save(answer);
        
        for (byte[] imageData : images) {
            Image image = new Image();
            image.setData(imageData);
            image.setAnswer(answer);
            imageRepo.save(image);
            
            answer.getImages().add(image);
        }
		return this.answerRepo.save(answer);
	}
	
	@Override
	public Answer answerFind(Long answerId) {
		return this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
	}
	
	@Override
	public List<Answer> answerFindByPost(Long postId) {
		this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		return this.answerRepo.answerFindByPost(postId);
	}
	
	@Override
	public List<Answer> answerFindByUser(Long userId) {
		this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return this.answerRepo.answerFindByUser(userId);
	}
	
	@Override
	public void answerDelete(Long answerId) {
		Answer answer = this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
		if(answer.getPost() != null) {
			Post post = answer.getPost();
			post.getAnswers().remove(answer);
		}
		if(answer.getUser() != null) {
			User user = answer.getUser();
			user.getAnswers().remove(answer);
		}
		this.imageRepo.imageDeleteByAnswer(answerId);
		this.commentService.commentDeleteByAnswer(answerId);
		this.voteRepo.voteDeleteByAnswer(answerId);
		this.answerRepo.delete(answer);
	}
	
	@Override
	public void answerDeleteByPost(Long postId) {
		this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		for(Answer answer : this.answerRepo.answerFindByPost(postId)) {
			this.answerDelete(answer.getId());
		}
	}
	
	@Override
	public void answerDeleteByUser(Long userId) {
		this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		for(Answer answer : this.answerRepo.answerFindByUser(userId)) {
			this.answerDelete(answer.getId());
		}
	}
	
	@Override
	public Answer answerUpdate(Long answerId, Answer updatedAnswer) {
		Answer answer = this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
		if(!answer.getComments().isEmpty() || !answer.getVotes().isEmpty())
			throw new ImpossibleUpdateException("Impossible to modify this answer beacuse it was already voted or commented");
		answer.setBody(updatedAnswer.getBody());
		answer.setLinks(updatedAnswer.getLinks());
		answer.setImages(updatedAnswer.getImages());
		return this.answerRepo.save(answer);
	}
}
