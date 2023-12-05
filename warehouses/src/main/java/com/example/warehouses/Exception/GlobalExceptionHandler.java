package com.example.warehouses.Exception;

import com.example.warehouses.Exception.Client.*;
import com.example.warehouses.Exception.Login.WrongPasswordException;
import com.example.warehouses.Exception.Warehouse.AlreadyRentedException;
import com.example.warehouses.Exception.Warehouse.WarehouseAlreadyExistsException;
import com.example.warehouses.Exception.Warehouse.WarehouseNotExistingException;
import jakarta.servlet.ServletResponse;
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
    public ResponseEntity<Object> handleWrongPasswordException() {

        return new ResponseEntity<>("Invalid Username or Password. Please try again...", HttpStatus.UNAUTHORIZED);
    }

    @SneakyThrows
    @ExceptionHandler(UserNotExististingException.class)
    public ResponseEntity<Object> HandleUserNotExistingException() {

        return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
    }

    @SneakyThrows
    @ExceptionHandler(ClientAlreadyRegisteredException.class)
    public ResponseEntity<Object> HandleClientAlreadyRegisteredException() {

        // TODO log request and return something general.

        return new ResponseEntity<>("Registration failed. Please check your information and try again.", HttpStatus.UNAUTHORIZED);
    }

    // TODO WarehouseAlreadyExistsException and WarehouseNotExistingException potentially overlapping
    @SneakyThrows
    @ExceptionHandler(WarehouseAlreadyExistsException.class)
    public ResponseEntity<Object> HandleWarehouseAlreadyExistsException() {

        return new ResponseEntity<>("Warehouse with such a name already exists", HttpStatus.UNAUTHORIZED);
    }

    @SneakyThrows
    @ExceptionHandler(WarehouseNotExistingException.class)
    public ResponseEntity<Object> HandleWarehouseNotExistingException() {

        return new ResponseEntity<>("Warehouse doesn't exist!", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(OwnerDoesntOwnAnyWarehouseException.class)
    public ResponseEntity<Object> HandleOwnerDoesntOwnAnyWarehouseException() {

        return new ResponseEntity<>("This owner doesn't own any warehouse yet!", HttpStatus.NOT_FOUND);
    }


    @SneakyThrows
    @ExceptionHandler(AlreadyRentedException.class)
    public ResponseEntity<Object> HandleAlreadyRentedException() {

        return new ResponseEntity<>("Warehouse is already rented out", HttpStatus.CONFLICT);
    }

    @SneakyThrows
    @ExceptionHandler(AgentNotAssignedWarehouseException.class)
    public ResponseEntity<Object> HandleAgentNotAssignedWarehouseException() {

        return new ResponseEntity<>("Agent not assigned to warehouse", HttpStatus.NOT_FOUND);
    }


    @SneakyThrows
    @ExceptionHandler(BadPathVariableException.class)
    public ResponseEntity<Object> HandleBadPathVariableException() {


        return new ResponseEntity<>("Bad path variable!", HttpStatus.BAD_REQUEST);
    }
    
}
