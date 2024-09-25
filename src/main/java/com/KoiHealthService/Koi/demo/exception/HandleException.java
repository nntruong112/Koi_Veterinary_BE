package com.KoiHealthService.Koi.demo.exception;

import com.KoiHealthService.Koi.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class HandleException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<String> handlingMethodException(MethodArgumentNotValidException exception){
        return ResponseEntity.badRequest().body(exception.getFieldError().getDefaultMessage());
    }
}
