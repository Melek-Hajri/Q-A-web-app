package com.example.demo.entities;

public enum RoleType {
	SimpleUser(0),
	Admin(1);
	
    private final int value;

    RoleType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RoleType fromValue(int value) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.getValue() == value) {
                return roleType;
            }
        }
        throw new IllegalArgumentException("Invalid role value: " + value);
    }
}

