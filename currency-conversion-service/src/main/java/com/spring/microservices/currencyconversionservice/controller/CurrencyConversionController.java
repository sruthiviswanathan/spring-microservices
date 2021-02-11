package com.spring.microservices.currencyconversionservice.controller;

import com.spring.microservices.currencyconversionservice.bean.CurrencyConversion;
import com.spring.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping("/currency-conversion/from/{fromValue}/to/{toValue}/quantity/{qty}")
    public CurrencyConversion calculateCurrency(@PathVariable String fromValue, @PathVariable String toValue,
                                                @PathVariable BigDecimal qty) {

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("fromValue", fromValue);
        uriVariables.put("toValue", toValue);
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{fromValue}/to/{toValue}", CurrencyConversion.class, uriVariables);
        CurrencyConversion currencyConversionValue = responseEntity.getBody();
        BigDecimal calculatedAmount = qty.multiply(currencyConversionValue.getConversionMultiple());

       return new CurrencyConversion(currencyConversionValue.getId(), fromValue, toValue, currencyConversionValue.getConversionMultiple(), qty, calculatedAmount, currencyConversionValue.getEnvironment() + " rest template");
    }

    @GetMapping("/currency-conversion-feign/from/{fromValue}/to/{toValue}/quantity/{qty}")
    public CurrencyConversion calculateCurrencyFeign(@PathVariable String fromValue, @PathVariable String toValue,
                                                @PathVariable BigDecimal qty) {

        CurrencyConversion currencyConversionValue = proxy.calculateCurrency(fromValue, toValue);
        BigDecimal calculatedAmount = qty.multiply(currencyConversionValue.getConversionMultiple());

        return new CurrencyConversion(currencyConversionValue.getId(), fromValue, toValue, currencyConversionValue.getConversionMultiple(), qty, calculatedAmount, currencyConversionValue.getEnvironment() + " feign");
    }
}
