package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Vote;

@Repository
public interface IVoteRepository extends JpaRepository<Vote, Long>{
	@Query("SELECT v FROM Vote v WHERE v.post.id = :postId")
    List<Vote> voteFindByPost(@Param("postId") Long postId);
	@Query("SELECT v FROM Vote v WHERE v.answer.id = :answerId")
    List<Vote> voteFindByAnswer(@Param("answerId") Long answerId);
	@Query("SELECT v FROM Vote v WHERE v.comment.id = :commentId")
    List<Vote> voteFindByComment(@Param("commentId") Long commentId);
	@Query("SELECT v FROM Vote v WHERE v.user.id = :userId")
    List<Vote> voteFindByUser(@Param("userId") Long userId);
	@Query("DELETE FROM Vote v WHERE v.post.id = :postId")
    void voteDeleteByPost(@Param("postId") Long postId);
	@Query("DELETE FROM Vote v WHERE v.answer.id = :answerId")
    void voteDeleteByAnswer(@Param("answerId") Long answerId);
	@Query("DELETE FROM Vote v WHERE v.comment.id = :commentId")
    void voteDeleteByComment(@Param("commentId") Long commentId);
	@Query("DELETE FROM Vote v WHERE v.user.id = :userId")
    void voteDeleteByUser(@Param("userId") Long userId);
}
