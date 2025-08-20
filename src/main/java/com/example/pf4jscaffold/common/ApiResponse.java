package com.example.pf4jscaffold.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 统一API响应格式
 * @param <T> 数据类型
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    
    private Integer code;
    private String message;
    private T data;
    
    public ApiResponse() {}
    
    public ApiResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 成功响应
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(200, "success");
    }
    
    /**
     * 成功响应带数据
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "success", data);
    }
    
    /**
     * 成功响应带自定义消息
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }
    
    /**
     * 客户端错误响应
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(400, message);
    }
    
    /**
     * 服务器错误响应
     */
    public static <T> ApiResponse<T> serverError(String message) {
        return new ApiResponse<>(500, message);
    }
    
    /**
     * 自定义响应
     */
    public static <T> ApiResponse<T> custom(Integer code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }
    
    // Getters and Setters
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}