package com.paypal.bfs.test.employeeserv.api.exceptions;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    public ServiceException(String message) {
        super(message);
    }
}
