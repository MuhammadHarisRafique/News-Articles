package com.example.hv.newsarticles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView searchTextView;
    private Button searchButton;

    public static ListView newsListView;

    public static TextView countTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchTextView = (TextView) findViewById(R.id.textView);

        searchButton = (Button) findViewById(R.id.button);

        newsListView = (ListView) findViewById(R.id.listView);

        countTextView = (TextView) findViewById(R.id.count);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!searchTextView.getText().toString().isEmpty()){

                    //for google feed api
                    String url = "https://ajax.googleapis.com/ajax/services/feed/find?" +
                            "v=1.0&q=";

                    String newText = searchTextView.getText().toString().replace(" ", "+");

                    final String completeUrl = url + newText;

                    new GetNews(MainActivity.this).execute(completeUrl);

                }
            }
        });
    }
}
