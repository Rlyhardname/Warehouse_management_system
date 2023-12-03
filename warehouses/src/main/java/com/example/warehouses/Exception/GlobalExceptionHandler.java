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

        return new ResponseEntity<>("password doesn't match user credentials", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(UserNotExististingException.class)
    public ResponseEntity<Object> HandleUserNotExistingException(HttpServletResponse response) {

        return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(ClientAlreadyRegisteredException.class)
    public ResponseEntity<Object> HandleClientAlreadyRegisteredException(HttpServletResponse response) {


        return new ResponseEntity<>("Client is already registered", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(WarehouseAlreadyExistsException.class)
    public ResponseEntity<Object> HandleWarehouseAlreadyExistsException() {

        return new ResponseEntity<>("Warehouse with such a name already exists", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(WarehouseNotExistingException.class)
    public ResponseEntity<Object> HandleWarehouseNotExistingException() {

        return new ResponseEntity<>("Warehouse with this this id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(OwnerDoesntOwnAnyWarehouseException.class)
    public ResponseEntity<Object> HandleOwnerDoesntOwnAnyWarehouseException() {

        return new ResponseEntity<>("This owner doesn't own any warehouse", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(AgentHasNoContractsException.class)
    public ResponseEntity<Object> HandleAgentHasNoContractsException(String s) {



        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(AlreadyRentedException.class)
    public ResponseEntity<Object> HandleAlreadyRentedException() {

        return new ResponseEntity<>("Warehouse is already rented out", HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(AgentNotAssignedWarehouseException.class)
    public ResponseEntity<Object> HandleAgentNotAssignedWarehouseException() {

        return new ResponseEntity<>("Agent not assigned to warehouse", HttpStatus.NOT_FOUND);
    }


    @SneakyThrows
    @ExceptionHandler(BadPathVariableException.class)
    public ResponseEntity<Object> HandleBadPathVariableException() {


        return new ResponseEntity<>("URL Path variable value doesn't exist in DB", HttpStatus.NOT_FOUND);
    }
    
}
