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

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        String unknown = getString(R.string.unknown);;

        TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        String alsoKnownAs = unknown;
        if (alsoKnownAsList != null && alsoKnownAsList.size() > 0) {
            alsoKnownAs = convertToString(alsoKnownAsList);
        }
        alsoKnownAsTv.setText(alsoKnownAs);

        TextView descriptionTv = findViewById(R.id.description_tv);
        String description = sandwich.getDescription();
        if (description == null || description.isEmpty()) {
            description = unknown;
        }
        descriptionTv.setText(description);

        TextView originTv = findViewById(R.id.origin_tv);
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin == null || placeOfOrigin.isEmpty()) {
            placeOfOrigin = unknown;
        }
        originTv.setText(placeOfOrigin);

        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = sandwich.getIngredients();
        String ingredients = unknown;
        if (ingredientsList != null && ingredientsList.size() > 0) {
            ingredients = convertToString(ingredientsList);
        }
        ingredientsTv.setText(ingredients);
    }

    private static String convertToString(List<String> listOfString) {
        StringBuilder sb = new StringBuilder();
        for (String s : listOfString)
        {
            sb.append(s).append(", ");
        }

        String result = sb.toString();
        result = result.substring(0, result.length() - 2);
        return result;
    }
}
