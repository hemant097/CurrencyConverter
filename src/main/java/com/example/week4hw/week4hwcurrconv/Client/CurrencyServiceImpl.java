package com.example.week4hw.week4hwcurrconv.Client;

import com.example.week4hw.week4hwcurrconv.DTO.StatusDto;
import com.example.week4hw.week4hwcurrconv.Entity.CurrencyRequest;
import com.example.week4hw.week4hwcurrconv.Entity.CurrencyResponse;
import com.example.week4hw.week4hwcurrconv.Entity.Status;
import com.example.week4hw.week4hwcurrconv.Exception.ResourceNotFound;
import com.example.week4hw.week4hwcurrconv.Mapper.StatusMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService{

    @Value("${apiKey}")
    private String API_KEY;


    private final RestClient restClient;
    private final StatusMapper statusMapper;
    Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    @Override
    public StatusDto getStatus() {

        log.trace("trying to getStatus of status of this api");

        ResponseEntity<Status> responseObject =
        restClient
                .get()
                .uri( uriBuilder -> uriBuilder
                        .path("/status")
                        .queryParam("apikey",API_KEY)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res)->{

                    log.error(new String(res.getBody().readAllBytes()));
                    throw new ResourceNotFound("unable to get status");
                })
                .toEntity(new ParameterizedTypeReference<>() {
                });


        Status status =  responseObject.getBody();

        log.debug("successfully received the status");
        log.trace("retrieved status of thos : {}",status);

         return statusMapper.toDto(status);
    }

    @Override
    public CurrencyResponse getCurrencyResponse(CurrencyRequest currencyRequest) {

        List<String> currencies = currencyRequest.getCurrencies();

        ResponseEntity<CurrencyResponse> responseObject =
                restClient
                        .get()
                        .uri( uriBuilder -> uriBuilder
                                .path("/latest")
                                .queryParam("apikey",API_KEY)
                                .queryParam("currencies",String.join(",",currencies))
                                .build())
                        .retrieve()
                        .toEntity(new ParameterizedTypeReference<>() {
                        });


        return responseObject.getBody();

    }

    @Override
    public Double convertFromAtoB(String fromCurrency, String toCurrency, Long units) {

        ResponseEntity<CurrencyResponse> responseObject =
                restClient
                        .get()
                        .uri( uriBuilder -> uriBuilder
                                .path("/latest")
                                .queryParam("apikey",API_KEY)
                                .queryParam("currencies",toCurrency)
                                .queryParam("base_currency",fromCurrency)
                                .build())
                        .retrieve()
                        .toEntity(new ParameterizedTypeReference<>() {
                        });


        if (responseObject.getBody() != null) {
            Map<String,Double> currencies = responseObject.getBody().getData();
            System.out.println(currencies);

            return currencies.get(toCurrency)*units;
        }
        else
            return 0.0;

    }
}
