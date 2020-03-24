package com.ebay.codechallenge.main.service;

import com.ebay.codechallenge.main.model.Category;
import com.ebay.codechallenge.main.model.Item;
import com.ebay.codechallenge.main.model.MinimumPrice;
import com.ebay.codechallenge.main.model.Seller;
import com.ebay.codechallenge.main.repository.CategoryRepository;
import com.ebay.codechallenge.main.repository.ItemRepository;
import com.ebay.codechallenge.main.repository.MinimumPriceRepository;
import com.ebay.codechallenge.main.repository.SellerRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ebay.codechallenge.main.utils.Utils.addError;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final MinimumPriceRepository minimumPriceRepository;
    private final SellerRepository sellerRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, MinimumPriceRepository minimumPriceRepository, SellerRepository sellerRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.minimumPriceRepository = minimumPriceRepository;
        this.sellerRepository = sellerRepository;
        this.categoryRepository = categoryRepository;
    }

    public String addItem(String payload) {
        JSONObject payloadObj = new JSONObject(payload);
        JSONObject returnObj = new JSONObject();
        JSONArray errorArr = new JSONArray();

        if (!payloadObj.has("title")) {
            addError(errorArr, "title is a required field");
        }
        if (!payloadObj.has("sellerId")) {
            addError(errorArr, "sellerId is a required field");
        }
        if (!payloadObj.has("categoryId")) {
            addError(errorArr, "categoryId is a required field");
        }
        if (!payloadObj.has("price")) {
            addError(errorArr, "price is a required field");
        }
        if (errorArr.length() != 0) {
            returnObj.put("errorList", errorArr);
        }

        if (returnObj.length() == 0) {
            Item item = new Item();
            item.setPrice(Double.parseDouble(payloadObj.getString("price")));
            item.setCategoryId(Long.parseLong(payloadObj.getString("categoryId")));
            item.setSellerId(Long.parseLong(payloadObj.getString("sellerId")));
            item.setTitle(payloadObj.getString("title"));
            itemRepository.save(item);

            returnObj.put("status", "successfully added item");
        }

        return returnObj.toString();
    }

    public String getItems() {
        JSONObject returnObj = new JSONObject();
        JSONArray returnArr = new JSONArray();

        List<Item> allItems = itemRepository.findAll();
        for (Item item: allItems) {
            JSONObject currObj = new JSONObject();
            currObj.put("id", item.getItemId());
            currObj.put("price", item.getPrice());
            currObj.put("categoryId", item.getCategoryId());
            currObj.put("sellerId", item.getSellerId());
            currObj.put("title", item.getTitle());
            returnArr.put(currObj);
        }

        returnObj.put("content", returnArr);
        return returnObj.toString();
    }

    public String getItem(long id) {
        JSONObject returnObj = new JSONObject();

        Item item = itemRepository.findById(id);
        returnObj.put("id", item.getItemId());
        returnObj.put("price", item.getPrice());
        returnObj.put("categoryId", item.getCategoryId());
        returnObj.put("sellerId", item.getSellerId());
        returnObj.put("title", item.getTitle());

        return returnObj.toString();
    }

    public String getItemEligibility(long id) {
        JSONObject returnObj = new JSONObject();

        // Get the item.
        Item item = itemRepository.findById(id);
        long categoryId = item.getCategoryId();

        // Get the seller profile for the category.
        Seller seller = sellerRepository.findBySellerId(item.getSellerId());

        // Get the minimum price for the category the item belongs to.
        MinimumPrice minimumPrice = minimumPriceRepository.findByCategoryId(categoryId);

        // Get the category of the item to see if it's preapproved.
        Category category = categoryRepository.findByCategoryId(categoryId);

        boolean approved = category.isPreapproved() && seller.isEnrolled() && (item.getPrice() >= minimumPrice.getMinimumPrice());
        if (approved) {
            returnObj.put("eligible", true);
        } else {
            returnObj.put("eligible", false);
        }

        return returnObj.toString();
    }
}
