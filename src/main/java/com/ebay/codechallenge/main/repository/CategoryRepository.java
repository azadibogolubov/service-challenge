package com.ebay.codechallenge.main.repository;

import com.ebay.codechallenge.main.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    ArrayList<Category> findAll();
    Category findByCategoryId(long categoryId);
}
