package com.mensalidade.ifrit.controllers.exceptions;

import java.io.Serializable;

public class StandardError implements Serializable {

    private Integer status;
    private String message;
    private Long timeStamp;
    private Integer line;

    public StandardError(Integer status, String message, Long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public StandardError(Integer status, String message, Long timeStamp, Integer line) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
        this.line = line;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }
}
