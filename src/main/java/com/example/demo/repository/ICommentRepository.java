package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Comment;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long>{

}
