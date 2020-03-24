package com.ebay.codechallenge.main.controller;

import com.ebay.codechallenge.main.annotations.JSONGetMapping;
import com.ebay.codechallenge.main.annotations.JSONPostMapping;
import com.ebay.codechallenge.main.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @JSONGetMapping(value = "/sellers")
    public String getSellers() {
        return sellerService.getSellers();
    }

    @JSONGetMapping(value = "/sellers/{id}")
    public String getSeller(@PathVariable long id) {
        return sellerService.getSeller(id);
    }

    @JSONPostMapping(value = "/addSeller")
    public String addSeller(@RequestBody String payload) {
        return sellerService.addSeller(payload);
    }
}
