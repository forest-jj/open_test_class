package com.example.demo.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *@Description 自定义异常
 */
public class DemoBaseException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    @Setter
    @Getter
    private String msg;

    @Setter
    @Getter
    private int code = 500;

    public DemoBaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public DemoBaseException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public DemoBaseException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public DemoBaseException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}
