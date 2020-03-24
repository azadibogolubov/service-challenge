package com.ebay.codechallenge.main;

import com.ebay.codechallenge.main.repository.SellerRepository;
import com.ebay.codechallenge.main.service.ConfigurationService;
import com.ebay.codechallenge.main.service.ItemService;
import com.ebay.codechallenge.main.service.SellerService;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Sampling of endpoint and unit tests to validate the presence of elements in the JSON responses,
 * as well as to exercise some functionality.
 * In an actual production application, I would have unit tests for everything, but ran
 * out of time.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTests {
    @Autowired
    SellerService sellerService;
    @Autowired
    ItemService itemService;
    @Autowired
    ConfigurationService configurationService;
    @Autowired
    SellerRepository sellerRepository;

    // Tests to verify structure of response.
    @Test
    public void testGetAllSellers() {
        JSONObject response = new JSONObject(sellerService.getSellers());
        Assert.assertTrue(response.has("content"));
        JSONObject firstElementOfContentArray = response.getJSONArray("content").getJSONObject(0);
        Assert.assertTrue(firstElementOfContentArray.has("firstName"));
        Assert.assertTrue(firstElementOfContentArray.has("lastName"));
        Assert.assertTrue(firstElementOfContentArray.has("enrolled"));
    }

    @Test
    public void testGetOneSeller() {
        JSONObject responseObj = new JSONObject(sellerService.getSeller(1));
        Assert.assertTrue(responseObj.has("firstName"));
        Assert.assertTrue(responseObj.has("lastName"));
        Assert.assertTrue(responseObj.has("enrolled"));
    }

    // Next two tests are to exercise the actual assignment of checking eligibility of an item.
    @Test
    public void testItemEligibilityForEligibleItem() {
        JSONObject responseObj = new JSONObject(itemService.getItemEligibility(1));
        boolean eligible = responseObj.getBoolean("eligible");
        Assert.assertTrue(eligible);
    }

    @Test
    public void testItemEligibilityForInEligibleItem() {
        JSONObject responseObj = new JSONObject(itemService.getItemEligibility(2));
        boolean eligible = responseObj.getBoolean("eligible");
        Assert.assertFalse(eligible);
    }
}
