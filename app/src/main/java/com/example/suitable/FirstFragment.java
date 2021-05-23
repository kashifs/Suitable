package com.example.suitable;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Random;

public class FirstFragment extends Fragment {

    //https://home.openweathermap.org/api_keys TODO best practice for this
    public final static String OWM_APIKEY = "fe7aafe463cc341de6052036b3e47fcf";

    //    public final static String NEW_YORK = "New York, US";
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String[] shortTops = {
            "Blue & White Striped Shirt", "V Neck White Tee", "Off White Tee"
    };

    public static final String[] longTops = {
            "Blue OCBD #1", "Blue OCBD #2", "Blue OCBD #3",
            "White OCBD #1", "WhiteOCBD #2", "WhiteOCBD #3",
            "Jean Shirt", "Black Long T Shirt", "Black Jean Shirt",
    };

//    public static final String[] formalTops = {
//            "Beige Dress Shirt"
//    };

    public static final String[] shortBottoms = {
            "Blue Chino Shorts", "Green Chino Shorts",
            "Khaki Chino Shorts", "Navy Chino Shorts"
    };

    public static final String[] longBottoms = {
            "Dark Jeans", "Khaki Chinos",
            "Olive Chinos", "Black Chinos", "Navy Chinos"
    };

    public static final String[] zapatos = {
            "White Sneakers", "Grey Sneakers", "Brown Loafers",
            "Blue and White Sneakers", "Brown Boots"
    };

    Random topsRand = new Random();
    Random bottomsRand = new Random();
    Random zapatosRand = new Random();

    String finalTop;
    String finalBottom;
    String finalZapato;


    RequestQueue requestQueue;
    StringRequest stringRequest;

    TextView textview_top;
    TextView textview_bottom;
    TextView textview_zapato;

    double feelsLike;
    double humidity;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textview_top = view.findViewById(R.id.textview_top);
        textview_bottom = view.findViewById(R.id.textview_bottom);
        textview_zapato = view.findViewById(R.id.textview_zapato);

        getWeather();
        getNewOutfit();

        view.findViewById(R.id.accept_outfit).setOnClickListener(view1 -> acceptOutfit());

        view.findViewById(R.id.new_outfit).setOnClickListener(view12 -> getNewOutfit());
    }

    private void getWeather() {
        // Instantiate the RequestQueue.
        requestQueue = Volley.newRequestQueue(getContext());

        String url = "http://api.openweathermap.org/data/2.5/weather?id=5128581&units=imperial&appid=" + OWM_APIKEY;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {

                    try {
                        JSONObject main = response.getJSONObject("main");
                        Log.e(TAG, "Main: " + main.toString());
                        feelsLike = Double.parseDouble(main.getString("feels_like"));
                        humidity = Double.parseDouble(main.getString("humidity"));

                        Log.e(TAG, "Feels Like: " + feelsLike);
                        Log.e(TAG, "Humidity: " + humidity);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.e("MainActivity.java", "That didn't work"));

        // Set the tag on the request.
        if (stringRequest != null) {
            stringRequest.setTag(TAG);
        }

        // Add the request to the RequestQueue.
        if (requestQueue != null) {
            requestQueue.add(jsonObjectRequest);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }

    private void acceptOutfit() {
        // Do something in response to button click
//        this.onViewCreated(view, null);
        Log.e(TAG, "In Accept Outfit");
    }

    private void getNewOutfit(){
        if (feelsLike >= 90) {

            finalTop = shortTops[topsRand.nextInt(shortTops.length)];
            finalBottom = shortBottoms[bottomsRand.nextInt(shortBottoms.length)];
            finalZapato = zapatos[zapatosRand.nextInt(zapatos.length - 1)];

            Log.e(TAG, "Today's Top: " + finalTop);
            Log.e(TAG, "Today's Bottom: " + finalBottom);
            Log.e(TAG, "Today's Zapato: " + finalZapato);

        } else if (feelsLike <= 85 && feelsLike > 75) {

            String[] allTops = Arrays.copyOf(shortTops, shortTops.length + longTops.length);
            System.arraycopy(longTops, 0, allTops, shortTops.length, longTops.length);

            String[] allBottoms = Arrays.copyOf(shortBottoms, shortBottoms.length + longBottoms.length);
            System.arraycopy(longBottoms, 0, allBottoms, shortBottoms.length, longBottoms.length);

            finalTop = allTops[topsRand.nextInt(allTops.length)];
            finalBottom = allBottoms[bottomsRand.nextInt(allBottoms.length)];
            finalZapato = zapatos[zapatosRand.nextInt(zapatos.length)];

            Log.e(TAG, "Today's Top: " + finalTop);
            Log.e(TAG, "Today's Bottom: " + finalBottom);
            Log.e(TAG, "Today's Zapato: " + finalZapato);
        } else {

            String[] allTops = Arrays.copyOf(shortTops, shortTops.length + longTops.length);
            System.arraycopy(longTops, 0, allTops, shortTops.length, longTops.length);

            finalTop = allTops[topsRand.nextInt(allTops.length)];
            finalBottom = longBottoms[bottomsRand.nextInt(longBottoms.length)];
            finalZapato = zapatos[zapatosRand.nextInt(zapatos.length)];

            Log.e(TAG, "Today's Top: " + finalTop);
            Log.e(TAG, "Today's Bottom: " + finalBottom);
            Log.e(TAG, "Today's Zapato: " + finalZapato);
        }

        textview_top.setText("Today it feels like " + feelsLike + "\n" + "Today's Top: " + finalTop);
        textview_bottom.setText("Today's bottom: " + finalBottom);
        textview_zapato.setText("Today's footwear: " + finalZapato);
    }
}