package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Find layout views to place values into later.
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView akaTv = findViewById(R.id.also_known_tv);
        TextView originTv = findViewById(R.id.origin_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        // Set title to that of the chosen sandwich.
        setTitle(sandwich.getMainName());

        // Place sandwich image into image view.
        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        // Place any alternate names in the text view, or a string of none are found.
        List<String> akaList = sandwich.getAlsoKnownAs();
        String akaString = "";
        for (int i = 0; i < akaList.size(); i++){
            if(i == 0) {
                akaString = akaList.get(i);
            }
            else{
                String akaName = akaList.get(i);
                akaString = akaString + ", " + akaName;
            }
        }
        if(akaString.equals("")){
            akaString = "Unknown alternate names.";
        }
        akaTv.setText(akaString);

        // Place the country/countries of origin in the text view, or a string if unknown.
        if(sandwich.getPlaceOfOrigin().isEmpty()){
            originTv.setText("Unknown country of origin.");
        }
        else{
            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        // Place the list of ingredients into the text view.
        List<String> ingredientsList = sandwich.getIngredients();
        String ingredientsString = "";

        for (int i = 0; i < ingredientsList.size(); i++){
            if(i == 0) {
                ingredientsString = ingredientsList.get(i);
            }
            else{
                String ingredientsName = ingredientsList.get(i);
                ingredientsString = ingredientsString + ", " + ingredientsName;
            }
        }
        ingredientsTv.setText(ingredientsString);

        // Place the sandwich description into the text view.
        descriptionTv.setText(sandwich.getDescription());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
