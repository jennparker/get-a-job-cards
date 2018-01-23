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

    Button androidButton, cssButton, databaseButton, effectiveJavaButton, generalButton, gitButton, htmlButton,
            interviewButton, javaButton, javaScriptButton, sqlButton;

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
        cssButton = (Button) findViewById(R.id.cssCategoryButton);
        databaseButton = (Button) findViewById(R.id.databaseCategoryButton);
        effectiveJavaButton = (Button) findViewById(R.id.effectiveJavaCategoryButton);
        generalButton = (Button) findViewById(R.id.generalCategoryButton);
        gitButton = (Button) findViewById(R.id.gitCategoryButton);
        htmlButton = (Button) findViewById(R.id.htmlCategoryButton);
        interviewButton = (Button) findViewById(R.id.interviewCategoryButton);
        javaButton = (Button) findViewById(R.id.javaCategoryButton);
        javaScriptButton = (Button) findViewById(R.id.javaScriptCategoryButton);
        sqlButton = (Button) findViewById(R.id.sqlCategoryButton);
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

        cssButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.css_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra("origin", categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        databaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.database_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra("origin", categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        effectiveJavaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.effective_java_category);
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
                startActivity(intent);
            }
        });

        gitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.git_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra("origin", categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        htmlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.html_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra("origin", categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        interviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.interview_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra("origin", categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        javaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.java_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra("origin", categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        javaScriptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.javaScript_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra("origin", categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        sqlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.sql_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra("origin", categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });
    }
}
