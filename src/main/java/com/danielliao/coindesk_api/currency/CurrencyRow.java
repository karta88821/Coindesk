package com.danielliao.coindesk_api.currency;

import java.util.Date;

public interface CurrencyRow {
    public String getName();
    public String getChineseName();
    public Float getRateFloat();
    public Date getUpdatedTime();
}
