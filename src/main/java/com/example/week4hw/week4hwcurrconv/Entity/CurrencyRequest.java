package com.example.week4hw.week4hwcurrconv.Entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CurrencyRequest {

    @NotNull
    @Size(min = 2,max = 10, message = "size can be between 2-10 inclusive")
    private List< @Pattern (regexp = "^[A-Z]{3}$" ,message = "Enter only 3 character currency codes in capital letters")
            String> currencies;
}
