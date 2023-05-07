package com.danielliao.coindesk_api.currency;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    @Query("select c.id from Currency c where c.name = :name")
    public Optional<Integer> findIdByName(@Param("name") String name);

    public List<CurrencyRow> findByName(String name);

    // data jpa 中每個操作都是一個transaction
    // 如果沒有聲明 @Transactional 的話，readOnly預設為 true
    // 在增刪改時，需要聲明@Transactional，將 readOnly 設為 false，才能進行操作
    @Transactional 
    // 告訴 data jpa 這是一個 update/delete 的動作
    @Modifying 
    @Query("update Currency c set c.rateFloat = :rateFloat, c.updatedTime = :updatedTime where c.name = :name")
    public void updateCurrencyByName(@Param("name") String name, @Param("rateFloat") Float rateFloat, @Param("updatedTime") Date updatedTime);

    @Transactional
    @Modifying
    public Integer deleteByName(String name);
}
