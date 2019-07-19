package com.example.tann.tiki_home_test.Execute;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.tann.tiki_home_test.Adapter.HotKeyAdapter;
import com.example.tann.tiki_home_test.Model.KeyWord;
import com.example.tann.tiki_home_test.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HotKey extends AppCompatActivity {
    private ArrayList<KeyWord> lsKeyword;
    private HotKeyAdapter adapter;
    private RecyclerView rvKeywords;
    private final String URL_DATA = "https://raw.githubusercontent.com/tikivn/android-home-test/v2/keywords.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_key);
        addControls();
        addEvents();
    }
    private void addControls(){
        lsKeyword = new ArrayList<>();
        adapter = new HotKeyAdapter(this, lsKeyword);
        rvKeywords = findViewById(R.id.rvKeywords);
    }
    private void addEvents() {
        getJSONfromURL(URL_DATA);
        rvKeywords.setAdapter(adapter);
        rvKeywords.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
    }

    private void getJSONfromURL(String url_data) {
        try {
            String data = new GetDataTask().execute(url_data).get();
            JSONArray arrData = new JSONArray(data);
            for (int i = 0; i < arrData.length(); i++)
                lsKeyword.add(new KeyWord(arrData.get(i).toString()));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
class GetDataTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        StringBuilder builder = new StringBuilder();
        HttpURLConnection connection = null;
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            builder.setLength(0);
            while ((line = bufferedReader.readLine()) != null)
                builder.append(line);
            bufferedReader.close();
        } catch (Exception e) {
            Log.e("error", e.toString());
        } finally {
            assert connection != null;
            connection.disconnect();
        }
        return builder.toString();
    }
}
