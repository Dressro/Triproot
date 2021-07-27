package com.example.project2;

public class comment {
    String comment;
    String register;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public comment(String comment, String register) {
        this.comment = comment;
        this.register = register;
    }
}
