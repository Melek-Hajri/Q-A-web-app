package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Tag;

@Repository
public interface ITagRepository extends JpaRepository<Tag, Long>{
	@Query("SELECT t FROM Tag t JOIN t.posts p WHERE p.id = :postId")
    List<Tag> tagFindByPost(@Param("postId") Long postId);
    Optional<Tag> findByName(String tagName);
}
