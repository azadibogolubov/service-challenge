package com.ebay.codechallenge.main.controller;

import com.ebay.codechallenge.main.annotations.JSONGetMapping;
import com.ebay.codechallenge.main.annotations.JSONPostMapping;
import com.ebay.codechallenge.main.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationController {
    @Autowired
    ConfigurationService configurationService;

    @JSONGetMapping(value = "/configuration")
    public String getConfigurationValues() {
        return configurationService.getConfigurationValues();
    }

    @JSONPostMapping(value = "/updateConfiguration")
    public String updateConfigurationValues(@RequestBody String payload) {
        return configurationService.updateConfigurationValues(payload);
    }
}
