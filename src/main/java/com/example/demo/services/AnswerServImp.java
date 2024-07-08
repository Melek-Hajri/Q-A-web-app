package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Answer;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.repository.IAnswerRepository;
import com.example.demo.repository.IPostRepository;
import com.example.demo.repository.IUserRepository;

@Service
public class AnswerServImp implements IAnswerService{
	@Autowired
	private IAnswerRepository answerRepo;
	@Autowired
	private IPostRepository postRepo;
	@Autowired
	private IUserRepository userRepo;
	
	@Override
	public Answer answerAdd(Answer answer) {
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
		this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
		this.answerRepo.deleteById(answerId);
	}
	@Override
	public void answerDeleteByPost(Long postId) {
		this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		this.answerRepo.answerDeleteByPost(postId);
	}
	@Override
	public void answerDeleteByUser(Long userId) {
		this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		this.answerRepo.answerDeleteByUser(userId);
	}
	@Override
	public Answer answerUpdate(Long answerId, Answer updatedAnswer) {
		Answer answer = this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
		answer.setUser(updatedAnswer.getUser());
		answer.setPost(updatedAnswer.getPost());
		answer.setBody(updatedAnswer.getBody());
		answer.setVotes(null);
		answer.setVotes(updatedAnswer.getVotes());
		answer.setVoteCount(updatedAnswer.getVoteCount());
		answer.setComments(null);
		answer.setComments(updatedAnswer.getComments());
		answer.setCreationDate(updatedAnswer.getCreationDate());
		return this.answerRepo.save(answer);
	}
}
