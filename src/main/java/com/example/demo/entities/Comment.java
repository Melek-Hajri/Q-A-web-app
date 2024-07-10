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
	
	@ManyToOne
	@JsonIgnoreProperties({"comments"})
	Answer answer;
	
	String body;
	
	@ElementCollection
	@CollectionTable(name = "comment_links", joinColumns = @JoinColumn(name = "comment_id"))
	@Column(name = "link")
	List<String> links;
	
	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"comment"})
	List<Vote> votes;
	
	int voteCount;
	
	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"comment"})
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
