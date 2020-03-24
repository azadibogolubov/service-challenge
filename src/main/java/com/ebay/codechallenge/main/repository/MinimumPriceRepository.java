package com.ebay.codechallenge.main.repository;

import com.ebay.codechallenge.main.model.MinimumPrice;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface MinimumPriceRepository extends CrudRepository<MinimumPrice, Long> {
    ArrayList<MinimumPrice> findAll();
    MinimumPrice findByCategoryId(long categoryId);
}
