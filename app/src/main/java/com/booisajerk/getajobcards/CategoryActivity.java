package com.booisajerk.getajobcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * Created by jenniferparker on 9/20/17.
 */

public class CategoryActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Parker " + CategoryActivity.class.getSimpleName();

    Button androidButton, generalButton, javaButton, javaScriptButton;
    String categoryName;

    AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate called");

        setContentView(R.layout.category_activity);

        Log.d(LOG_TAG, "Initializing db");
        db = AppDatabase.getInMemoryDatabase(getApplicationContext());

        androidButton = (Button) findViewById(R.id.androidCategoryButton);
        generalButton = (Button) findViewById(R.id.generalCategoryButton);
        javaButton = (Button) findViewById(R.id.javaCategoryButton);
        javaScriptButton = (Button) findViewById(R.id.javaScriptCategoryButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart called");

        androidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.android_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra("origin", categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        generalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.general_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra("origin", categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);            }
        });

        javaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.java_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra("origin", categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);            }
        });

        javaScriptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.javaScript_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra("origin", categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);            }
        });
    }
}
