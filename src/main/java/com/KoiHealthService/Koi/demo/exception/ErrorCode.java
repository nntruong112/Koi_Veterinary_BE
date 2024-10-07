package com.KoiHealthService.Koi.demo.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    NO_CUSTOMER_FOUND(1000, "No customer found", HttpStatus.NOT_FOUND),
    FISH_AGE_INVALID (2000, "Fish age invalid", HttpStatus.BAD_REQUEST),
    USER_UNACCEPTED(9999,"User is not accepted", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED( 1002, "User existed",HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003,"Username must be at least 3 characters",HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004,"Password must be at least 8 characters",HttpStatus.BAD_REQUEST),
    INVALID_KEY(1001,"Invalid message key",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005,"User not existed",HttpStatus.NOT_FOUND),
    EMAIL_INVALID(1011,"Email must be include @gmail.com",HttpStatus.BAD_REQUEST),
    PASSWORD_MATCHES(1009, "Password are not matches",HttpStatus.BAD_REQUEST),
    PHONE_INVALID(1012,"Phone must be 10 number",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006,"Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007,"You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_CODE(1008,"Invalid Code",HttpStatus.BAD_REQUEST),
    VERIFICATION_CODE_ALREADY_SENT(1010,"Code have already sent",HttpStatus.BAD_REQUEST)
    ;
    @Setter
    private int code;
    @Setter
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
