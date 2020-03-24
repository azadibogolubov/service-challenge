package com.ebay.codechallenge.main;

import com.ebay.codechallenge.main.model.Category;
import com.ebay.codechallenge.main.model.MinimumPrice;
import com.ebay.codechallenge.main.model.Seller;
import com.ebay.codechallenge.main.repository.CategoryRepository;
import com.ebay.codechallenge.main.repository.MinimumPriceRepository;
import com.ebay.codechallenge.main.repository.SellerRepository;
import com.ebay.codechallenge.main.service.ConfigurationService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests {
    @Autowired
    ConfigurationService configurationService;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    MinimumPriceRepository minimumPriceRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void testUpdateConfigurationAddSeller() {
        JSONObject payloadObj = new JSONObject();
        payloadObj.put("addSeller", "1");
        configurationService.updateConfigurationValues(payloadObj.toString());
        Seller seller = sellerRepository.findBySellerId(1);
        Assert.assertTrue(seller.isEnrolled());
    }

    @Test
    public void testUpdateConfigurationRemoveSeller() {
        JSONObject payloadObj = new JSONObject();
        payloadObj.put("removeSeller", "1");
        configurationService.updateConfigurationValues(payloadObj.toString());
        Seller seller = sellerRepository.findBySellerId(1);
        Assert.assertFalse(seller.isEnrolled());
    }

    @Test
    public void testUpdateMinimumPrice() {
        JSONObject payloadObj = new JSONObject();
        JSONArray payloadArr = new JSONArray();
        JSONObject minPriceObj = new JSONObject();
        minPriceObj.put("categoryId", "1");
        minPriceObj.put("minimumPrice", "499.99");
        payloadArr.put(minPriceObj);
        payloadObj.put("updateMinimumPrice", payloadArr);

        configurationService.updateConfigurationValues(payloadObj.toString());
        MinimumPrice minimumPrice = minimumPriceRepository.findByCategoryId(1);
        Assert.assertEquals(499.99, minimumPrice.getMinimumPrice(), 0.01);
    }

    @Test
    public void testUpdateConfigurationAddCategory() {
        JSONObject payloadObj = new JSONObject();
        payloadObj.put("addCategory", "3");
        configurationService.updateConfigurationValues(payloadObj.toString());
        Category category = categoryRepository.findByCategoryId(1);
        Assert.assertTrue(category.isPreapproved());
    }

    @Test
    public void testUpdateConfigurationRemoveCategory() {
        JSONObject payloadObj = new JSONObject();
        payloadObj.put("removeCategory", "1");
        configurationService.updateConfigurationValues(payloadObj.toString());
        Category category = categoryRepository.findByCategoryId(1);
        Assert.assertFalse(category.isPreapproved());
    }
}
