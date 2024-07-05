package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Answer;

@Repository
public interface IAnswerRepository extends JpaRepository<Answer, Long>{

}
