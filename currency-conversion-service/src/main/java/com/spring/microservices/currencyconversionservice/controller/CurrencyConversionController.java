package com.spring.microservices.currencyconversionservice.controller;

import com.spring.microservices.currencyconversionservice.bean.CurrencyConversion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @GetMapping("/currency-conversion/from/{fromValue}/to/{toValue}/quantity/{qty}")
    public CurrencyConversion calculateCurrency(@PathVariable String fromValue, @PathVariable String toValue,
                                                @PathVariable BigDecimal qty) {

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("fromValue", fromValue);
        uriVariables.put("toValue", toValue);
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{fromValue}/to/{toValue}", CurrencyConversion.class, uriVariables);
        CurrencyConversion currencyConversionValue = responseEntity.getBody();
        BigDecimal calculatedAmount = qty.multiply(currencyConversionValue.getConversionMultiple());

       return new CurrencyConversion(currencyConversionValue.getId(), fromValue, toValue, currencyConversionValue.getConversionMultiple(), qty, calculatedAmount, currencyConversionValue.getEnvironment());
    }
}
