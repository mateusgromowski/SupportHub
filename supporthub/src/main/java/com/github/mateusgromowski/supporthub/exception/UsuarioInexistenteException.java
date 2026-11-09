package com.github.mateusgromowski.supporthub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioInexistenteException extends RuntimeException {

    public UsuarioInexistenteException(String message) {
        super(message);
    }

}
