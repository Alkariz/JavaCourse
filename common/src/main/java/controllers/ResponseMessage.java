package controllers;

import java.io.Serializable;

public class ResponseMessage implements Serializable {
    private String message;
    private Boolean success;

    public ResponseMessage() {
    }

    public ResponseMessage(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "message=" + message +
                ", success=" + success +
                '}';
    }
}
