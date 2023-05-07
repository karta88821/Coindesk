package com.danielliao.coindesk_api.currency;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyRestController {
    
    @Autowired
    private CurrencyService currencyService;
   
    @GetMapping("/{name}")
    public ResponseEntity<?> getCurrency(@PathVariable String name) {

        Map<String, Object> res = new HashMap<>();
        
        Optional<CurrencyRow> currency = currencyService.getCurrencyByName(name);

        if (!currency.isPresent()) {
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        CurrencyResponse response = new CurrencyResponse();
        response.setName(currency.get().getName());
        response.setChineseName(currency.get().getChineseName());
        response.setRateFloat(currency.get().getRateFloat());
        response.setUpdatedTime(format.format(currency.get().getUpdatedTime()));

        res.put("message", "Get currency " + name + " successful.");
        res.put("data", response);
        
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addCurrency(@RequestBody CurrencyAddBody body) {
        Map<String, Object> res = new HashMap<>();
        
        Optional<Integer> existedId = currencyService.getIdByName(body.getName());

        if (existedId.isPresent()) {
            res.put("message", "Currency " + body.getName() + " is already existed.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        Currency currency = new Currency();
        currency.setName(body.getName());
        currency.setChineseName(body.getChineseName());
        currency.setRateFloat(body.getRateFloat());
        currency.setUpdatedTime(new Date());

        Currency savedCurrency = currencyService.saveCurrency(currency);

        if (savedCurrency != null) {
            res.put("message", "Add Currency " + body.getName() + " successful.");
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } else {
            res.put("message", "Add Currency " + body.getName() + " failed.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @PatchMapping("/{name}")
    public ResponseEntity<?> updateCurrency(@PathVariable String name, @RequestBody CurrencyUpdateBody body) {
        Map<String, Object> res = new HashMap<>();
        
        Optional<Integer> existedId = currencyService.getIdByName(name);

        if (!existedId.isPresent()) {
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }

        currencyService.updateCurrencyByName(name, body.getRateFloat(), new Date());

        res.put("message", "Update currency " + name + " successful.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteCurrency(@PathVariable String name) {
        Map<String, Object> res = new HashMap<>();
        
        Optional<Integer> existedId = currencyService.getIdByName(name);

        if (!existedId.isPresent()) {
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }

        Integer deletedId = currencyService.deleteCurrency(name);

        if (deletedId != null) {
            res.put("message", "Delete currency " + name + " successful.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            res.put("message", "Delete currency " + name + " failed.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }
}
