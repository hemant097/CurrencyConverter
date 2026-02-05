package com.example.week4hw.week4hwcurrconv.Client;

import com.example.week4hw.week4hwcurrconv.DTO.StatusDto;
import com.example.week4hw.week4hwcurrconv.Entity.CurrencyRequest;
import com.example.week4hw.week4hwcurrconv.Entity.CurrencyResponse;

public interface CurrencyService {

    StatusDto getStatus();

    CurrencyResponse getCurrencyResponse(CurrencyRequest currencyRequest);

    Double convertFromAtoB(String fromCurrency, String toCurrency, Long units);
}
