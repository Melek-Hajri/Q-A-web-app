package com.example.demo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Answer;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Post;
import com.example.demo.entities.StatusType;
import com.example.demo.entities.User;
import com.example.demo.entities.Vote;
import com.example.demo.entities.VoteType;
import com.example.demo.entities.exceptions.PostSolvedException;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.entities.exceptions.UserAlreadyVotedException;
import com.example.demo.repository.IVoteRepository;

@Service
public class VoteServImp implements IVoteService {
	
	@Autowired
	private IVoteRepository voteRepo;
	
	@Autowired
    private UserServImp userService;

    @Autowired
    private PostServImp postService;

    @Autowired
    private AnswerServImp answerService;

    @Autowired
    private CommentServImp commentService;
    
    @Override
    @Transactional
    public Vote voteCast(Long userId, Long postId, Long answerId, Long commentId, VoteType type) {
        User user = this.userService.userFind(userId);
        
        Vote vote = new Vote();
        vote.setUser(user);
        vote.setType(type);

        if (postId != null && answerId == null && commentId == null) {
        	if(this.voteRepo.voteFindByUserPost(postId, userId).isPresent())
        		throw new UserAlreadyVotedException("This user has already voted this post");
            Post post = this.postService.postFind(postId);
            if(post.getStatus() == StatusType.Solved)
        		throw new PostSolvedException();
            post.getVotes().add(vote);
            vote.setPost(post);
            post.updateVoteCountOnAdd(type);
        } else if (answerId != null && postId == null && commentId == null) {
        	if(this.voteRepo.voteFindByUserAnswer(postId, userId).isPresent())
        		throw new UserAlreadyVotedException("This user has already voted this answer");
            Answer answer = this.answerService.answerFind(answerId);
            if(answer.getPost().getStatus() == StatusType.Solved) {
    			throw new PostSolvedException();
    		}
            answer.getVotes().add(vote);
            vote.setAnswer(answer);
            answer.updateVoteCountOnAdd(type);
        } else if (commentId != null && postId == null && answerId == null) {
        	if(this.voteRepo.voteFindByUserComment(postId, userId).isPresent())
        		throw new UserAlreadyVotedException("This user has already voted this comment");
            Comment comment = this.commentService.commentFind(commentId);
            if(comment.getPost().getStatus() == StatusType.Solved) {
    			throw new PostSolvedException();
    		}
            comment.getVotes().add(vote);
            vote.setComment(comment);
            comment.updateVoteCountOnAdd(type);
        } else {
            throw new IllegalArgumentException("Either postId, answerId, or commentId must be provided");
        }
        user.getVotes().add(vote);

        return voteRepo.save(vote);
    }
    
    @Override
    public Vote voteFind(Long voteId) {
    	return this.voteRepo.findById(voteId).orElseThrow(() -> new ResourceNotFoundException("Vote not found"));
    }
    
    @Override
    public List<Vote> voteFindAll() {
    	return this.voteRepo.findAll();
    }
    
    @Override
    public List<Vote> voteFindByUser(Long userId) {
    	this.userService.userFind(userId);
        return this.voteRepo.voteFindByUser(userId);
    }
    
    @Override
    public List<Vote> voteFindByPost(Long postId) {
    	this.postService.postFind(postId);
        return this.voteRepo.voteFindByPost(postId);
    }
    
    @Override
    public List<Vote> voteFindByAnswer(Long answerId) {
    	this.answerService.answerFind(answerId);
        return this.voteRepo.voteFindByAnswer(answerId);
    }
    
    @Override
    public List<Vote> voteFindByComment(Long commentId) {
    	this.commentService.commentFind(commentId);
        return this.voteRepo.voteFindByComment(commentId);
    }
    
    @Override 
    @Transactional
    public void voteCancel(Long voteId) {
        Vote vote = this.voteFind(voteId);
        if(vote.getPost().getStatus() == StatusType.Solved) {
			throw new PostSolvedException();
		}
        if(vote.getUser() != null) {
        	vote.getUser().getVotes().remove(vote);
        }
        if (vote.getPost() != null) {
            vote.getPost().getVotes().remove(vote);
            vote.getPost().updateVoteCountOnRemove(vote.getType());
        } else if (vote.getAnswer() != null) {
            vote.getAnswer().getVotes().remove(vote);
            vote.getAnswer().updateVoteCountOnRemove(vote.getType());
        } else if (vote.getComment() != null) {
            vote.getComment().getVotes().remove(vote);
            vote.getComment().updateVoteCountOnRemove(vote.getType());
        }
        
        voteRepo.deleteById(voteId);
    }
    
    @Override
    @Transactional
    public void voteDeleteByUser(Long userId) {
        User user = this.userService.userFind(userId);
        for(Vote vote : user.getVotes()) {
			this.voteCancel(vote.getId());
		}
    }
    
    @Override
    @Transactional
    public void voteDeleteByPost(Long postId) {
        Post post = this.postService.postFind(postId);
        for(Vote vote : post.getVotes()) {
			this.voteCancel(vote.getId());
		}

    }
    
    @Override
    @Transactional
    public void voteDeleteByAnswer(Long answerId) {
        Answer answer = this.answerService.answerFind(answerId);
        for(Vote vote : answer.getVotes()) {
			this.voteCancel(vote.getId());
		}
    }
    
    @Override
    @Transactional
    public void voteDeleteByComment(Long commentId) {
        Comment comment = this.commentService.commentFind(commentId);
        for(Vote vote : comment.getVotes()) {
			this.voteCancel(vote.getId());
		}
    }
    
    @Override
    @Transactional
    public Vote voteUpdate(Long voteId, Vote updatedVote) {
    	Vote vote = this.voteFind(voteId);
    	if(vote.getPost().getStatus() == StatusType.Solved) {
			throw new PostSolvedException();
		}
    	vote.getPost().updateVoteCountOnRemove(vote.getType());
    	vote.setType(updatedVote.getType());
    	vote.getPost().updateVoteCountOnAdd(vote.getType());
    	return this.voteRepo.save(vote);
    }
    
}
