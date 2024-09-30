package com.stayease.advisor;

import com.stayease.dto.ExceptionDto;
import com.stayease.exceptions.AlreadyExistException;
import com.stayease.exceptions.BadRequestException;
import com.stayease.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAdvisor {


    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ExceptionDto<String>> handleAlreadyExistException(
            AlreadyExistException exception
    ){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionDto<>(exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDto<String>> handleConflictException(
            BadRequestException exception
    ){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDto<>(exception.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto<String>> handleNotFoundException(
            NotFoundException exception
    ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto<>(exception.getMessage()));
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionDto<String>> handleMethodArgumentException(
            MethodArgumentTypeMismatchException ex,
            WebRequest webRequest

    ){
        return  new ResponseEntity<>(new ExceptionDto<>(ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInValidMethodArgException(
            MethodArgumentNotValidException ex,
            WebRequest webRequest
    ) {

        Map<String, String> err = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(
                (error) -> {
                    String field = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    err.put(field, message);
                }
        );
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionDto<String>> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex){
        return  new ResponseEntity<>(new ExceptionDto<>("Method not allowed"),HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto<String>> handleException(RuntimeException exception){
        return new ResponseEntity<>(new ExceptionDto<>(exception.getMessage()),HttpStatus.BAD_REQUEST);
    }

}
