package com.docsupport.jp.pojo;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ApiError {

    private Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private HttpStatus status;
    private String error;
    private String message;

    /*
    "timestamp": "2022-09-25T08:33:52.268+00:00",
    "status": 409,
    "error": "Conflict",
    "message": "My Cause: Email or Mobile Number already Exists",
    "path": "/docsupport/registerjs"
    */

    public ApiError(HttpStatus status, String error, String message) {
        super();
        this.timestamp = new Date();
        this.status = status;
        this.error = error;
        this.message = message;
    }

}