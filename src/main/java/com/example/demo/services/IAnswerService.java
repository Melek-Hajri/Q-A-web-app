package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Answer;

public interface IAnswerService {
	public Answer answerAdd(Long userId, Long postId, String body, List<String> links, List<byte[]> images);
	public Answer answerFind(Long answerId);
	public List<Answer> answerFindByPost(Long postId);
	public List<Answer> answerFindByUser(Long userId);
	public void answerDelete(Long answerId);
	public void answerDeleteByPost(Long postId);
	public void answerDeleteByUser(Long userId);
	public Answer answerUpdate(Long id, Answer updatedAnswer);
}
