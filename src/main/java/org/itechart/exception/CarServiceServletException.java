package org.itechart.exception;

import jakarta.servlet.ServletException;

public class CarServiceServletException extends ServletException {
    public CarServiceServletException(String message) {
        super(message);
    }

    public CarServiceServletException() {
    }

    public CarServiceServletException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public CarServiceServletException(Throwable rootCause) {
        super(rootCause);
    }
}
