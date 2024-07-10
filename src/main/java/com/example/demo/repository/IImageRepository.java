package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Image;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long>{
	@Query("SELECT i FROM Image i WHERE i.post.id = :postId")
    List<Image> imageFindByPost(@Param("postId") Long postId);
	@Query("SELECT i FROM Image i WHERE i.answer.id = :answerId")
    List<Image> imageFindByAnswer(@Param("answerId") Long answerId);
	@Query("SELECT i FROM Image i WHERE i.comment.id = :commentId")
    List<Image> imageFindByComment(@Param("commentId") Long commentId);
}
