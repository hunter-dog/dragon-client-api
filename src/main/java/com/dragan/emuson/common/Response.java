package com.dragan.emuson.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Response 공통 DTO
 * @param <T>
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private int code = 200;
    private boolean status = true;
    private String message;
    private String errorType;

    @JsonProperty("data")
    private T result;

    @JsonProperty("data")
    private List<T> results;

    private PageInfo pageInfo;

    private Response(T result) {
        this.result = result;
    }

    private Response(String message, T result) {
        this.message = message;
        this.result = result;
    }

    private Response(List<T> results) {
        this.results = results;
    }

    public Response(List<T> results, PageInfo pageInfo) {
        this.results = results;
        this.pageInfo = pageInfo;
    }

    private Response(int code, String message, T result, String errorType) {
        this.status = false;
        this.code = code;
        this.message = message;
        this.result = result;
        this.errorType = errorType;
    }

    public static <T> Response<T> ofSuccess(T result) {
        return new Response<>(result);
    }

    public static <T> Response<T> ofSuccess(String message, T result) {
        return new Response<>(message, result);
    }

    public static <T> Response<T> ofSuccess(List<T> results) {
        return new Response<>(results);
    }

    public static <T> Response<T> ofError(int code, String message, T result, String errorType) {
        return new Response<>(code, message, result, errorType);
    }

    public static <T> Response<T> ofSuccessWithPaging(Page<T> page) {
        return new Response<>(page.getContent(), PageInfo.fromPage(page));
    }

    @JsonProperty("data")
    public Object getData() {
        return result != null ? result : results;
    }

}
