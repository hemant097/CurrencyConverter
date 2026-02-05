package com.example.week4hw.week4hwcurrconv.Advice;

import com.example.week4hw.week4hwcurrconv.Exception.UnknownAPIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UnknownAPIException.class)
    public ResponseEntity<APIError> resourceNotFound(UnknownAPIException rnf) {

        String currDT = giveCurrentDateTime();

        APIError apiError = APIError.builder()
                .message(rnf.getLocalizedMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorRecordedTime(currDT)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> internalServerError(MethodArgumentNotValidException manve) {

        //getting all the binding errors from the exception and converting it to List<String> using stream
        List<String> errorList = manve.getBindingResult()
                .getAllErrors()
                .stream().map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        String errors = String.join(", ", errorList);

        String dateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm"));
        //using lombok builder
        APIError apiError = APIError.builder()
                .message("Input validation failed ," + errors)
                .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                .errorRecordedTime(dateAndTime)
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
    }

    //handles every other exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> internalServerError(Exception exception) {

        String currDT = giveCurrentDateTime();

        APIError apiError = APIError.builder()
                .message(exception.getMessage())
                .errorRecordedTime(currDT)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //returns current date and time when called
    private String giveCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm"));
    }
}