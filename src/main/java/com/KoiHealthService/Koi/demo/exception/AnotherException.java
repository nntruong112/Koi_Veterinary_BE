package com.KoiHealthService.Koi.demo.exception;

public class AnotherException extends RuntimeException {

    public AnotherException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private ErrorCode errorCode;
}
