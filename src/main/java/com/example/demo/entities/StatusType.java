package com.example.demo.entities;

public enum StatusType {
	Solved(0),
	Unsolved(1);
	
	private final int value;

	StatusType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StatusType fromValue(int value) {
        for (StatusType statusType : StatusType.values()) {
            if (statusType.getValue() == value) {
                return statusType;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
