package io.sponges.dubtrack4j.util;

public enum ContentType {

    JSON("application/json"),
    WWW_FORM("application/x-www-form-urlencoded"),
    OCTET_STREAM("application/octet-stream");

    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
