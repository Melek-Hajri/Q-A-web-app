package com.example.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Image;
import com.example.demo.entities.Post;
import com.example.demo.entities.StatusType;
import com.example.demo.entities.Tag;
import com.example.demo.entities.User;
import com.example.demo.entities.exceptions.ImpossibleUpdateException;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.repository.IImageRepository;
import com.example.demo.repository.IPostRepository;
import com.example.demo.repository.ITagRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.repository.IVoteRepository;

@Service
public class PostServImp implements IPostService {
	
	@Autowired
	private IUserRepository userRepo;
	
	@Autowired
	private IPostRepository postRepo;
	
	@Autowired
	private IImageRepository imageRepo;
	
	@Autowired
	private AnswerServImp answerService;
	
	@Autowired
	private CommentServImp commentService;
	
	@Autowired
	private ITagRepository tagRepo;
	
	@Autowired
	private ImageServImp imageService;
	
	@Autowired
	private VoteServImp voteService;
	
	
	@Override
	@Transactional
	public Post postAdd(Long userId, String title, String body, List<String> links, List<byte[]> images, StatusType status) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		Post post = new Post();
		post.setUser(user);
		post.setTitle(title);
		post.setBody(body);
		if(links!=null)
			post.setLinks(links);
		post.setStatus(status);
		post.setCreationDate(new Date());
		if(images != null) {
			post.setImages(new ArrayList<>());
			post = this.postRepo.save(post);
			
			for (byte[] imageData : images) {
	            Image image = new Image();
	            image.setData(imageData);
	            image.setPost(post);
	            imageRepo.save(image);
	            
	            post.getImages().add(image);
	        }
		}
		return this.postRepo.save(post);
	}
	
	@Override
	@Transactional
	public void setPostTags(Long postId, List<Long> tagIds) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		post.setTags(new HashSet<>());
		for(Long id : tagIds) {
			Tag tag = this.tagRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
			post.getTags().add(tag);
			if(tag.getPosts() == null) {
				tag.setPosts(new HashSet<>());
			}
			tag.getPosts().add(post);
		}
	}

	@Override
	public Post postFind(Long postId) {
		return this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
	}
	
	@Override
	public List<Post> postFindAll() {
		return this.postRepo.findAll();
	}
	
	@Override
	public List<Post> postFindByUser(Long userId) {
		this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return this.postRepo.postFindByUser(userId);
	}
	
	@Override
	@Transactional
	public void postDelete(Long postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		if(post.getUser() != null) {
			User user = post.getUser();
			user.getPosts().remove(post);
		}
		if(post.getTags() != null) {
			for(Tag tag : post.getTags()) {
				tag.getPosts().remove(post);
			}
		}
		this.answerService.answerDeleteByPost(postId);
		this.commentService.commentDeleteByPost(postId);
		this.imageService.imageDeleteByPost(postId);
		this.voteService.voteDeleteByPost(postId);
	}
	
	@Override
	@Transactional
	public void postDeleteByUser(Long userId) {
		for(Post post : this.postFindByUser(userId)) {
			this.postDelete(post.getId());
		}
	}
	
	@Override
	@Transactional
	public Post postUpdate(Long postId, Post updatedPost) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		if(post.getStatus() == StatusType.Solved) {
			throw new ImpossibleUpdateException("Post is already solved and can't be modified");
		} else if(!post.getAnswers().isEmpty() || !post.getComments().isEmpty() || !post.getVotes().isEmpty()) {
			throw new ImpossibleUpdateException("Post has already been commented, answered or voted and can't be modified");
		} else {
			post.setTitle(updatedPost.getTitle());
			post.setBody(updatedPost.getBody());
			post.setLinks(updatedPost.getLinks());
			post.setImages(updatedPost.getImages());
			post.setStatus(updatedPost.getStatus());
			return this.postRepo.save(post);
		}
	}

}
