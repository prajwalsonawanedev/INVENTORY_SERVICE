package com.poc.inventoryservice.repository;

import com.poc.inventoryservice.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByStockName(String stockName);

    @Query("SELECT s.stockQuantity FROM Stock s WHERE s.stockId = :stockId")
    Integer findStockQuantityByStockId(@Param("stockId") Long stockId);

}