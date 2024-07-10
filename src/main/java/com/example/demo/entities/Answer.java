package com.example.demo.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Answer implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne
	@JsonIgnoreProperties({"answers"})
	User user;
	
	@ManyToOne
	@JsonIgnoreProperties({"answers"})
	Post post;
	
	String body;
	
	@ElementCollection
	@CollectionTable(name = "answer_links", joinColumns = @JoinColumn(name = "answer_id"))
	@Column(name = "link")
	List<String> links;
	
	@OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"answer"})
	List<Vote> votes;
	
	int voteCount;
	
	@OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"answer"})
	List<Comment> comments;
	
	@OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"answer"})
	List<Image> images;
	
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
