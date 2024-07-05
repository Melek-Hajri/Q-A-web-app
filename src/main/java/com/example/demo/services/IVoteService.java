package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Vote;

public interface IVoteService {
	public Vote voteAdd(Vote vote);
	public Vote voteFind(Long comment_id);
	public List<Vote> voteFindAll();
	public List<Vote> voteFindByUser(Long user_id);
	public List<Vote> voteFindByPost(Long post_id);
	public List<Vote> voteFindByAnswer(Long answer_id);
	public List<Vote> voteFindByComment(Long comment_id);
	public void voteDelete(Long vote_id);
	public void voteDeleteByUser(Long user_id);
	public void voteDeleteByPost(Long post_id);
	public void voteDeleteByAnswer(Long answer_id);
	public void voteDeleteByComment(Long comment_id);
}
