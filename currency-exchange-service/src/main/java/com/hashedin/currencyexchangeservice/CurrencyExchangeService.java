package com.hashedin.currencyexchangeservice;

import com.hashedin.currencyexchangeservice.models.CurrencyExchange;
import com.hashedin.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyExchangeService {

    @Autowired
    CurrencyExchangeRepository repository;

    public List<CurrencyExchange> getAllCurrencyExchange(){
        return repository.findAll();
    }

    public CurrencyExchange findByFromAndTo(String from, String to) throws CustomException {
        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
        if(currencyExchange == null){
            throw new CustomException("No CurrencyExchange found by from '"+from+"' and to '"+to+"'");
        }
        return currencyExchange;
    }

    public CurrencyExchange create(CurrencyExchange currencyExchange) throws CustomException {
        CurrencyExchange createdCurrencyExchange = repository.save(currencyExchange);
        if(createdCurrencyExchange == null){
            throw new CustomException("Unable to create CurrencyExchange");
        }
        return createdCurrencyExchange;
    }

    public CurrencyExchange update(CurrencyExchange currencyExchange) throws CustomException {
        if(currencyExchange.getId() == null){
            throw new CustomException("Unable to update CurrencyExchange without ID");
        }
        Optional<CurrencyExchange> currencyExchangeOptional = repository.findById(currencyExchange.getId());
        if(!currencyExchangeOptional.isPresent()){
            throw new CustomException("Unable to fetch CurrencyExchange by ID - "+ currencyExchange.getId());
        }
        CurrencyExchange currencyExchangeFetched = currencyExchangeOptional.get();
        currencyExchangeFetched.setFrom(currencyExchange.getFrom());
        currencyExchangeFetched.setConversionMultiple(currencyExchange.getConversionMultiple());
        currencyExchangeFetched.setTo(currencyExchange.getTo());
        CurrencyExchange savedCurrencyExchange = repository.save(currencyExchangeFetched);
        if(savedCurrencyExchange == null){
            throw new CustomException("Unable to create CurrencyExchange");
        } else return savedCurrencyExchange;
    }

    public String deleteByFromAndTo(String from, String to) throws CustomException {
        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
        if(currencyExchange == null){
            throw new CustomException("No CurrencyExchange found by from '"+from+"' and to '"+to+"'");
        }
        try{
            repository.delete(currencyExchange);
        } catch(Exception exception){
            throw new CustomException("Unable to delete CurrencyExchange");
        }
        return "CurrencyExchange Successfully deleted";
    }
}
