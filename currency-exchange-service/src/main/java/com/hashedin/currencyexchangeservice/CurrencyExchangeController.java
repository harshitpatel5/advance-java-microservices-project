package com.hashedin.currencyexchangeservice;


import com.hashedin.currencyexchangeservice.models.CurrencyExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/currency-exchange")
public class CurrencyExchangeController {

    @Autowired
    CurrencyExchangeService service;

    @GetMapping("/test")
    public String check(){
        return "okay";
    }

    @GetMapping("/get/from/{from}/to/{to}")
    public CurrencyExchange getByFromAndTo(@PathVariable String from, @PathVariable String to) throws CustomException {
        return service.findByFromAndTo(from, to);
    }

    @GetMapping("/get-all")
    public List<CurrencyExchange> getAllCurrencyExchange(){
        return service.getAllCurrencyExchange();
    }

    @PostMapping("/create")
    public CurrencyExchange create(@RequestBody CurrencyExchange currencyExchange) throws CustomException {
        return service.create(currencyExchange);
    }

    @PutMapping("/update")
    public CurrencyExchange update(@RequestBody CurrencyExchange currencyExchange) throws CustomException {
        return service.update(currencyExchange);
    }

    @DeleteMapping("delete/from/{from}/to/{to}")
    public String deleteByFromAndTo(@PathVariable String from, @PathVariable String to) throws CustomException {
        service.deleteByFromAndTo(from, to);
        return "Successfully Deleted";
    }
}
