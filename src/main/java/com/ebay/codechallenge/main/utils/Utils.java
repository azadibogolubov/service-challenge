package com.ebay.codechallenge.main.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class Utils {
    public static JSONArray addError(JSONArray errorArr, String error) {
        JSONObject errorObj = new JSONObject();
        errorObj.put("error", error);
        errorArr.put(errorObj);
        return errorArr;
    }
}
