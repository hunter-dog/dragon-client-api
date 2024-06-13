package com.dragan.emuson.exception;

import com.dragan.emuson.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 클라이언트 요청에 대한 예외처리
     * @param bre
     * @return http 응답
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response> handleBadRequestException(BadRequestException bre) {
        log.error("bad request exception - {}", bre.getMessage());

        Response response = Response.ofError(BAD_REQUEST.value(), bre.getMessage(), null, bre.getClass().getSimpleName());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    /**
     * 클라이언트 요청 데이터 유효성 검증 실패에 대한 예외처리
     * @param e
     * @return http 응답
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationExceptions(MethodArgumentNotValidException e) {
        log.error("validation error - {}", e.getMessage());
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        Response response = Response.ofError(BAD_REQUEST.value(), "유효성 검증 실패", errors, e.getClass().getSimpleName());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    /**
     * 알 수 없는 오류에 대한 예외처리
     * @param e
     * @return http 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception e) {
        log.error("unknown error", e);

        Response response = Response.ofError(INTERNAL_SERVER_ERROR.value(), "알 수 없는 오류 발생", null, e.getClass().getSimpleName());
        return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
    }

}
