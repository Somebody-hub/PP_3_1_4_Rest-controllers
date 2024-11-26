package ru.kata.spring.boot_security.demo.exception;

public class IncorrectUserException extends RuntimeException {
    public IncorrectUserException(){
        super("Incorrect data! Please check fields and try again");
    }
}
