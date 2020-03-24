package com.ebay.codechallenge.main.repository;

import com.ebay.codechallenge.main.model.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SellerRepository extends CrudRepository<Seller, Long> {
    ArrayList<Seller> findAll();
    Seller findBySellerId(long id);
}
