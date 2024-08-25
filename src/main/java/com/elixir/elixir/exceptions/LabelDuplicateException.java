package com.elixir.elixir.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "La label que se intenta agregar esta duplicada")
public class LabelDuplicateException extends Exception {

}