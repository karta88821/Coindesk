package com.danielliao.coindesk_api.currency;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyAddBody {
    private String name;
    private String chineseName;
    private Float rateFloat;
}
