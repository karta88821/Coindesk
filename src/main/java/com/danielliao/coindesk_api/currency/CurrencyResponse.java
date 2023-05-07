package com.danielliao.coindesk_api.currency;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyResponse {
    private String name;
    private String chineseName;
    private Float rateFloat;
    private String updatedTime;
}
