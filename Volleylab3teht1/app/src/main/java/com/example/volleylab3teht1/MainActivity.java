package com.example.volleylab3teht1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

    EditText Edit;
    TextView textView;
    Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.textView);
        goButton = findViewById(R.id.goButton);


        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText();
            }
        });
    }


    private String getTextFieldURL() {
        Edit = findViewById(R.id.text_editor);
        String URL = Edit.getText().toString();
        return URL;
    }

    private void showText() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getTextFieldURL();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                textView.setText(response.substring(0, 15000));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Ei toiminu");
            }
        });
        queue.add(stringRequest);
    }
}
