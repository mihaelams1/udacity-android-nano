package com.udacity.sandwichclub.utils;

import android.support.annotation.Nullable;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME_TAG = "name";
    private static final String MAIN_NAME_TAG = "mainName";
    private static final String ALSO_KNOWN_AS_TAG = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN_TAG = "placeOfOrigin";
    private static final String DESCRIPTION_TAG = "description";
    private static final String IMAGE_TAG = "image";
    private static final String INGREDIENTS_TAG = "ingredients";

    @Nullable
    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject nameObject = jsonObject.getJSONObject(NAME_TAG);
            String mainName = nameObject.getString(MAIN_NAME_TAG);
            JSONArray alsoKnownAsArray = nameObject.getJSONArray(ALSO_KNOWN_AS_TAG);
            String placeOfOrigin = jsonObject.getString(PLACE_OF_ORIGIN_TAG);
            String description = jsonObject.getString(DESCRIPTION_TAG);
            String image = jsonObject.getString(IMAGE_TAG);
            JSONArray ingredientsArray = jsonObject.getJSONArray(INGREDIENTS_TAG);

            return new Sandwich(
                    mainName,
                    getListOfStringsFromJsonArray(alsoKnownAsArray),
                    placeOfOrigin,
                    description,
                    image,
                    getListOfStringsFromJsonArray(ingredientsArray));
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Nullable
    private static List<String> getListOfStringsFromJsonArray(JSONArray jsonArray) {
        try {
            List<String> returnedList = new ArrayList<>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                returnedList.add(jsonArray.getString(i));
            }

            return returnedList;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
