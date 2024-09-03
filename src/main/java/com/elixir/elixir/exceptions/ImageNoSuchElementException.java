package com.elixir.elixir.exceptions;

public class ImageNoSuchElementException extends Exception {
    public ImageNoSuchElementException() {
        super("Image not found");
    }
}