package com.danielliao.coindesk_api.currency;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

@Service
@EnableJpaRepositories
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepo;

    public Optional<Integer> getIdByName(String name) {
        return currencyRepo.findIdByName(name);
    }

    public Optional<CurrencyRow> getCurrencyByName(String name) {
        List<CurrencyRow> res = currencyRepo.findByName(name);
        if (res.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(res.get(0));
        }
    }

    public void updateCurrencyByName(String name, Float rateFloat, Date updatedTime) {
        currencyRepo.updateCurrencyByName(name, rateFloat, updatedTime);
    }

    public Currency saveCurrency(Currency currency) {
        return currencyRepo.save(currency);
    }

    public Integer deleteCurrency(String name) {
        return currencyRepo.deleteByName(name);
    }
}
