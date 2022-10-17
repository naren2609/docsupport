package com.docsupport.jp.pojo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class Exceptions {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class ForbiddenException extends RuntimeException {}

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="User Not Found")
    public static class UserNotFoundException extends RuntimeException {}

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason="User is Disabled")
    public static class UserDisabledException extends RuntimeException {}

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static class InvalidUserOrTokenException extends RuntimeException {}

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static class InvalidUserInfoException extends RuntimeException {}

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public static class TooManyAttemptsException extends RuntimeException {}

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static class EmailIdNotConfiguredException extends RuntimeException {
    }
}

