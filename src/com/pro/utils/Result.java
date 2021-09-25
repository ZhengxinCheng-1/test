package com.pro.utils;

/**
 * 返回结果封装
 * @param <T>
 */
public class Result<T> {

    private String msg;
    private int code;
    private T data;

    public static Result success() {
        Result result = new Result();
        result.setCode(200);
        return result;
    }

    public static Result success(String msg) {
        Result result = success();
        result.setMsg(msg);
        return result;
    }

    public static <T> Result success(T data) {
        Result result = success();
        result.setData(data);
        return result;
    }

    public static Result failed(String msg, int code) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
