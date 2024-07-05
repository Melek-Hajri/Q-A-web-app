package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Vote;

@Repository
public interface IVoteRepository extends JpaRepository<Vote, Long>{

}
