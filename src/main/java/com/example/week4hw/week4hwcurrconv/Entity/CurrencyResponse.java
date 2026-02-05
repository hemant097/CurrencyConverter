package com.example.week4hw.week4hwcurrconv.Entity;

import lombok.Data;

import java.util.Map;

@Data
public class CurrencyResponse {

    private Map<String, Double> data;
}
