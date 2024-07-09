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
import com.example.demo.repository.IAnswerRepository;
import com.example.demo.repository.ICommentRepository;
import com.example.demo.repository.IImageRepository;
import com.example.demo.repository.IPostRepository;

@Service
public class ImageServImp implements IImageService {
	
	@Autowired
	private IImageRepository imageRepo;
	
	@Autowired
	private IPostRepository postRepo;
	
	@Autowired
	private IAnswerRepository answerRepo;
	
	@Autowired
	private ICommentRepository commentRepo;
	
	@Override
	@Transactional
	public Image imageAdd(byte[] data, Long postId, Long answerId, Long commentId) {
		Image image = new Image();
		image.setData(data);
		if (postId != null && answerId == null && commentId == null) {
            Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
            post.getImages().add(image);
            image.setPost(post);
        } else if (answerId != null && postId == null && commentId == null) {
            Answer answer = this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
            answer.getImages().add(image);
            image.setAnswer(answer);
        } else if (commentId != null && postId == null && answerId == null) {
            Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
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
		this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		return this.imageRepo.imageFindByPost(postId);
	}
	
	@Override
	public List<Image> imageFindByAnswer(Long answerId) {
		this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
		return this.imageRepo.imageFindByAnswer(answerId);
	}
	
	@Override
	public List<Image> imageFindByComment(Long commentId) {
		this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
		return this.imageRepo.imageFindByComment(commentId);
	}
	
	@Override
	@Transactional
	public void imageDelete(Long imageId) {
		Image image = this.imageRepo.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("Image not found"));
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
		this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		for(Image image : this.imageRepo.imageFindByPost(postId)) {
			this.imageDelete(image.getId());
		}
	}
	
	@Override
	@Transactional
	public void imageDeleteByAnswer(Long answerId) {
		this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
		for(Image image : this.imageRepo.imageFindByAnswer(answerId)) {
			this.imageDelete(image.getId());
		}
	}
	
	@Override
	@Transactional
	public void imageDeleteByComment(Long commentId) {
		this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
		for(Image image : this.imageRepo.imageFindByComment(commentId)) {
			this.imageDelete(image.getId());
		}
	}
}
