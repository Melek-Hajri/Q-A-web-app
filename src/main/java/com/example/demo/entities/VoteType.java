package com.example.demo.entities;

public enum VoteType {
	Upvote(0),
	Downvote(1);
	
	private final int value;

	VoteType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static VoteType fromValue(int value) {
        for (VoteType voteType : VoteType.values()) {
            if (voteType.getValue() == value) {
                return voteType;
            }
        }
        throw new IllegalArgumentException("Invalid vote value: " + value);
    }
}
