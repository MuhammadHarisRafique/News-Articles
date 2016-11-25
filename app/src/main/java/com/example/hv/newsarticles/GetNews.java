package com.example.hv.newsarticles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by hv on 11/25/16.
 */
public class GetNews extends AsyncTask<String, String, String> {

    ArrayList<News> newsList;
    private Context context;
    News news;
    int itemsCount;

    public GetNews(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            newsList = new ArrayList<News>();

            String response = sendGet(strings[0]);

            JSONObject object = new JSONObject(response);

            JSONObject responseData = object.getJSONObject("responseData");
            JSONArray newsArray = responseData.getJSONArray("entries");

            itemsCount = newsArray.length();

            for (int i = 0; i <= newsArray.length() - 1; i++){

                JSONObject item = newsArray.getJSONObject(i);

                news = new News();

                if (item.has("title")){
                    String title = item.getString("title");
                    news.setTitle(stripHtml(title));
                }

                if (item.has("contentSnippet")){
                    String contentSnippet = item.getString("contentSnippet");
                    news.setContentSnippet(stripHtml(contentSnippet));
                }

                if (item.has("link")){
                    String link = item.getString("link");
                    news.setLink(stripHtml(link));
                }

                newsList.add(news);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

    @Override
    protected void onPostExecute(String s) {

        // Setup the data source
        NewsAdapter adapter = new NewsAdapter(context, newsList);

        // get the ListView and attach the adapter
        MainActivity.newsListView.setAdapter(adapter);
        MainActivity.countTextView.setText("Number of Results: " + newsList.size());

        MainActivity.newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String url = newsList.get(i).getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        });

    }

    private String sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        //con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }

}
