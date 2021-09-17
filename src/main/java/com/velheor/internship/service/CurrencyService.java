package com.velheor.internship.service;

import com.velheor.internship.clients.CurrencyClient;
import com.velheor.internship.models.Currency;
import com.velheor.internship.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyClient currencyClient;

    public void updateCurrentCurrenciesRates() {
        List<Currency> currencies = currencyClient.getAllCurrenciesFromUrls();
        currencies.forEach(currency -> currency.getRates()
                .forEach(rate -> rate.setCurrency(currency)));
        currencyRepository.saveAll(currencies);
    }

    public Currency findLatestCurrency(String name) {
        return currencyRepository.findFirstByNameOrderByUpdatedDesc(name).orElseThrow(
                () -> new EntityNotFoundException("Currency with " + name + "not existed"));
    }
}