package com.example.demo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Answer;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Image;
import com.example.demo.entities.Post;
import com.example.demo.entities.exceptions.ResourceNotFoundException;
import com.example.demo.repository.IImageRepository;

@Service
public class ImageServImp implements IImageService {
	
	@Autowired
	private IImageRepository imageRepo;
	
	@Autowired
	private PostServImp postService;
	
	@Autowired
	private AnswerServImp answerService;
	
	@Autowired
	private CommentServImp commentService;
	
	@Override
	@Transactional
	public Image imageAdd(byte[] data, Long postId, Long answerId, Long commentId) {
		Image image = new Image();
		image.setData(data);
		if (postId != null && answerId == null && commentId == null) {
            Post post = this.postService.postFind(postId);
            post.getImages().add(image);
            image.setPost(post);
        } else if (answerId != null && postId == null && commentId == null) {
            Answer answer = this.answerService.answerFind(answerId);
            answer.getImages().add(image);
            image.setAnswer(answer);
        } else if (commentId != null && postId == null && answerId == null) {
            Comment comment = this.commentService.commentFind(commentId);
            comment.getImages().add(image);
            image.setComment(comment);
        } else {
            throw new IllegalArgumentException("Either postId, answerId, or commentId must be provided");
        }
		return this.imageRepo.save(image);
	}
	
	@Override 
	public Image imageFind(Long imageId) {
		return this.imageRepo.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("Image not found"));
	}
	
	@Override
	public List<Image> imageFindAll() {
		return this.imageRepo.findAll();
	}
	
	@Override
	public List<Image> imageFindByPost(Long postId) {
		this.postService.postFind(postId);
		return this.imageRepo.imageFindByPost(postId);
	}
	
	@Override
	public List<Image> imageFindByAnswer(Long answerId) {
		this.answerService.answerFind(answerId);
		return this.imageRepo.imageFindByAnswer(answerId);
	}
	
	@Override
	public List<Image> imageFindByComment(Long commentId) {
		this.commentService.commentFind(commentId);
		return this.imageRepo.imageFindByComment(commentId);
	}
	
	@Override
	@Transactional
	public void imageDelete(Long imageId) {
		Image image = this.imageFind(imageId);
		if (image.getPost() != null) {
			image.getPost().getImages().remove(image);
        } else if (image.getAnswer() != null) {
        	image.getAnswer().getImages().remove(image);
        } else if (image.getComment() != null) {
        	image.getComment().getImages().remove(image);
        }
		this.imageRepo.deleteById(imageId);
	}
	
	@Override
	@Transactional
	public void imageDeleteByPost(Long postId) {
		Post post = this.postService.postFind(postId);
		for(Image image : post.getImages()) {
			this.imageDelete(image.getId());
		}
	}
	
	@Override
	@Transactional
	public void imageDeleteByAnswer(Long answerId) {
		Answer answer = this.answerService.answerFind(answerId);
		for(Image image : answer.getImages()) {
			this.imageDelete(image.getId());
		}
	}
	
	@Override
	@Transactional
	public void imageDeleteByComment(Long commentId) {
		Comment comment = this.commentService.commentFind(commentId);
		for(Image image : comment.getImages()) {
			this.imageDelete(image.getId());
		}
	}
}
