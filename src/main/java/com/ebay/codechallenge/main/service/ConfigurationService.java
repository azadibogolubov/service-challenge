package com.ebay.codechallenge.main.service;

import com.ebay.codechallenge.main.model.Category;
import com.ebay.codechallenge.main.model.MinimumPrice;
import com.ebay.codechallenge.main.model.Seller;
import com.ebay.codechallenge.main.repository.CategoryRepository;
import com.ebay.codechallenge.main.repository.MinimumPriceRepository;
import com.ebay.codechallenge.main.repository.SellerRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ConfigurationService {
    private final MinimumPriceRepository minimumPriceRepository;
    private final CategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;

    @Autowired
    public ConfigurationService(MinimumPriceRepository minimumPriceRepository, CategoryRepository categoryRepository, SellerRepository sellerRepository) {
        this.minimumPriceRepository = minimumPriceRepository;
        this.categoryRepository = categoryRepository;
        this.sellerRepository = sellerRepository;
    }

    /**
     * Obtain the configuration values as they can be displayed on a configuration page.
     * @return The list of configuration values in JSON format.
     */
    public String getConfigurationValues() {
        ArrayList<MinimumPrice> allMinimumPrices = minimumPriceRepository.findAll();
        ArrayList<Category> allCategories = categoryRepository.findAll();
        ArrayList<Seller> allSellers = sellerRepository.findAll();

        JSONObject returnObj = new JSONObject();
        JSONArray enrolledSellerArr = new JSONArray();
        JSONArray minimumPriceArr = new JSONArray();
        JSONArray preapprovedCategoryArr = new JSONArray();

        JSONObject currObj;

        // Iterate the sellers, enrolled or not, and display back in array format.
        for (Seller s: allSellers) {
            currObj = new JSONObject();
            currObj.put("sellerId", s.getSellerId());
            currObj.put("firstName", s.getFirstName());
            currObj.put("lastName", s.getLastName());
            currObj.put("enrolled", s.isEnrolled());
            enrolledSellerArr.put(currObj);
        }

        // Iterate the minimum prices with corresponding categories, and display back in array format.
        for (MinimumPrice mp: allMinimumPrices) {
            currObj = new JSONObject();
            currObj.put("minimumPrice", mp.getMinimumPrice());
            currObj.put("categoryId", mp.getCategoryId());
            minimumPriceArr.put(currObj);
        }

        // Iterate the preapproved categories, approved or not, and display back in array format.
        for (Category c: allCategories) {
            currObj = new JSONObject();
            currObj.put("categoryId", c.getCategoryId());
            currObj.put("preapproved", c.isPreapproved());
            preapprovedCategoryArr.put(currObj);
        }

        // Add the arrays to the object that will be returned to display in JSON format.
        returnObj.put("enrolledSellers", enrolledSellerArr);
        returnObj.put("minimumPrices", minimumPriceArr);
        returnObj.put("preapprovedCategories", preapprovedCategoryArr);

        return returnObj.toString();
    }

    /**
     * A larger function that will multi-plex the payload and do the valid operation(s) required,
     * based on one or more options chosen from a configuration page on the event that the update/save
     * button is pressed and the POST request sent.
     * @param payload The payload of the request with the valid updates, additions, or removals request.
     * @return A success or error message.
     */
    public String updateConfigurationValues(String payload) {
        JSONObject payloadObj = new JSONObject(payload);
        JSONObject returnObj = new JSONObject();

        // By wrapping in a getString call, we ensure that we are passing an int or string without error.
        if (payloadObj.has("addSeller")) {
            addSeller(Integer.parseInt(payloadObj.getString("addSeller")));
        }

        // By wrapping in a getString call, we ensure that we are passing an int or string without error.
        if (payloadObj.has("removeSeller")) {
            long id = Integer.parseInt(payloadObj.getString("removeSeller"));
            removeSeller(id);
        }

        // By wrapping in a getArray call, we ensure that we getting the array that we want,
        // thereby being able to parse it and get the values we need.
        if (payloadObj.has("updateMinimumPrice")) {
            JSONArray minPriceArr = payloadObj.getJSONArray("updateMinimumPrice");
            JSONObject minPriceObj = minPriceArr.getJSONObject(0);
            long id = Long.parseLong(minPriceObj.getString("categoryId"));
            float value = Float.parseFloat(minPriceObj.getString("minimumPrice"));
            updateMinimumPrice(id, value);
        }

        if (payloadObj.has("addCategory")) {
            addCategory(Long.parseLong(payloadObj.getString("addCategory")));
        }

        // By wrapping in a getString call, we ensure that we are passing an int or string without error.
        if (payloadObj.has("removeCategory")) {
            removeCategory(Long.parseLong(payloadObj.getString("removeCategory")));
        }

        returnObj.put("status", "success");
        return returnObj.toString();
    }

    /**
     * Will add a new, or update an existing seller's enrollment status.
     * @param sellerId The id of the seller.
     */
    public void addSeller(int sellerId) {
        Seller seller = sellerRepository.findBySellerId(sellerId);
        seller.setEnrolled(true);
        sellerRepository.save(seller);
    }

    /**
     * Will remove a seller, while keeping the record in the DB, by setting to false.
     * @param sellerId The id of the seller.
     */
    public void removeSeller(long sellerId) {
        Seller seller = sellerRepository.findBySellerId(sellerId);
        seller.setEnrolled(false);
        sellerRepository.save(seller);
    }

    /**
     * Will update the minimum price for a given category.
     * @param id The category id to update.
     * @param value The new minimum price.
     */
    public void updateMinimumPrice(long id, float value) {
        MinimumPrice minimumPrice = minimumPriceRepository.findByCategoryId(id);
        minimumPrice.setMinimumPrice(value);
        minimumPriceRepository.save(minimumPrice);
    }

    /**
     * Will add a category, by setting to true.
     * @param categoryId The category id.
     */
    public void addCategory(long categoryId) {
        Category category = categoryRepository.findByCategoryId(categoryId);
        category.setPreapproved(true);
        categoryRepository.save(category);
    }

    /**
     * Will remove a category, while keeping the record in the DB, by setting to false.
     * @param categoryId The category id.
     */
    public void removeCategory(long categoryId) {
        Category category = categoryRepository.findByCategoryId(categoryId);
        category.setPreapproved(false);
        categoryRepository.save(category);
    }
}
