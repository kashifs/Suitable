package com.example.suitable;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    //https://home.openweathermap.org/api_keys TODO best practice for this
    public final static String OWM_APIKEY = "fe7aafe463cc341de6052036b3e47fcf";

    //    public final static String NEW_YORK = "New York, US";
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String[] shortTops = {
            "BlueWhiteStriped", "VNeckWhite", "OffWhite"
    };

    public static final String[] longTops = {
            "BlueOCBD_1", "BlueOCBD_2", "BlueOCBD_3",
            "WhiteOCBD_1", "WhiteOCBD_2", "WhiteOCBD_3",
            "JeanShirt", "BlackTShirt", "BlackJeanShirt",
    };

    public static final String[] formalTops = {
            "BeigeShirt"
    };

    public static final String[] shortBottoms = {
            "BlueChinoShort", "GreenChinoShort", "KhakiChinoShort", "NavyChinoShort"
    };

    public static final String[] longBottoms = {
            "DarkJean", "KhakiChino", "OliveChino", "BlackChino", "NavyChino"
    };

    public static final String[] zapatos = {
            "WhiteS", "GreyS", "BrownL", "BlueWhiteS", "BrownB"
    };

    Random topsRand = new Random();
    Random bottomsRand = new Random();
    Random zapatosRand = new Random();

    String finalTop;
    String finalBottom;
    String finalZapato;

//    int n = topsRand.nextInt(20);

    RequestQueue requestQueue;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textview = findViewById(R.id.textview_top);
        setSupportActionBar(toolbar);

        textview.setText("test");

        
        // Instantiate the RequestQueue.
        requestQueue = Volley.newRequestQueue(this);
        String url = "http://api.openweathermap.org/data/2.5/weather?id=5128581&units=imperial&appid=" + OWM_APIKEY;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.e(TAG, "JSONObject response is: "+ response.toString());

                        try {

                            JSONObject main = response.getJSONObject("main");
                            Log.e(TAG, "Main: " + main.toString());
                            double feelsLike = Double.parseDouble(main.getString("feels_like"));
                            double humidity = Double.parseDouble(main.getString("humidity"));

                            Log.e(TAG, "Feels Like: " + feelsLike);
                            Log.e(TAG, "Humidity: " + humidity);

                            feelsLike = 70;

                            int numTops;
                            int numBottoms;

                            if (feelsLike >= 90) {
                                Log.e(TAG, "Today's Top: " + shortTops[topsRand.nextInt(shortTops.length)]);
                                Log.e(TAG, "Today's Bottom: " + shortBottoms[bottomsRand.nextInt(shortBottoms.length)]);
                                Log.e(TAG, "Today's Zapato: " + zapatos[zapatosRand.nextInt(zapatos.length - 1)]);

                            }
                            else if (feelsLike <= 85 && feelsLike > 75) {

                                String[] allTops = Arrays.copyOf(shortTops, shortTops.length + longTops.length);
                                System.arraycopy(longTops, 0, allTops, shortTops.length, longTops.length);

                                String[] allBottoms = Arrays.copyOf(shortBottoms, shortBottoms.length + longBottoms.length);
                                System.arraycopy(longBottoms, 0, allBottoms, shortBottoms.length, longBottoms.length);

                                Log.e(TAG, "Today's Top: " + allTops[topsRand.nextInt(allTops.length)]);
                                Log.e(TAG, "Today's Bottom: " + allBottoms[bottomsRand.nextInt(allBottoms.length)]);
                                Log.e(TAG, "Today's Zapato: " + zapatos[zapatosRand.nextInt(zapatos.length)]);
                            }
                            else{

                                String[] allTops = Arrays.copyOf(shortTops, shortTops.length + longTops.length);
                                System.arraycopy(longTops, 0, allTops, shortTops.length, longTops.length);

                                Log.e(TAG, "Today's Top: " + allTops[topsRand.nextInt(allTops.length)]);
                                Log.e(TAG, "Today's Bottom: " + longBottoms[bottomsRand.nextInt(longBottoms.length)]);
                                Log.e(TAG, "Today's Zapato: " + zapatos[zapatosRand.nextInt(zapatos.length)]);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MainActivity.java", "That didn't work");
                    }
                });

        // Set the tag on the request.
        if (stringRequest != null) {
            stringRequest.setTag(TAG);
        }

        // Add the request to the RequestQueue.
        if (requestQueue != null) {
            requestQueue.add(jsonObjectRequest);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }


}