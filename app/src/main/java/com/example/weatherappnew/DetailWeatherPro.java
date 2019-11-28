package com.example.weatherappnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailWeatherPro extends AppCompatActivity {

    MyWeatherDetailViewAdapter adapterLeft;
    ArrayList<DetailWeather> mData;
    int dayOfWeek;
    LinearLayout _boxplace;
    private SlidingUpPanelLayout mLayout;
    TextView _txtdegOrg,_txtrain,_txtbaran,PlceCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather_pro);
        AsyncHttpClient Zirclient = new AsyncHttpClient();
        Intent intent = getIntent();
        _txtdegOrg = findViewById(R.id.txtdegOrg);
        _txtrain = findViewById(R.id.txtrain);
        _txtbaran = findViewById(R.id.txtbaran);
        Calendar calendar = Calendar.getInstance();
        PlceCity = findViewById(R.id.PlceCity);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        _boxplace = findViewById(R.id.boxplace);
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                //Toast.makeText(DetailWeatherPro.this,previousState + " " + newState,Toast.LENGTH_SHORT).show();

                if (newState.toString() == "COLLAPSED") {

                    _boxplace.setBackgroundColor(Color.parseColor("#ffffff"));
                    //_txtrain.setTextColor(Color.parseColor("#ffffff"));
                    //_txtdegOrg.setTextColor(Color.parseColor("#ffffff"));
                    //_txtbaran.setTextColor(Color.parseColor("#ffffff"));
                }
                else
                {
                    _boxplace.setBackgroundColor(Color.TRANSPARENT);
                    //_txtrain.setTextColor(Color.parseColor("#000"));
                    //_txtdegOrg.setTextColor(Color.parseColor("#000"));
                    //_txtbaran.setTextColor(Color.parseColor("#000"));
                }

            }
        });
        //Log.d("varandtest", String.valueOf(day));
        String _CityName = intent.getStringExtra("CityName");


        PlceCity.setText(_CityName.toUpperCase());

        String Url = "https://api.openweathermap.org/data/2.5/forecast/daily?q="+_CityName+",IR&appid=141a44d6c157c2a60e5e70551c399aba";// + intent.getStringExtra("ItemID");
        Log.d("varandtest", Url);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Url, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("varandtest", String.valueOf(throwable));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //Log.d("varandtest", responseString);
                JSONObject jsonobject = null;
                try {
                    jsonobject = new JSONObject(responseString);
                    JSONArray jsonoArray = new JSONArray(jsonobject.getString("list"));
                    int dayof = dayOfWeek;
                    mData = new ArrayList<>();
                    for (int i = 0; i < jsonoArray.length(); i++) {
                        JSONObject jsonSub = jsonoArray.getJSONObject(i);
                        Log.d("varandtest", jsonSub.getString("weather"));

                        JSONArray jweatherArray = new JSONArray(jsonSub.getString("weather"));
                        JSONObject jsonWSub = jweatherArray.getJSONObject(0);//icon
                        Log.d("varandtest", "ghable mdata");
                        //mData.add(jsonWSub.getString("description"));
                        if (i==0)
                        {
                            _txtrain.setText(jsonSub.getString("humidity") + "%");
                            _txtdegOrg.setText(jsonSub.getString("deg") + "°C");
                            _txtbaran.setText(jsonSub.getString("speed") + "m/s");
                        }
                        else if (i !=  0 && i !=  1)
                        {
                            //"http://openweathermap.org/img/wn/" + jsonWSub.getString("icon") + ".png"
                            mData.add(new DetailWeather(GetCurrentday(dayof), jsonWSub.getString("icon"), jsonWSub.getString("description"), jsonSub.getString("deg") + "°"));
                        }
                        else if (i == 1)
                        {
                            mData.add(new DetailWeather("Tommorow",jsonWSub.getString("icon"), jsonWSub.getString("description"), jsonSub.getString("deg") + "°"));
                        }

                        Log.d("varandtest", "bade mdata");
                        if (dayof != 7)
                        {
                            dayof = dayof + 1;
                        }
                        else
                        {
                            dayof = 1;
                        }

                        //icon http://openweathermap.org/img/wn/01n.png
                    }
                    RecyclerView recyclerView = findViewById(R.id.rvshowitem);
                    recyclerView.setLayoutManager(new LinearLayoutManager(DetailWeatherPro.this));
                    adapterLeft = new MyWeatherDetailViewAdapter(DetailWeatherPro.this,mData);
                    //adapterLeft.setClickListener(MainActivity.this);
                    recyclerView.setAdapter(adapterLeft);
                    //Log.d("varandtest", String.valueOf(mData.size()));


                } catch (JSONException e) {
                    Log.d("varandtest", "izinja");
                    e.printStackTrace();
                }

                //Toast.makeText(DetailWeatherPro.this,responseString,Toast.LENGTH_SHORT).show();
            }
        });


    }

    public String  GetCurrentday(int dayOfW)
    {
        String weekDay = "";



        if (Calendar.MONDAY == dayOfW) {
            weekDay = "Monday";
        } else if (Calendar.TUESDAY == dayOfW) {
            weekDay = "Tuesday";
        } else if (Calendar.WEDNESDAY == dayOfW) {
            weekDay = "Wednesday";
        } else if (Calendar.THURSDAY == dayOfW) {
            weekDay = "Thursday";
        } else if (Calendar.FRIDAY == dayOfW) {
            weekDay = "Friday";
        } else if (Calendar.SATURDAY == dayOfW) {
            weekDay = "Saturday";
        } else if (Calendar.SUNDAY == dayOfW) {
            weekDay = "Sunday";
        }
        return  weekDay;
    }
}
