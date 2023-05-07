package com.danielliao.coindesk_api.coindesk;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.danielliao.coindesk_api.currency.Currency;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CoindeskService {

    private final String API_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

    public String getJsonString() {
        return WebClient.create()
            .get()
            .uri(API_URL)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    public CoindeskResponse getCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        CoindeskResponse response = new CoindeskResponse();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00");

        try {
            JsonNode root = mapper.readTree(new URL(API_URL));

            String dateString = root.get("time").get("updatedISO").asText();
            Date updatedDate = format.parse(dateString);
            response.setUpdatedTime(updatedDate);

            Iterator<Entry<String, JsonNode>> e = root.get("bpi").fields();
            while (e.hasNext()) {
                Currency currency = mapper.treeToValue(e.next().getValue(), Currency.class);
                currency.setChineseName(getChineseName(currency.getName()));
                currency.setUpdatedTime(updatedDate);

                currencies.add(currency);
            }

        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        response.setCurrencies(currencies);
        
        return response;
    }

    private String getChineseName(String name) {
        String res = "";
        if (name.equals("USD")) {
            res = "美金";
        } else if (name.equals("GBP")) {
            res = "英鎊";
        } else if (name.equals("EUR")) {
            res = "歐元";
        }
        return res;
    }
}
