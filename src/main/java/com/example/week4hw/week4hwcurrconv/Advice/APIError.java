package com.example.week4hw.week4hwcurrconv.Advice;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class APIError {
    private HttpStatus httpStatus;
    private String message;
    private String errorRecordedTime;
}