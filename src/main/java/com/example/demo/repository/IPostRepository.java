package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Post;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long>{
	@Query("SELECT p FROM Post p WHERE p.user.id = :userId")
	List<Post> postFindByUser(@Param("userId") Long userId);
}
