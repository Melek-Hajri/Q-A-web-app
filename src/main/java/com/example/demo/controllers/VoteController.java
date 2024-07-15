package com.example.demo.controllers;

import com.example.demo.entities.Vote;
import com.example.demo.entities.VoteType;
import com.example.demo.entities.exceptions.PostSolvedException;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.services.VoteServImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    private VoteServImp voteService;

    @PostMapping("/Cast")
    public ResponseEntity<Vote> castVote(@RequestParam Long userId,
                                         @RequestParam(required = false) Long postId,
                                         @RequestParam(required = false) Long answerId,
                                         @RequestParam(required = false) Long commentId,
                                         @RequestParam int voteValue) {

        try {
        	VoteType type = VoteType.fromValue(voteValue);
            Vote savedVote = voteService.voteCast(userId, postId, answerId, commentId, type);
            return new ResponseEntity<>(savedVote, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
        	System.out.println(e);
            return ResponseEntity.badRequest().build();
        } catch (PostSolvedException e) {
        	System.out.println(e);
        	return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/Find/{voteId}")
    public ResponseEntity<Vote> findVote(@PathVariable Long voteId) {
        try {
            Vote vote = voteService.voteFind(voteId);
            return ResponseEntity.ok(vote);
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/FindAll")
    public ResponseEntity<List<Vote>> findAllVotes() {
        List<Vote> votes = voteService.voteFindAll();
        return ResponseEntity.ok(votes);
    }

    @GetMapping("/FindByUser/{userId}")
    public ResponseEntity<List<Vote>> findByUser(@PathVariable Long userId) {
        try {
            List<Vote> votes = voteService.voteFindByUser(userId);
            return ResponseEntity.ok(votes);
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/FindByPost/{postId}")
    public ResponseEntity<List<Vote>> findByPost(@PathVariable Long postId) {
        try {
            List<Vote> votes = voteService.voteFindByPost(postId);
            return ResponseEntity.ok(votes);
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/FindByAnswer/{answerId}")
    public ResponseEntity<List<Vote>> findByAnswer(@PathVariable Long answerId) {
        try {
            List<Vote> votes = voteService.voteFindByAnswer(answerId);
            return ResponseEntity.ok(votes);
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/FindByComment/{commentId}")
    public ResponseEntity<List<Vote>> findByComment(@PathVariable Long commentId) {
        try {
            List<Vote> votes = voteService.voteFindByComment(commentId);
            return ResponseEntity.ok(votes);
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/Cancel/{voteId}")
    public ResponseEntity<Void> cancelVote(@PathVariable Long voteId) {
        try {
            voteService.voteCancel(voteId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.notFound().build();
        } catch (PostSolvedException e) {
        	System.out.println(e);
        	return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/DeleteByUser/{userId}")
    public ResponseEntity<Void> deleteVotesByUser(@PathVariable Long userId) {
        try {
            voteService.voteDeleteByUser(userId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/DeleteByPost/{postId}")
    public ResponseEntity<Void> deleteVotesByPost(@PathVariable Long postId) {
        try {
            voteService.voteDeleteByPost(postId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/DeleteByAnswer/{answerId}")
    public ResponseEntity<Void> deleteVotesByAnswer(@PathVariable Long answerId) {
        try {
            voteService.voteDeleteByAnswer(answerId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/DeleteByComment/{commentId}")
    public ResponseEntity<Void> deleteVotesByComment(@PathVariable Long commentId) {
        try {
            voteService.voteDeleteByComment(commentId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/Update/{voteId}")
    public ResponseEntity<Vote> updateVote(@PathVariable Long voteId,
    		                               @RequestBody Vote updatedVote) {
    	try {
            Vote updated = voteService.voteUpdate(voteId, updatedVote);
            return ResponseEntity.ok(updated);
        } catch (ResourceNotFoundException e) {
        	System.out.println(e);
            return ResponseEntity.notFound().build();
        } catch (PostSolvedException e) {
        	System.out.println(e);
        	return ResponseEntity.badRequest().build();
        }
    }
}
