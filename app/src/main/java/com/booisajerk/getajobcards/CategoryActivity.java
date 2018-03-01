package com.booisajerk.getajobcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by jenniferparker on 9/20/17.
 */

public class CategoryActivity extends AppCompatActivity {

    private static final String LOG_TAG = Constants.LOG_TAG_NAME + CategoryActivity.class.getSimpleName();


    private Button androidButton, effectiveJavaButton, generalButton,
            interviewButton, javaButton, sqlButton;

    private String categoryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate called");

        setContentView(R.layout.category_activity);

        androidButton = (Button) findViewById(R.id.androidCategoryButton);
        effectiveJavaButton = (Button) findViewById(R.id.effectiveJavaCategoryButton);
        generalButton = (Button) findViewById(R.id.generalCategoryButton);
        interviewButton = (Button) findViewById(R.id.interviewCategoryButton);
        javaButton = (Button) findViewById(R.id.javaCategoryButton);
        sqlButton = (Button) findViewById(R.id.sqlCategoryButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart called");

        //Listening for the various Category click events

        androidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.android_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        effectiveJavaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.effective_java_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        generalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.general_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        interviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.interview_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        javaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.java_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        sqlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.sql_category);
                Intent intent = new Intent(CategoryActivity.this, CardActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });
    }
}
