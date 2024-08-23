package com.elixir.elixir.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "La categoria no existe")
public class CategoryNoSuchElementException extends Exception {

}