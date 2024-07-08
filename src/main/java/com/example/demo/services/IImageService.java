package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Image;

public interface IImageService {
	public Image imageAdd(Image image);
	public Image imageFind(Long image_id);
	public List<Image> imageFindAll();
	public List<Image> imageFindByPost(Long post_id);
	public List<Image> imageFindByAnswer(Long answer_id);
	public List<Image> imageFindByComment(Long comment_id);
	public void imageDelete(Long comment_id);
	public void imageDeleteByPost(Long post_id);
	public void imageDeleteByAnswer(Long answer_id);
	public void imageDeleteByComment(Long comment_id);
}
