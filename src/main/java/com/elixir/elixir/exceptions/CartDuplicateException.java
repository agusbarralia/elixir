package com.elixir.elixir.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El usuario ya tiene asigando un carrito")
public class CartDuplicateException extends Exception {
    
}
