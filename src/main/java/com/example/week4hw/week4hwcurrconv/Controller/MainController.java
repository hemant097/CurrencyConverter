package com.example.week4hw.week4hwcurrconv.Controller;

import com.example.week4hw.week4hwcurrconv.Client.CurrencyService;
import com.example.week4hw.week4hwcurrconv.DTO.StatusDto;
import com.example.week4hw.week4hwcurrconv.Entity.CurrencyRequest;
import com.example.week4hw.week4hwcurrconv.Entity.CurrencyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/currencyapi")
@RequiredArgsConstructor
public class MainController {

    private final CurrencyService currencyService;

    @GetMapping(path = "status")
    ResponseEntity<StatusDto> getStatus() {
        return ResponseEntity.ok(currencyService.getStatus());
    }

    @PostMapping(path = "rates")
    ResponseEntity<Map<String,Double>> getLatest(@Valid @RequestBody CurrencyRequest request) {
        return ResponseEntity.ok(currencyService
                .getCurrencyResponse(request).getData());
    }

    @PostMapping(path = "/convert")
    ResponseEntity<String> convertCurrencies(@RequestParam(name = "from") String fromCurrency,
                                             @RequestParam(name = "to", defaultValue = "INR", required = false) String toCurrency,
                                             @RequestParam(name = "units", defaultValue = "1", required = false) Long units) {

        Double converted = currencyService.convertFromAtoB(fromCurrency, toCurrency, units);

        return ResponseEntity.ok(units + " " + fromCurrency + " in " + toCurrency + " is " + converted.toString());
    }

}
