package com.hashedin.currencyexchangeservice;

import com.hashedin.currencyexchangeservice.models.CurrencyExchange;
import com.hashedin.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyExchangeServiceTests {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @MockBean
    private CurrencyExchangeRepository repository;

    @Test
    public void getAllCurrencyExchangeTest(){
        List<CurrencyExchange> currencyExchanges = new ArrayList<>();
        currencyExchanges.add(new CurrencyExchange());
        currencyExchanges.add(new CurrencyExchange());
        when(repository.findAll()).thenReturn(currencyExchanges);
        assertEquals(2, currencyExchangeService.getAllCurrencyExchange().size());
    }

    @Test
    public void findByFromAndToTest() throws CustomException {
        CurrencyExchange currencyExchange = new CurrencyExchange();
        currencyExchange.setId(1l);
        currencyExchange.setFrom("USD");
        currencyExchange.setTo("INR");
        when(repository.findByFromAndTo("USD", "INR")).thenReturn(currencyExchange);
        assertEquals(1l, currencyExchangeService.findByFromAndTo("USD", "INR").getId());
    }

    @Test
    public void deleteByFromAndTo() throws CustomException {
        CurrencyExchange currencyExchange = new CurrencyExchange();
        currencyExchange.setId(1l);
        currencyExchange.setFrom("USD");
        currencyExchange.setTo("INR");
        when(repository.findByFromAndTo("USD", "INR")).thenReturn(currencyExchange);
        currencyExchangeService.deleteByFromAndTo("USD", "INR");
        verify(repository, times(1)).delete(currencyExchange);
    }
}
