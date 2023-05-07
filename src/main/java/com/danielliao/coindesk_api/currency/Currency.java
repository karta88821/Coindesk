package com.danielliao.coindesk_api.currency;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Currency")
@JsonIgnoreProperties(value = { "updatedTime" }, ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Currency {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true)
    @JsonProperty("code")
    private String name;

    @Column(unique = true)
    private String chineseName;

    @JsonProperty("rate_float")
    @Column(name = "rate_float")
    private Float rateFloat;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;
}
