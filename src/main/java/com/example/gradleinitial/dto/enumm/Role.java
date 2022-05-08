package com.example.gradleinitial.dto.enumm;

public enum Role{

    AUTHOR("Author", new Long(1)), READER("Reader", new Long(2));

    private final String key;
    private final Long value;

    Role(String key, Long value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public Long getValue() {
        return value;
    }
}