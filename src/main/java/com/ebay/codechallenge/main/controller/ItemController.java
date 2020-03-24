package com.ebay.codechallenge.main.controller;

import com.ebay.codechallenge.main.annotations.JSONGetMapping;
import com.ebay.codechallenge.main.annotations.JSONPostMapping;
import com.ebay.codechallenge.main.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemController {
    @Autowired
    ItemService itemService;

    @JSONPostMapping(value = "/addItem")
    public String obtainInfo(@RequestBody String payload) {
        return itemService.addItem(payload);
    }

    @JSONGetMapping(value = "/items")
    public String getAllItems() {
        String allItems = itemService.getItems();
        return allItems;
    }

    @JSONGetMapping(value = "/items/{id}")
    public String getItem(@PathVariable long id) {
        return itemService.getItem(id);
    }

    @JSONGetMapping(value = "/itemEligible/{id}")
    public String getItemEligibility(@PathVariable long id) {
        return itemService.getItemEligibility(id);
    }
}
