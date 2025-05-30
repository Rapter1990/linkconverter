package com.casestudy.linkconverter.common.exception;

import com.casestudy.linkconverter.common.model.CustomError;
import com.casestudy.linkconverter.converter.exception.DeepLinkConversionException;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Global exception handler that intercepts various exceptions thrown by controllers
 * and converts them into well-formed {@link CustomError} responses.
 *
 * <p>Handled exception types:
 * <ul>
 *   <li>{@link MethodArgumentNotValidException} – for @Valid request‐body validation failures</li>
 *   <li>{@link ConstraintViolationException} – for @PathVariable/@RequestParam constraint violations</li>
 *   <li>{@link RuntimeException} – for uncaught runtime errors (mapped to 404)</li>
 *   <li>{@link DeepLinkConversionException} – for custom deep-link conversion errors</li>
 * </ul>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle validation errors on request bodies annotated with {@code @Valid}.
     *
     * @param ex the exception containing field‐level validation failures
     * @return a 400 Bad Request with a {@link CustomError} describing each sub-error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {

        List<CustomError.CustomSubError> subErrors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    subErrors.add(
                            CustomError.CustomSubError.builder()
                                    .field(fieldName)
                                    .message(message)
                                    .build()
                    );
                }
        );

        CustomError customError = CustomError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(CustomError.Header.VALIDATION_ERROR.getName())
                .message("Validation failed")
                .subErrors(subErrors)
                .build();

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);

    }

    /**
     * Handle constraint violations on path variables or request parameters.
     *
     * @param ex the exception containing constraint violations
     * @return a 400 Bad Request with a {@link CustomError} listing each violation
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handlePathVariableErrors(final ConstraintViolationException ex) {

        List<CustomError.CustomSubError> subErrors = new ArrayList<>();
        ex.getConstraintViolations()
                .forEach(constraintViolation ->
                        subErrors.add(
                                CustomError.CustomSubError.builder()
                                        .message(constraintViolation.getMessage())
                                        .field(StringUtils.substringAfterLast(constraintViolation.getPropertyPath().toString(), "."))
                                        .value(constraintViolation.getInvalidValue() != null ? constraintViolation.getInvalidValue().toString() : null)
                                        .type(constraintViolation.getInvalidValue().getClass().getSimpleName())
                                        .build()
                        )
                );

        CustomError customError = CustomError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(CustomError.Header.VALIDATION_ERROR.getName())
                .message("Constraint violation")
                .subErrors(subErrors)
                .build();

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);

    }

    /**
     * Handle any uncaught {@link RuntimeException}.
     *
     * @param ex the runtime exception
     * @return a 404 Not Found with a {@link CustomError} containing the exception message
     */
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<?> handleRuntimeException(final RuntimeException ex) {
        CustomError customError = CustomError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .header(CustomError.Header.API_ERROR.getName())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle application-specific {@link DeepLinkConversionException}.
     *
     * @param ex the deep-link conversion exception, including its HTTP status
     * @return a response with the exception’s configured status and a {@link CustomError}
     */
    @ExceptionHandler(DeepLinkConversionException.class)
    protected ResponseEntity<CustomError> handleDeepLinkConversion(DeepLinkConversionException ex) {
        CustomError error = CustomError.builder()
                .httpStatus(DeepLinkConversionException.STATUS)
                .header(CustomError.Header.API_ERROR.getName())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, DeepLinkConversionException.STATUS);
    }

}
