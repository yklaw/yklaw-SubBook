/*
 * CMPUT 301. Assignment 1.
 * SubBook
 *
 * Copyright (c) 2018. Yu Kin Law. CMPUT 301. University of Alberta. All rights reserved.
 */

package com.yklaw_subbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Main Activity
 *
 * @author Yu Kin Law
 * @version 1.0
 * @see Activity
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<Subscription>subList;


    /**
     * On Start of the Activity, Create a new list of Subscription.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subList = new ArrayList<Subscription>();
    }

    /**
     * Reloads the file
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile();
    }

    /**
     * Save the data into a Subscription List
     */
    public void saveSub(View view) {
        EditText nameText = findViewById(R.id.name);
        EditText dateText = findViewById(R.id.date);
        EditText chargeText = findViewById(R.id.charge);
        EditText commentText = findViewById(R.id.comment);

        String name = nameText.getText().toString();
        String date = dateText.getText().toString();
        String charge = chargeText.getText().toString();
        String comment = commentText.getText().toString();

        if(ValidData.checkName(name) && ValidData.checkDate(date)
                && ValidData.checkCharge(charge) && ValidData.checkComment(comment)) {
            Subscription sub = new Subscription(name, date, charge, comment);
            saveInFile(sub);

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Subscription Saved", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            /* Error Message */
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Invalid Inputs, Subscription Not Saved", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    /**
     * Save subList to file
     */
    private void saveInFile(Subscription sub) {
        subList.add(sub);
        try {
            FileOutputStream fos = openFileOutput("sub.sav",
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            //subList = new ArrayList<Subscription>();
            Gson gson = new Gson();
            gson.toJson(subList, out);
            out.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
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
     * Create intent to display Subscription
     */
    public void viewSub(View view) {
        Intent intent = new Intent(this, DisplayActivity.class);
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
