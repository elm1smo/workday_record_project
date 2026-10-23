package com.bennu.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.bennu.response.ErrorResponse;

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

    // errores por duplicidad de datos o violación de restricciones de integridad
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> manageIntegrityErrors(DataIntegrityViolationException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.CONFLICT.value(), // HTTP 409
            "Conflicto de integridad de datos",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // error por recurso no encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> manageNotFoundResource(ResourceNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Recurso no encontrado",
            ex.getMessage()
        );
        
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // HTTP 404
    }

    // error por endpoint no encontrado 
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> manageEndpointNotFound(NoHandlerFoundException ex) {

        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Endpoint no encontrado",
            "La ruta '" + ex.getRequestURL() + "' con el método HTTP '" + ex.getHttpMethod() + "' no existe en este servidor."
        );
        
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // HTTP 404
    }

    // otros errores
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manageOtherErrors(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), // HTTP 500
            "Error interno del servidor",
            "Error inesperado. Contacte al administrador."
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
