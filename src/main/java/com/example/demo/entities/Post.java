package com.example.demo.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
public class Post implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne
	@JsonIgnoreProperties({"posts", "answers", "comments", "votes"})
	User user;
	
	String title;
	
	String body;
	
	@ElementCollection
	@CollectionTable(name = "post_links", joinColumns = @JoinColumn(name = "post_id"))
	@Column(name = "link")
	List<String> links;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"post"})
	List<Image> images;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "post_tag",
		joinColumns = @JoinColumn(name = "post_id"),
		inverseJoinColumns = @JoinColumn(name = "tag_id")
	)
	List<Tag> tags;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"post", "user", "answer", "comment"})
	List<Vote> votes;
	
	int voteCount;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"post", "comments", "votes", "user"})
	List<Answer> answers;
	
	@Enumerated(EnumType.ORDINAL)
	StatusType status;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"post", "user", "answer", "votes"})
	List<Comment> comments;
	
	@Temporal(TemporalType.DATE)
	Date creationDate;
	
	@ElementCollection
	@CollectionTable(name = "post_recommendedtags", joinColumns = @JoinColumn(name = "post_id"))
	@Column(name = "tag_id")
	List<Tag> recommendedTags;
	
	@ManyToMany
    @JoinTable(
        name = "similar_posts",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "similar_post_id")
    )
	List<Post> similarPosts;
	
	//aiService: todo
	
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
