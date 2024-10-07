package com.KoiHealthService.Koi.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnotherException extends RuntimeException {

    public AnotherException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private ErrorCode errorCode;
}
