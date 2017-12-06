package com.stjohns.stormchat.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.stjohns.stormchat.R;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        SearchView sv = (SearchView)findViewById(R.id.search_activity_search_bar);
        ListView lv = (ListView)findViewById(R.id.result_list);
        ArrayList<String> arraySearchResults = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                SearchActivity.this,
                android.R.layout.simple_list_item_1,
                arraySearchResults);
        lv.setAdapter(adapter);

    }
}
