package com.elixir.elixir.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "La variedad que se intenta agregar esta duplicada")
public class VarietyDuplicateException extends Exception {

}