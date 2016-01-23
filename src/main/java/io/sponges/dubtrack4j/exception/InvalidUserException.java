package io.sponges.dubtrack4j.exception;

public class InvalidUserException extends Exception {

    private final String username;

    public InvalidUserException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "Invalid user \"" + username + "\"";
    }
}
