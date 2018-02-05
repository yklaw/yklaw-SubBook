/*
 * CMPUT 301. Assignment 1.
 * SubBook
 *
 * Copyright (c) 2018. Yu Kin Law. CMPUT 301. University of Alberta. All rights reserved.
 */

package com.yklaw_subbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Display Activity
 * @author Yu Kin Law
 * @version 1.0
 * @see Activity
 * @see MainActivity
 */
public class DisplayActivity extends AppCompatActivity {

    private ArrayList<Subscription> subList;
    ArrayAdapter<String> arrayadapter;
    ListView listView;
    TextView textView;

    /**
     * On Start of the Activity, Create the list and the text for display
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        listView = findViewById(R.id.subList);
        textView = findViewById(R.id.chargeText);
        subList = new ArrayList<Subscription>();
    }

    /**
     * Reloads the file and display
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile();

        ArrayList<String> tempSubList = new ArrayList<String>();

        // If subList is not empty
        if (subList.isEmpty() == false) {
            float total = 0;
            // Add Sub info into a temp list, and calculate the total
            for (Subscription sub : subList) {
                tempSubList.add(sub.getSubInfo());
                total = total + Float.parseFloat(sub.getCharge());
            }

            // Set the total charge
            textView.setText("Total Monthly Charge: $" + total);

            arrayadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                    tempSubList);

            listView.setAdapter(arrayadapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    editActivity(i);
                }
            });
        }
    }


    /**
     * Load past Subscription from file
     */
    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput("sub.sav");
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            // Taken https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-02-04
            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            subList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subList = new ArrayList<Subscription>();
        }
    }

    /**
     * Start Edit Activity when clicked
     */
    private void editActivity(int i) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("Location", i);
        startActivity(intent);
    }

    /**
     * Called when the activity is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy is called");
    }
}
