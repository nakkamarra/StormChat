package com.stjohns.stormchat.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stjohns.stormchat.R;
import java.util.ArrayList;

public class SearchActivity extends Activity {
    private ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userDB = database.getReference("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        SearchView sv = findViewById(R.id.search_activity_search_bar);
        ListView lv = findViewById(R.id.result_list);
        ArrayList<String> arraySearchResults = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                SearchActivity.this,
                android.R.layout.simple_list_item_1,
                list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedResult = adapterView.getItemAtPosition(i).toString();
                String userID = selectedResult.substring(selectedResult.indexOf(":")+1);
                Intent whereToGo = new Intent(SearchActivity.this, ForeignProfileActivity.class);
                whereToGo.putExtra("userID", userID);
                startActivity(whereToGo);
            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                list.clear();
                getSearchResults(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()){
                    list.clear();
                } else {
                    list.clear();
                    getSearchResults(newText);
                }
                return true;
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_search, menu);
//        MenuItem item = menu.findItem(R.id.menuSearch);
//        SearchView searchView = (SearchView)item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

    public void getSearchResults(final String search){
        userDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("Key", dataSnapshot.getKey());
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    if (d.getKey().equalsIgnoreCase("email")){
                        if (d.getValue(String.class).contains(search)){
                            list.add(d.getValue(String.class));
                            adapter.notifyDataSetChanged();
                        }
                    } else if (d.getKey().equalsIgnoreCase("username")){
                        if (d.getValue(String.class).contains(search)){
                            list.add(d.getValue(String.class) +" :"+ dataSnapshot.getKey());
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent whereToGo = new Intent(SearchActivity.this, HomeActivity.class);
        SearchActivity.this.finish();
        startActivity(whereToGo);
    }
}
