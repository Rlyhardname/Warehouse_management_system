package com.example.warehouses.Exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @SneakyThrows
    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<Object> handleWrongPasswordException(HttpServletResponse response){
        response.sendRedirect("http://localhost:8080/");
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(UserNotExististingException.class)
    public ResponseEntity<Object> HandleUserNotExistingException(HttpServletResponse response){

        response.sendRedirect("http://localhost:8080/");
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(ClientAlreadyRegisteredException.class)
    public ResponseEntity<Object> HandleClientAlreadyRegisteredException(HttpServletResponse response){

        response.sendRedirect("http://localhost:8080/createClient.html");
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }


}
