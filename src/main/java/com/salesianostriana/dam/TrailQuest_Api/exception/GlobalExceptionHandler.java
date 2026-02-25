package com.salesianostriana.dam.TrailQuest_Api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Builder;
import lombok.extern.java.Log;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Log
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
        ProblemDetail result = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        result.setTitle("Recurso no encontrado");
        result.setType(URI.create("https://www.trailquest-api.com/errors/resource-not-found"));
        return result;
    }

    @ExceptionHandler(UsernameAlredyInUseException.class)
    public ProblemDetail handleUsernameAlreadyInUse(UsernameAlredyInUseException ex) {
        ProblemDetail result = ProblemDetail
                .forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        result.setTitle("Nombre de usuario ya en uso");
        result.setType(URI.create("https://www.trailquest-api.com/errors/username-already-in-use"));
        return result;
    }

    @ExceptionHandler(InvalidFileTypeException.class)
    public ProblemDetail handleInvalidFileType(InvalidFileTypeException ex) {
        ProblemDetail result = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        result.setTitle("Tipo de archivo no válido");
        result.setType(URI.create("https://www.trailquest-api.com/errors/invalid-file-type"));
        return result;
    }

    @ExceptionHandler(StorageException.class)
    public ProblemDetail handleStorageException(StorageException ex) {
        ProblemDetail result = ProblemDetail
                .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar el archivo");
        result.setTitle("Error de almacenamiento");
        result.setType(URI.create("https://www.trailquest-api.com/errors/storage-error"));
        log.severe("Error de almacenamiento: " + ex.getMessage());
        return result;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        ProblemDetail result = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        result.setTitle("Argumento no válido");
        result.setType(URI.create("https://www.trailquest-api.com/errors/illegal-argument"));
        return result;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex) {
        ProblemDetail result = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Error de validación");

        List<ApiValidationSubError> subErrors =
                ex.getConstraintViolations().stream()
                        .map(ApiValidationSubError::from)
                        .toList();

        result.setProperty("invalid-params", subErrors);
        result.setTitle("Violación de restricciones");
        result.setType(URI.create("https://www.trailquest-api.com/errors/constraint-violation"));

        return result;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ProblemDetail result = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Error de validación en los datos de entrada"
        );

        List<ApiValidationSubError> subErrors =
                ex.getAllErrors().stream()
                        .map(ApiValidationSubError::from)
                        .toList();

        result.setProperty("invalid-params", subErrors);
        result.setTitle("Argumento no válido");
        result.setType(URI.create("https://www.trailquest-api.com/errors/validation-error"));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        ProblemDetail result = ProblemDetail
                .forStatusAndDetail(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error interno del servidor"
                );
        result.setTitle("Error inesperado");
        result.setType(URI.create("https://www.trailquest-api.com/errors/internal-server-error"));

        log.severe("Error no controlado: " + ex.getMessage());
        ex.printStackTrace();

        return result;
    }

    // Record para los sub-errores de validación (igual que en tu ejemplo)
    @Builder
    record ApiValidationSubError(
            String object,
            String message,
            @JsonInclude(JsonInclude.Include.NON_NULL)
            String field,
            @JsonInclude(JsonInclude.Include.NON_NULL)
            Object rejectedValue
    ) {
        public ApiValidationSubError(String object, String message) {
            this(object, message, null, null);
        }

        public static ApiValidationSubError from(ObjectError error) {
            if (error instanceof FieldError fieldError) {
                return ApiValidationSubError.builder()
                        .object(fieldError.getObjectName())
                        .message(fieldError.getDefaultMessage())
                        .field(fieldError.getField())
                        .rejectedValue(fieldError.getRejectedValue())
                        .build();
            } else {
                return ApiValidationSubError.builder()
                        .object(error.getObjectName())
                        .message(error.getDefaultMessage())
                        .build();
            }
        }

        public static ApiValidationSubError from(ConstraintViolation v) {
            return ApiValidationSubError.builder()
                    .message(v.getMessage())
                    .rejectedValue(v.getInvalidValue())
                    .object(v.getRootBean().getClass().getSimpleName())
                    .field(
                            Optional.ofNullable(v.getPropertyPath())
                                    .map(PathImpl.class::cast)
                                    .map(PathImpl::getLeafNode)
                                    .map(NodeImpl::asString)
                                    .orElse("unknown")
                    )
                    .build();
        }
    }
}