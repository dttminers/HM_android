package com.hm.application.model;

public class HMException extends Exception {
    public HMException(String message) {
        super(message);
    }

    public HMException(String message, Exception e) {
        super(message, e);
        e.printStackTrace();
    }
}