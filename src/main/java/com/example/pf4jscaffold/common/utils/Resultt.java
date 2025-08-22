package com.example.pf4jscaffold.common.utils;

import java.io.Serializable;

public class Resultt<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    public Resultt() {
    }

    public Resultt(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Resultt<T> success() {
        return success(null);
    }

    public static <T> Resultt<T> success(T data) {
        return new Resultt<>(200, "success", data);
    }

    public static <T> Resultt<T> error(int code, String msg) {
        return new Resultt<>(code, msg);
    }

    public Resultt(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}