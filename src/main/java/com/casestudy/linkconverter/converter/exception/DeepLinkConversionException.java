package com.casestudy.linkconverter.converter.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class DeepLinkConversionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3360470571052054538L;

    /** HTTP status that will be returned for this error. */
    public static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    private static final String DEFAULT_MESSAGE = """
        Deep link conversion failed!
        """;

    public DeepLinkConversionException(final String detail) {
        super(DEFAULT_MESSAGE + " " + detail);
    }

    public DeepLinkConversionException(final String detail, final Throwable cause) {
        super(DEFAULT_MESSAGE + " " + detail, cause);
    }

}
