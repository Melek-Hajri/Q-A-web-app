package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entities.Answer;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Image;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.entities.Vote;
import com.example.demo.entities.VoteType;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.repository.IAnswerRepository;
import com.example.demo.repository.ICommentRepository;
import com.example.demo.repository.IPostRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.repository.IVoteRepository;

public class VoteServImp implements IVoteService {
	
	@Autowired
	private IVoteRepository voteRepo;
	
	@Autowired
    private IUserRepository userRepo;

    @Autowired
    private IPostRepository postRepo;

    @Autowired
    private IAnswerRepository answerRepo;

    @Autowired
    private ICommentRepository commentRepo;
    
    @Override
    public Vote voteCast(Long userId, Long postId, Long answerId, Long commentId, VoteType type) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        
        Vote vote = new Vote();
        vote.setUser(user);
        vote.setType(type);

        if (postId != null && answerId == null && commentId == null) {
            Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
            post.getVotes().add(vote);
            vote.setPost(post);
            post.updateVoteCountOnAdd(type);
        } else if (answerId != null && postId == null && commentId == null) {
            Answer answer = this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
            answer.getVotes().add(vote);
            vote.setAnswer(answer);
            answer.updateVoteCountOnAdd(type);
        } else if (commentId != null && postId == null && answerId == null) {
            Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
            comment.getVotes().add(vote);
            vote.setComment(comment);
            comment.updateVoteCountOnAdd(type);
        } else {
            throw new IllegalArgumentException("Either postId, answerId, or commentId must be provided");
        }

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
        this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return this.voteRepo.voteFindByUser(userId);
    }
    
    @Override
    public List<Vote> voteFindByPost(Long postId) {
        this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return this.voteRepo.voteFindByPost(postId);
    }
    
    @Override
    public List<Vote> voteFindByAnswer(Long answerId) {
        this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
        return this.voteRepo.voteFindByAnswer(answerId);
    }
    
    @Override
    public List<Vote> voteFindByComment(Long commentId) {
        this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        return this.voteRepo.voteFindByComment(commentId);
    }
    
    @Override 
    public void voteCancel(Long voteId) {
        Vote vote = this.voteRepo.findById(voteId).orElseThrow(() -> new ResourceNotFoundException("Vote not found"));
        
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
    public void voteDeleteByUser(Long userId) {
        this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        for(Vote vote : this.voteRepo.voteFindByUser(userId)) {
			this.voteCancel(vote.getId());
		}
    }
    
    @Override
    public void voteDeleteByPost(Long postId) {
        this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        for(Vote vote : this.voteRepo.voteFindByPost(postId)) {
			this.voteCancel(vote.getId());
		}

    }
    
    @Override
    public void voteDeleteByAnswer(Long answerId) {
        this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
        for(Vote vote : this.voteRepo.voteFindByAnswer(answerId)) {
			this.voteCancel(vote.getId());
		}
    }
    
    @Override
    public void voteDeleteByComment(Long commentId) {
        this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        for(Vote vote : this.voteRepo.voteFindByComment(commentId)) {
			this.voteCancel(vote.getId());
		}
    }
    
}
