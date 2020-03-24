package com.ebay.codechallenge.main.repository;

import com.ebay.codechallenge.main.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    ArrayList<Item> findAll();
    Item findById(long id);
    void deleteById(long id);
}
