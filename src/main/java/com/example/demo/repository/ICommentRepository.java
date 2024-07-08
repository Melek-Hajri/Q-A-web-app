package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Answer;
import com.example.demo.entities.Comment;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long>{
	@Query("SELECT c FROM Comment c WHERE c.post.id = :postId")
    List<Comment> commentFindByPost(@Param("postId") Long postId);
	@Query("SELECT c FROM Comment c WHERE c.answer.id = :answerId")
    List<Comment> commentFindByAnswer(@Param("answerId") Long answerId);
	@Query("SELECT c FROM Comment c WHERE c.user.id = :aId")
    List<Comment> commentFindByUser(@Param("userId") Long userId);
	@Query("DELETE FROM Comment c WHERE c.post.id = :postId")
    void commentDeleteByPost(@Param("postId") Long postId);
	@Query("DELETE FROM Comment c WHERE c.answer.id = :answerId")
    void commentDeleteByAnswer(@Param("answerId") Long answerId);
	@Query("DELETE FROM Comment c WHERE c.user.id = :userId")
    void commentDeleteByUser(@Param("userId") Long userId);
}
