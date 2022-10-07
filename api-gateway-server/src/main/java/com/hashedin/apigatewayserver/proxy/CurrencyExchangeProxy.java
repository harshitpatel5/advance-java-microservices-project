package com.hashedin.apigatewayserver.proxy;

import com.hashedin.apigatewayserver.models.CurrencyExchange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "currency-exchange-service")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/get/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to);

    @PostMapping("/currency-exchange/create")
    public CurrencyExchange createExchangeRate(@RequestBody CurrencyExchange currencyExchange);

    @DeleteMapping("/currency-exchange/delete/from/{from}/to/{to}")
    public String deleteByFromAndTo(@PathVariable String from, @PathVariable String to);

    @GetMapping("/currency-exchange/get-all")
    public List<CurrencyExchange> getAllCurrencyExchange();
}