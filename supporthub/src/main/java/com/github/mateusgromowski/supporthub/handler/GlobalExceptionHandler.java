package com.github.mateusgromowski.supporthub.handler;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.github.mateusgromowski.supporthub.exception.BancoDeDadosVazioException;
import com.github.mateusgromowski.supporthub.exception.CategoriaDuplicadaException;
import com.github.mateusgromowski.supporthub.exception.CategoriaInexistenteException;
import com.github.mateusgromowski.supporthub.exception.EmailDuplicadoException;
import com.github.mateusgromowski.supporthub.exception.UsuarioInexistenteException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ BancoDeDadosVazioException.class, UsuarioInexistenteException.class,
            CategoriaInexistenteException.class, HttpMessageNotReadableException.class,
            DataIntegrityViolationException.class })
    public ResponseEntity<?> handleBadRequest(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ EmailDuplicadoException.class, CategoriaDuplicadaException.class })
    public ResponseEntity<?> handleConflict(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
