package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Vote;
import com.example.demo.entities.VoteType;

public interface IVoteService {
	public Vote voteCast(Long userId, Long postId, Long answerId, Long commentId, VoteType type);
	public Vote voteFind(Long voteId);
	public List<Vote> voteFindAll();
	public List<Vote> voteFindByUser(Long userId);
	public List<Vote> voteFindByPost(Long postId);
	public List<Vote> voteFindByAnswer(Long answerId);
	public List<Vote> voteFindByComment(Long commentId);
	public void voteCancel(Long voteId);
	public void voteDeleteByUser(Long userId);
	public void voteDeleteByPost(Long postId);
	public void voteDeleteByAnswer(Long answerId);
	public void voteDeleteByComment(Long commentId);
	public Vote voteUpdate(Long voteId, Vote updatedVote);
}
