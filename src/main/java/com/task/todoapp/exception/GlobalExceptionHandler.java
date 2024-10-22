package com.task.todoapp.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.task.todoapp.controller.TaskController;
import com.task.todoapp.dao.TaskStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Arrays;

@ControllerAdvice(assignableTypes = TaskController.class)
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handlePathVariableMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid  path variable: " + ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause=ex.getCause();
        if(cause instanceof InvalidFormatException formatException) {
            Class<?> targetType = formatException.getTargetType();
            System.out.println(targetType);
            if (targetType.isEnum() && targetType.isAssignableFrom(TaskStatus.class)) {
                TaskStatus[] taskStatuses = TaskStatus.values();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Format for status" + Arrays.toString(taskStatuses));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }






}
