package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

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
	@Query("SELECT v FROM Vote v WHERE v.post.id = :postId AND v.user.id = :userId")
    Optional<Vote> voteFindByUserPost(@Param("postId") Long postId, @Param("userId") Long userId);
	@Query("SELECT v FROM Vote v WHERE v.answer.id = :answerId AND v.user.id = :userId")
	Optional<Vote> voteFindByUserAnswer(@Param("answerId") Long answerId, @Param("userId") Long userId);
	@Query("SELECT v FROM Vote v WHERE v.comment.id = :commentId AND v.user.id = :userId")
	Optional<Vote> voteFindByUserComment(@Param("commentId") Long commentId, @Param("userId") Long userId);
	
}
