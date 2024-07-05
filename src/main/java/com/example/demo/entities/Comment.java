package com.example.demo.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
public class Comment implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne
	@JsonIgnoreProperties({"comments"})
	User user;
	@ManyToOne
	@JsonIgnoreProperties({"comments"})
	Post post;
	Answer answer;
	String body;
	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"comment"})
	List<Vote> votes;
	int voteCount;
	@Temporal(TemporalType.DATE)
	Date creationDate;
	
	public void updateVoteCountOnAdd(VoteType vote) {
		if (vote == VoteType.Upvote)
			this.voteCount++;
		else 
			this.voteCount--;
	}
	public void updateVoteCountOnRemove(VoteType vote) {
		if (vote == VoteType.Upvote)
			this.voteCount--;
		else 
			this.voteCount++;
	}
}
