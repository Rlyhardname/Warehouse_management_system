package com.example.warehouses.Exception;

import com.example.warehouses.Exception.Client.*;
import com.example.warehouses.Exception.Login.WrongPasswordException;
import com.example.warehouses.Exception.Warehouse.AlreadyRentedException;
import com.example.warehouses.Exception.Warehouse.WarehouseAlreadyExistsException;
import com.example.warehouses.Exception.Warehouse.WarehouseNotExistingException;
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
    public ResponseEntity<Object> handleWrongPasswordException(HttpServletResponse response) {
        response.sendRedirect("http://localhost:8080/exception/WrongPasswordException");
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(UserNotExististingException.class)
    public ResponseEntity<Object> HandleUserNotExistingException(HttpServletResponse response) {

        response.sendRedirect("http://localhost:8080/exception/UserNotExististingException");
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(ClientAlreadyRegisteredException.class)
    public ResponseEntity<Object> HandleClientAlreadyRegisteredException(HttpServletResponse response) {

        response.sendRedirect("http://localhost:8080/exception/ClientAlreadyRegisteredException.html");
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(WarehouseAlreadyExistsException.class)
    public ResponseEntity<Object> HandleWarehouseAlreadyExistsException(HttpServletResponse response) {

        response.sendRedirect("http://localhost:8080/exception/WarehouseAlreadyExistsException");
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(WarehouseNotExistingException.class)
    public ResponseEntity<Object> HandleWarehouseNotExistingException(HttpServletResponse response) {

        System.out.println("Warehouse with such an ID doesn't exist!");
        response.sendRedirect("http://localhost:8080/exception/WarehouseNotExistingException");
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(OwnerDoesntOwnAnyWarehouseException.class)
    public ResponseEntity<Object> HandleOwnerDoesntOwnAnyWarehouseException(HttpServletResponse response) {

        System.out.println("Owner doesn't own any warehouse!");
        response.sendRedirect("http://localhost:8080/exception/OwnerDoesntOwnAnyWarehouseException.html");
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(AgentHasNoContractsException.class)
    public ResponseEntity<Object> HandleAgentHasNoContractsException(HttpServletResponse response) {

        response.sendRedirect("http://localhost:8080/exception/AgentHasNoContractsException.html");
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(AlreadyRentedException.class)
    public ResponseEntity<Object> HandleAlreadyRentedException(HttpServletResponse response) {

        response.sendRedirect("http://localhost:8080/exception/AlreadyRentedException.html");
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(AgentNotAssignedWarehouseException.class)
    public ResponseEntity<Object> HandleAgentNotAssignedWarehouseException(HttpServletResponse response) {

        response.sendRedirect("http://localhost:8080/excetpion/AgentNotAssignedWarehouseException.html");
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }


    @SneakyThrows
    @ExceptionHandler(BadPathVariableException.class)
    public ResponseEntity<Object> HandleBadPathVariableException(HttpServletResponse response) {

        response.sendRedirect("http://localhost:8080/excetpion/exception/BadPathVariableException");
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }
    
}
