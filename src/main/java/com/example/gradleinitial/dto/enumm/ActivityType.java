package com.example.gradleinitial.dto.enumm;


public enum ActivityType{

    LIKE("like", 1), SHARE("share", 2), VIEW("view", 3);

    private final String key;
    private final Integer value;

    ActivityType(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public Integer getValue() {
        return value;
    }
}

