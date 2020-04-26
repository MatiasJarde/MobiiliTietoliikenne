package com.example.lab2teht1async;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String htmlText;
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

    public class simpleHTTP extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";

        @Override
        protected String doInBackground(String... params) {
            String stringURL = params[0];
            String inputLine;

            try {
                URL myURL = new URL(stringURL);

                HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();

                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);

                connection.connect();

                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());

                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                htmlText = stringBuilder.toString();


            } catch (IOException e) {
                e.printStackTrace();
                htmlText = null;
            }

            return htmlText;
        }

        @Override
        protected void onPostExecute(String htmlText) {
            textView.setText(htmlText);
        }
    }




    private String getTextFieldURL() {
        Edit = findViewById(R.id.text_editor);
        String URL = Edit.getText().toString();
        return URL;
    }

    private void showText() {
        String URL = getTextFieldURL();
        new simpleHTTP().execute(URL);
    }
}
