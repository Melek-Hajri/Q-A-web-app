package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Image;
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
	public Image imageAdd(Image image) {
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
	public void imageDelete(Long imageId) {
		this.imageRepo.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("Image not found"));
		this.imageRepo.deleteById(imageId);
	}
	@Override
	public void imageDeleteByPost(Long postId) {
		this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
		this.imageRepo.imageDeleteByPost(postId);
	}
	@Override
	public void imageDeleteByAnswer(Long answerId) {
		this.answerRepo.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found"));
		this.imageRepo.imageDeleteByAnswer(answerId);
	}
	@Override
	public void imageDeleteByComment(Long commentId) {
		this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
		this.imageRepo.imageDeleteByComment(commentId);
	}
}
