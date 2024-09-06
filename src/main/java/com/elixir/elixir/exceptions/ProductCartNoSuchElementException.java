package com.elixir.elixir.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "El producto no existe en el carrito.")
public class ProductCartNoSuchElementException extends Exception {
    
}
