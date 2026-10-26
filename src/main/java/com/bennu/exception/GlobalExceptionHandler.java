package com.bennu.exception;

import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.bennu.response.ErrorResponse;

import tools.jackson.databind.exc.MismatchedInputException;

@RestControllerAdvice 
public class GlobalExceptionHandler {
    // errores por mal argumento (datos inexistentes, argumentos incorrectos, etc)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> manageBadPetitions(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(), // HTTP 400
            "Petición incorrecta",
            ex.getMessage() // Mensaje de service
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // errores por duplicidad de datos o violación de restricciones de integridad en la BD
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> manageIntegrityErrors(DataIntegrityViolationException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.CONFLICT.value(), // HTTP 409
            "Conflicto de integridad de datos",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // error por recurso no encontrado (dato no encontrado)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> manageNotFoundResource(ResourceNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Recurso no encontrado",
            ex.getMessage()
        );
        
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // HTTP 404
    }

    // error por endpoint no encontrado (ruta inexistente)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> manageEndpointNotFound(NoHandlerFoundException ex) {

        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Endpoint no encontrado",
            "La ruta '" + ex.getRequestURL() + "' no existe en este servidor."
        );
        
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // HTTP 404
    }

    // ruta existente, pero con método HTTP distinto
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.METHOD_NOT_ALLOWED.value(),
            "Método no permitido",
            "El método " + ex.getMethod() + " no está permitido para esta ruta."
        );
        
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED); // HTTP 405
    }

    // argumento enviado no pasa la validación respectiva
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.joining(" | "));

        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(), // 400
            "Argumentos no válidos",
            message
        );
        
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // HTTP 400
    }

    // Error de tipo de dato en JSON (@RequestBody)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonTypeMismatch(HttpMessageNotReadableException ex) {
        String message = "Error en el formato de los datos enviados.";

        if (ex.getCause() instanceof MismatchedInputException mismatchedEx) {
            String field = mismatchedEx.getPath().get(0).getPropertyName();
            String expectedType = mismatchedEx.getTargetType().getSimpleName();
            message = String.format(
                "El campo '%s' recibió un valor incompatible. Se esperaba un tipo: %s.",
                field,
                expectedType
            );
        }

        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Error en el formato de los datos enviados (Body)",
            message
        );
        
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // HTTP 400
    }

    // 2. Captura errores de tipo de dato en la URL (@PathVariable o @RequestParam)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleUrlTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("El parámetro '%s' en la URL recibió el valor '%s', el cual no se puede convertir al tipo requerido (%s).", 
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Error en el formato de los datos enviados (URL)",
            message
        );
        
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // HTTP 400
    }

    // otros errores
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manageOtherErrors(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Error interno del servidor",
            "Error inesperado. Contacte al administrador."
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); // HTTP 500
    }
}
