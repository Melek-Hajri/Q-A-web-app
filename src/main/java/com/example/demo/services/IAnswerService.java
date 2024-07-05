package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Answer;

public interface IAnswerService {
	public Answer answerAdd(Answer answer);
	public Answer answerFind(Long answer_id);
	public List<Answer> answerFindByPost(Long post_id);
	public List<Answer> answerFindByUser(Long user_id);
	public void answerDelete(Long answer_id);
	public void answerDeleteByPost(Long post_id);
	public void answerDeleteByUser(Long user_id);
	public Answer answerUpdate(Long id, Answer updated_answer);
}
