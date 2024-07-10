package com.example.demo.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Vote implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Enumerated(EnumType.ORDINAL)
	VoteType type;
	
	@ManyToOne
	@JsonIgnoreProperties({"votes", "posts", "answers", "comments"})
	User user;
	
	@ManyToOne
	@JsonIgnoreProperties({"votes", "user", "answers", "comments", "images"})
	Post post;
	
	@ManyToOne
	@JsonIgnoreProperties({"votes", "user", "post", "comments", "images"})
	Answer answer;
	
	@ManyToOne
	@JsonIgnoreProperties({"votes", "user", "post", "answer", "images"})
	Comment comment;
}
