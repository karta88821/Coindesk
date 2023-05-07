package com.danielliao.coindesk_api.coindesk;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielliao.coindesk_api.currency.Currency;
import com.danielliao.coindesk_api.currency.CurrencyService;

@RequestMapping("/api")
@RestController
public class CoindeskRestController {
    
    @Autowired
    private CoindeskService coindeskService;

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/callCoindesk")
    public ResponseEntity<?> getAllInitCurrencies() {
        CoindeskResponse response = coindeskService.getCurrencies();

        for (Currency c: response.getCurrencies()) {
 
            Optional<Integer> row = currencyService.getIdByName(c.getName());

            if (row.isPresent()) {
                currencyService.updateCurrencyByName(c.getName(), c.getRateFloat(), c.getUpdatedTime());
            } else {
                currencyService.saveCurrency(c);
            }
        }

        Map<String, Object> res = new HashMap<>();
        res.put("message", "Calling coindesk api success!");
        res.put("updatedTime", response.getUpdatedTime());
        
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
