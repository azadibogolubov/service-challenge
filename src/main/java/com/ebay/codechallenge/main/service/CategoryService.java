package com.ebay.codechallenge.main.service;

import com.ebay.codechallenge.main.model.Category;
import com.ebay.codechallenge.main.repository.CategoryRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.ebay.codechallenge.main.utils.Utils.addError;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, JdbcTemplate jdbcTemplate) {
        this.categoryRepository = categoryRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getCategories() {
        ArrayList<Category> allCategories = categoryRepository.findAll();
        JSONObject returnObj = new JSONObject();
        JSONArray returnArr = new JSONArray();

        for (Category c: allCategories) {
            JSONObject currObj = new JSONObject();
            currObj.put("id", c.getCategoryId());
            currObj.put("name", c.getCategoryName());
            currObj.put("preapproved", c.isPreapproved());
            returnArr.put(currObj);
        }

        returnObj.put("content", returnArr);
        return returnObj.toString();
    }

    public String getCategory(long id) {
        JSONObject returnObj = new JSONObject();

        Category c = categoryRepository.findByCategoryId(id);

        returnObj.put("id", c.getCategoryId());
        returnObj.put("name", c.getCategoryName());
        returnObj.put("preapproved", c.isPreapproved());

        return returnObj.toString();
    }

    public String addCategory(String payload) {
        JSONObject payloadObj = new JSONObject(payload);
        JSONObject returnObj = new JSONObject();
        JSONArray errorArr = new JSONArray();

        if (!payloadObj.has("categoryName")) {
            addError(errorArr, "categoryName is a required field");
        }
        if (!payloadObj.has("preapproved")) {
            addError(errorArr, "preapproved is a required field");
        }
        if (errorArr.length() != 0) {
            returnObj.put("errorList", errorArr);
        }

        if (returnObj.length() == 0) {
            Category category = new Category();
            category.setCategoryName(payloadObj.getString("categoryName"));
            category.setPreapproved(Boolean.parseBoolean(payloadObj.getString("preapproved")));
            categoryRepository.save(category);

            returnObj.put("status", "successfully added category");
        }

        return returnObj.toString();
    }
}
