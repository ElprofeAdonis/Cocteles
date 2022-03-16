package com.example.cocteles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IntroActivity extends AppCompatActivity {

    EditText txtSearch;
    TextView strDrink, strCategory, strInstructions;
    Button btnSearch;
    ImageView imgDrink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        txtSearch = findViewById(R.id.txtSearch);
        strDrink = findViewById(R.id.strDrink);
        strCategory = findViewById(R.id.strCategory);
        strInstructions = findViewById(R.id.strInstructions);
        btnSearch = findViewById(R.id.btnSearch);
        imgDrink = findViewById(R.id.imgDrink);

        /*btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchDrink();
            }
        });*/
    }

    public void goToMain(View v) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    public void searchDrink(View v) {
        String strParam = txtSearch.getText().toString();
        String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + strParam;

        StringRequest getRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray drinksArray = jsonObject.getJSONArray("drinks");
                    JSONObject firstDrink = drinksArray.getJSONObject(0);

                    Picasso.get()
                            .load(firstDrink.getString("strDrinkThumb"))
                            .error(R.mipmap.ic_launcher)
                            .into(imgDrink);

                    strDrink.setText(firstDrink.getString("strDrink"));
                    strCategory.setText(firstDrink.getString("strCategory"));
                    strInstructions.setText(firstDrink.getString("strInstructions"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(getRequest);
    }
}