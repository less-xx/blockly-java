package org.teapotech.blockly.web;

public class ErrorResponse {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ErrorResponse of(String message) {
        ErrorResponse err = new ErrorResponse();
        err.setMessage(message);
        return err;
    }

}
