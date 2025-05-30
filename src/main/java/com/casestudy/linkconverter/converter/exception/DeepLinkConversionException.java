package com.casestudy.linkconverter.converter.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * Exception thrown when deep-link conversion fails.
 * Carries a default error message prefix and an HTTP status code
 * indicating a bad request (400).
 */
public class DeepLinkConversionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3360470571052054538L;

    /** HTTP status that will be returned for this error. */
    public static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    private static final String DEFAULT_MESSAGE = """
        Deep link conversion failed!
        """;

    /**
     * Create a new DeepLinkConversionException with a detailed message.
     *
     * @param detail additional detail describing the failure
     */
    public DeepLinkConversionException(final String detail) {
        super(DEFAULT_MESSAGE + " " + detail);
    }

    /**
     * Create a new DeepLinkConversionException with a detailed message and a root cause.
     *
     * @param detail additional detail describing the failure
     * @param cause the original exception that caused this error
     */
    public DeepLinkConversionException(final String detail, final Throwable cause) {
        super(DEFAULT_MESSAGE + " " + detail, cause);
    }

}
