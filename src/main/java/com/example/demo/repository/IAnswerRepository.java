package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Answer;

@Repository
public interface IAnswerRepository extends JpaRepository<Answer, Long>{
	@Query("SELECT a FROM Answer a WHERE a.post.id = :postId")
    List<Answer> answerFindByPost(@Param("postId") Long postId);
	@Query("SELECT a FROM Answer a WHERE a.user.id = :userId")
    List<Answer> answerFindByUser(@Param("userId") Long userId);
}
