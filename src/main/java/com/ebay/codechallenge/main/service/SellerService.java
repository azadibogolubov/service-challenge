package com.ebay.codechallenge.main.service;

import com.ebay.codechallenge.main.model.Seller;
import com.ebay.codechallenge.main.repository.SellerRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.ebay.codechallenge.main.utils.Utils.addError;

@Service
public class SellerService {
    private final SellerRepository sellerRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public String getSellers() {
        JSONObject returnObj = new JSONObject();
        JSONArray returnArr = new JSONArray();

        ArrayList<Seller> allSellers = sellerRepository.findAll();
        for (Seller s: allSellers) {
            JSONObject currObj = new JSONObject();
            currObj.put("id", s.getSellerId());
            currObj.put("firstName", s.getFirstName());
            currObj.put("lastName", s.getLastName());
            currObj.put("enrolled", s.isEnrolled());
            returnArr.put(currObj);
        }

        returnObj.put("content", returnArr);
        return returnObj.toString();

    }

    public String getSeller(long id) {
        JSONObject returnObj = new JSONObject();

        Seller seller = sellerRepository.findBySellerId(id);
        returnObj.put("id", seller.getSellerId());
        returnObj.put("firstName", seller.getFirstName());
        returnObj.put("lastName", seller.getLastName());
        returnObj.put("enrolled", seller.isEnrolled());

        return returnObj.toString();
    }

    public String addSeller(String payload) {
        JSONObject payloadObj = new JSONObject(payload);
        JSONObject returnObj = new JSONObject();
        JSONArray errorArr = new JSONArray();

        if (!payloadObj.has("firstName")) {
            addError(errorArr, "firstName is a required field");
        }
        if (!payloadObj.has("lastName")) {
            addError(errorArr, "lastName is a required field");
        }
        if (!payloadObj.has("enrolled")) {
            addError(errorArr, "enrolled is a required field");
        }
        if (errorArr.length() != 0) {
            returnObj.put("errorList", errorArr);
        }

        if (returnObj.length() == 0) {
            Seller seller = new Seller();
            seller.setFirstName(payloadObj.getString("firstName"));
            seller.setLastName(payloadObj.getString("lastName"));
            seller.setEnrolled(Boolean.parseBoolean(payloadObj.getString("enrolled")));
            sellerRepository.save(seller);

            returnObj.put("status", "successfully added seller");
        }

        return returnObj.toString();
    }
}
