package com.example.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Answer;
import com.example.demo.entities.Image;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.entities.exceptions.ImpossibleUpdateException;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.repository.IAnswerRepository;
import com.example.demo.repository.IImageRepository;

@Service
public class AnswerServImp implements IAnswerService{
	
	@Autowired
	private IAnswerRepository answerRepo;
	
	@Autowired
	private PostServImp postService;
	
	@Autowired
	private UserServImp userService;
	
	@Autowired
	private IImageRepository imageRepo;
	
	@Override
	@Transactional
	public Answer answerAdd(Long userId, Long postId, String body, List<String> links, List<byte[]> images) {
		User user = this.userService.userFind(userId);
        Post post = this.postService.postFind(postId);

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
		this.postService.postFind(postId);
		return this.answerRepo.answerFindByPost(postId);
	}
	
	@Override
	public List<Answer> answerFindByUser(Long userId) {
		this.userService.userFind(userId);
		return this.answerRepo.answerFindByUser(userId);
	}
	
	@Override
	@Transactional
	public void answerDelete(Long answerId) {
		Answer answer = this.answerFind(answerId);
		if(answer.getPost() != null) {
			Post post = answer.getPost();
			post.getAnswers().remove(answer);
		}
		if(answer.getUser() != null) {
			User user = answer.getUser();
			user.getAnswers().remove(answer);
		}
		this.answerRepo.deleteById(answerId);
	}
	
	@Override
	@Transactional
	public void answerDeleteByPost(Long postId) {
		this.postService.postFind(postId);
		for(Answer answer : this.answerRepo.answerFindByPost(postId)) {
			this.answerDelete(answer.getId());
		}
	}
	
	@Override
	@Transactional
	public void answerDeleteByUser(Long userId) {
		this.userService.userFind(userId);
		for(Answer answer : this.answerRepo.answerFindByUser(userId)) {
			this.answerDelete(answer.getId());
		}
	}
	
	@Override
	@Transactional
	public Answer answerUpdate(Long answerId, Answer updatedAnswer) {
		Answer answer = this.answerFind(answerId);
		if(!answer.getComments().isEmpty() || !answer.getVotes().isEmpty())
			throw new ImpossibleUpdateException("Impossible to modify this answer beacuse it was already voted or commented");
		answer.setBody(updatedAnswer.getBody());
		answer.setLinks(updatedAnswer.getLinks());
		answer.setImages(updatedAnswer.getImages());
		return this.answerRepo.save(answer);
	}
}
