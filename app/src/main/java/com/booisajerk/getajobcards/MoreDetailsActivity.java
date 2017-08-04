package com.booisajerk.getajobcards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by jenniferparker on 7/25/17.
 */


public class MoreDetailsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_details);

        TextView moreText = (TextView) findViewById(R.id.moreDetailsText);

        String moreValue = getIntent().getExtras().getString("more_string");
        moreText.setText(moreValue);

    }
}
