package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        final String SANDWICHJSON_MAIN_NAME = "mainName";
        final String SANDWICHJSON_AKA = "alsoKnownAs";
        final String SANDWICHJSON_ORIGIN = "placeOfOrigin";
        final String SANDWICHJSON_DESCRIPTION = "description";
        final String SANDWICHJSON_IMAGE = "image";
        final String SANDWICHJSON_INGREDIENTS = "ingredients";

        // Turning the fake JSON into a JSONObject to then extract the data from.
        JSONObject sandwichJsonObject = new JSONObject(json);

        // Get the main name of the sandwich.
        JSONObject sandwichNameJsonObject = sandwichJsonObject.getJSONObject("name");
        String sandwichName = sandwichNameJsonObject.getString(SANDWICHJSON_MAIN_NAME);

        // Get the list of any alternate sandwich names.
        JSONArray sandwichJsonArrayAka = sandwichNameJsonObject.optJSONArray(SANDWICHJSON_AKA);
        List<String> sandwichAka = new ArrayList<String>() {
        };
        if (sandwichJsonArrayAka.isNull(0)) {
            sandwichAka.add("");
        }
        else {
            for (int i = 0; i < sandwichJsonArrayAka.length(); i++) {
                String sandwichAkaName = sandwichJsonArrayAka.getString(i);
                sandwichAka.add(sandwichAkaName);
            }
        }

        // Get the country of origin.
        String sandwichOrigin = sandwichJsonObject.getString(SANDWICHJSON_ORIGIN);

        // Get the description of the sandwich.
        String sandwichDescription = sandwichJsonObject.getString(SANDWICHJSON_DESCRIPTION);

        // Get the URL of the image.
        String sandwichImageUrl = sandwichJsonObject.getString(SANDWICHJSON_IMAGE);

        // Get the list of sandwich ingredients.
        JSONArray sandwichJsonArrayIngredients = sandwichJsonObject.optJSONArray(SANDWICHJSON_INGREDIENTS);
        List<String> sandwichIngredients = new ArrayList<String>() {
        };
        if (sandwichJsonArrayIngredients.isNull(0)) {
            sandwichIngredients.add("");
        }
        else {
            for (int i = 0; i < sandwichJsonArrayIngredients.length(); i++) {
                String sandwichIngredientsName = sandwichJsonArrayIngredients.getString(i);
                sandwichIngredients.add(sandwichIngredientsName);
            }
        }

        // Place all parsed values into a new sandwich object.
        Sandwich parsedSandwich = new Sandwich(sandwichName, sandwichAka, sandwichOrigin,
                sandwichDescription, sandwichImageUrl, sandwichIngredients);

        // Return the sandwich with all the delicious parsed values.
        return parsedSandwich;
    }
}