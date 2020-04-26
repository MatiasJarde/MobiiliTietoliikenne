package com.example.footballleagueslab3teht2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    JSONArray areasJSON;
    JSONObject name;
    String temp;
    String idTemp;
    String areaid;

    String areaURL = "https://api.football-data.org/v2/areas";


    private ArrayList<String> areasList = new ArrayList<>();
    private ArrayList<String> areaIDList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);


        getAreas(areaURL);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedItemID = (int) parent.getItemIdAtPosition(position);
                String areaName = (String) parent.getItemAtPosition(position);
                areaid = areaIDList.get(selectedItemID);
                Intent competitionIntent = new Intent(MainActivity.this, CompetitionActivity.class);
                competitionIntent.putExtra("areaid", areaid);
                competitionIntent.putExtra("areaName", areaName);
                startActivity(competitionIntent);
            }
        });



    }

    private void getAreas(String areaURL) {
        JsonObjectRequest jsonAreaRequest = new JsonObjectRequest
                (Request.Method.GET, areaURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            areasJSON = response.getJSONArray("areas");
                            parseJSON(areasJSON);
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

        MySingleton.getInstance(this).addToRequestQueue(jsonAreaRequest);

    }

    private void parseJSON(JSONArray json) {
        for (int i = 0; i < json.length(); i++) {
            try {
                name = json.getJSONObject(i);
            } catch (JSONException e) {
                Log.d("mytag", "" + e);
            }

            try {
                temp = name.getString("name");
                idTemp = name.getString("id");
            } catch (JSONException e) {
                Log.d("mytag", "" + e);
            }

            areasList.add(temp);
            areaIDList.add(idTemp);
            arrayAdapt();
        }

    }
    private void arrayAdapt () {
        final ArrayAdapter<String> aa;
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, areasList);
        listView.setAdapter(aa);
    }
}
