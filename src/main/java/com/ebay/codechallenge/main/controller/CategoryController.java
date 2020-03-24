package com.ebay.codechallenge.main.controller;

import com.ebay.codechallenge.main.annotations.JSONGetMapping;
import com.ebay.codechallenge.main.annotations.JSONPostMapping;
import com.ebay.codechallenge.main.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @JSONGetMapping(value = "/categories")
    public String getCategories() {
        return categoryService.getCategories();
    }

    @JSONGetMapping(value = "/categories/{id}")
    public String getCategory(@PathVariable long id) {
        return categoryService.getCategory(id);
    }

    @JSONPostMapping(value = "/addCategory")
    public String addCategory(@RequestBody String payload) {
        return categoryService.addCategory(payload);
    }
}
