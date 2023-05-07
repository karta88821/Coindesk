package com.danielliao.coindesk_api.coindesk;

import java.util.Date;
import java.util.List;

import com.danielliao.coindesk_api.currency.Currency;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CoindeskResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Date updatedTime;

    private List<Currency> currencies;
}
