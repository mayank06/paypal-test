package com.paypal.bfs.test.employeeserv.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends ServiceException {

    public InternalServerErrorException(String message) {
        super(message);
    }
}
