package com.booisajerk.getajobcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by jenniferparker on 9/20/17.
 */

public class CategoryActivity extends AppCompatActivity {

    private static final String LOG_TAG = Constants.LOG_TAG_NAME + CategoryActivity.class.getSimpleName();


    private Button androidButton, answerThisButton, bugsButton, dataStructuresButton, effectiveJavaButton, generalButton,
            interviewButton, javaButton, kotlinButton;

    private String categoryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate called");

        setContentView(R.layout.category_activity);

        androidButton = findViewById(R.id.androidCategoryButton);
        dataStructuresButton = findViewById(R.id.dataStructuresCategoryButton);
        effectiveJavaButton = findViewById(R.id.effectiveJavaCategoryButton);
        generalButton = findViewById(R.id.generalCategoryButton);
        interviewButton = findViewById(R.id.interviewCategoryButton);
        javaButton = findViewById(R.id.javaCategoryButton);
        kotlinButton = findViewById(R.id.kotlinCategoryButton);
        answerThisButton = findViewById(R.id.answerThisCategoryButton);
        bugsButton = findViewById(R.id.bugsButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart called");

        //TODO get rid of the duplicate listener code

        androidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.android_category);
                Intent intent = new Intent(CategoryActivity.this, LoadingActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        dataStructuresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.data_structures_category);
                Intent intent = new Intent(CategoryActivity.this, LoadingActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        effectiveJavaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.effective_java_category);
                Intent intent = new Intent(CategoryActivity.this, LoadingActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        generalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.general_category);
                Intent intent = new Intent(CategoryActivity.this, LoadingActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        interviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.interview_category);
                Intent intent = new Intent(CategoryActivity.this, LoadingActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        javaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.java_category);
                Intent intent = new Intent(CategoryActivity.this, LoadingActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        kotlinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.kotlin_category);
                Intent intent = new Intent(CategoryActivity.this, LoadingActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        answerThisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.answer_this_category);
                Intent intent = new Intent(CategoryActivity.this, LoadingActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });

        bugsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = getString(R.string.bugs_category);
                Intent intent = new Intent(CategoryActivity.this, LoadingActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, categoryName);
                Log.d(LOG_TAG, categoryName + " category selected.");
                startActivity(intent);
            }
        });
    }
}