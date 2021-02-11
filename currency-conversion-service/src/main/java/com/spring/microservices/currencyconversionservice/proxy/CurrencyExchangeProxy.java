package com.spring.microservices.currencyconversionservice.proxy;

import com.spring.microservices.currencyconversionservice.bean.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "currency-exchange" ,url="localhost:8000")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{fromValue}/to/{toValue}")
    public CurrencyConversion calculateCurrency(@PathVariable String fromValue, @PathVariable String toValue);
}
