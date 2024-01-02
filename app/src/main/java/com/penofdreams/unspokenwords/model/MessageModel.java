package com.penofdreams.unspokenwords.model;

public class MessageModel {
    private final String name;
    private final String message;
    private final String email;
    private final boolean isPending;

    public MessageModel(String name, String message, String email, boolean isPending) {
        this.name = name;
        this.message = message;
        this.email = email;
        this.isPending = isPending;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsPending() {
        return isPending;
    }
}
