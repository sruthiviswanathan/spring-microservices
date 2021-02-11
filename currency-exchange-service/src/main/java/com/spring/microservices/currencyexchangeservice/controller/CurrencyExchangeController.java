package com.spring.microservices.currencyexchangeservice.controller;

import com.spring.microservices.currencyexchangeservice.bean.CurrencyExchange;
import com.spring.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @GetMapping("/currency-exchange/from/{fromValue}/to/{toValue}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String fromValue, @PathVariable String toValue) {
        CurrencyExchange value = currencyExchangeRepository.findByFromAndTo(fromValue, toValue);
        if (value == null) {
            throw new RuntimeException("Unable to find currency exchange data");
        }
        value.setEnvironment(environment.getProperty("local.server.port"));
        return value;
    }
}
