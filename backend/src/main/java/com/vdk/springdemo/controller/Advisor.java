package com.vdk.springdemo.controller;

import com.vdk.springdemo.controller.exception.InvaliUserOrPasswordException;
import com.vdk.springdemo.controller.exception.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class Advisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAEE() {
        return ResponseEntity.badRequest().body("This user already exists");
    }

    @ExceptionHandler(InvaliUserOrPasswordException.class)
    public ResponseEntity<?> handleIUOPE() {
        return ResponseEntity.badRequest().body("Invalid user or password was sended");
    }

}
