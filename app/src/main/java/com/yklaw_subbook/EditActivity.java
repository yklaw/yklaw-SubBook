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
 * Edit Activity
 * @author Yu Kin Law
 * @version 1.0
 * @see Activity
 * @see DisplayActivity
 */
public class EditActivity extends AppCompatActivity {

    private ArrayList<Subscription> subList;
    int i;

    EditText nameText;
    EditText dateText;
    EditText chargeText;
    EditText commentText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        nameText = findViewById(R.id.name);
        dateText = findViewById(R.id.date);
        chargeText = findViewById(R.id.charge);
        commentText = findViewById(R.id.comment);
        subList = new ArrayList<Subscription>();

    }

    /**
     * Reloads the file
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile();
        i = -1;

        Intent intent = getIntent();
        i = intent.getIntExtra("Location", 0);

        // If subList is not empty, retrieve object with location
        if (subList.isEmpty() == false) {
        Subscription sub = subList.get(i);

        // Set text from subList
        nameText.setText(sub.getName());
        dateText.setText(sub.getDate());
        chargeText.setText(sub.getCharge());
        commentText.setText(sub.getComment());
        }
    }

    /**
     * Save subList to file
     */
    private void saveInFile(Subscription sub) {

        subList.set(i, sub);
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
     * Updates Subscription and save into file
     * Similar structure to saveSub() in MainActivity
     */
    public void updateSub(View view) {

        if (i != -1) {
            String name = nameText.getText().toString();
            String date = dateText.getText().toString();
            String charge = chargeText.getText().toString();
            String comment = commentText.getText().toString();

            if(ValidData.checkName(name) && ValidData.checkDate(date)
                    && ValidData.checkCharge(charge) && ValidData.checkComment(comment)) {
                Subscription sub = new Subscription(name, date, charge, comment);
                saveInFile(sub);

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Subscription Updated", Toast.LENGTH_SHORT);
                toast.show();
            } else {
            /* Error Message */
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Invalid Inputs, Subscription Not Updated", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }

    /**
     * Remove Subscription object and save into file
     * Similar structure to saveSub() in MainActivity
     */
    public void deleteSub(View view) {

        subList.remove(i);
        try {
            FileOutputStream fos = openFileOutput("sub.sav",
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(subList, out);
            out.flush();

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Subscription Deleted", Toast.LENGTH_SHORT);
            toast.show();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }  catch (IOException e) {
            throw new RuntimeException();
        }
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
