package com.example.footballleagueslab3teht2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompetitionActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;
    JSONArray competitions;
    String name;
    JSONObject tempObject;
    String areaID;
    String areaName;

    private ArrayList<String> leagues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.textView);

        areaID = getIntent().getStringExtra("areaid");
        areaName = getIntent().getStringExtra("areaName");
        /* getSupportActionBar().setTitle(areaName); */
        String areaSpecificURL = "https://api.football-data.org/v2/competitions?areas=" + areaID;

        getCompetitions(areaSpecificURL);

    }

    private void getCompetitions(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            competitions = response.getJSONArray("competitions");
                            parseJSON(competitions);
                        } catch (JSONException e) {
                            Log.d("mytag", "" + e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("mytag", "" + error);
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    private void parseJSON(JSONArray json) {
        for (int i = 0; i < json.length(); i++) {
            try {
                tempObject = json.getJSONObject(i);
            } catch (JSONException e) {
                Log.d("mytag", "" + e);
            }

            try {
                name = tempObject.getString("name");
            } catch (JSONException e) {
                Log.d("mytag", "" + e);
            }

            leagues.add(name);
            arrayAdapt();
        }
    }

    private void arrayAdapt () {
        final ArrayAdapter<String> aa;
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, leagues);
        listView.setAdapter(aa);
    }
}
