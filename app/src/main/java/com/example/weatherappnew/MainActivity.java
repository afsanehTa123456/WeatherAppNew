package com.example.weatherappnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> MoviesArry =new ArrayList<>();
    MyWeatherViewAdapter adapterLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchView =findViewById(R.id.floating_search_view);




        final SQLiteOpenHelper helper = new SQLiteOpenHelper(MainActivity.this, "Sematec", null, 1);
//Toast.makeText(MainActivity.this,"onMenuOpened",Toast.LENGTH_SHORT).show();

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                //Toast.makeText(MainActivity.this,"onMenuOpened",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSearchAction(String currentQuery) {
                //Toast.makeText(MainActivity.this,currentQuery,Toast.LENGTH_SHORT).show();
                int CountTest = helper.CheckItemifSaved(currentQuery);
                if (CountTest == 0) {
                    helper.insertMovies(currentQuery);
                }
                Intent intent = new Intent(MainActivity.this, DetailWeatherPro.class);
                intent.putExtra("CityName", currentQuery);
                startActivity(intent);
                LoadItems(helper);
            }
        });


        LoadItems(helper);



    }
    public void LoadItems(SQLiteOpenHelper helper)
    {
        MoviesArry = helper.getAllStudentsName();
        String varand = String.valueOf(MoviesArry.size());
        //Toast.makeText(MainActivity.this,varand,Toast.LENGTH_SHORT).show();

        RecyclerView recyclerView = findViewById(R.id.rvContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapterLeft = new MyWeatherViewAdapter(MainActivity.this, MoviesArry);
        adapterLeft.setClickListener(MainActivity.this);
        recyclerView.setAdapter(adapterLeft);
    }
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(MainActivity.this, DetailWeatherPro.class);
        intent.putExtra("CityName", MoviesArry.get(position));
        startActivity(intent);
    }
}
