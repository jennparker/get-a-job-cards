package com.booisajerk.getajobcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Parker" + MainActivity.class.getSimpleName();

    //TODO add app bar to card_activity screen
    //TODO create a save question for later button
    //TODO create a add a new question button
    //TODO create a back button
    //TODO switch to fragments
    //TODO handle onRotation
    //TODO card_activity flip animation
    //TODO use ViewPager for cards
    //TODO option to select by category
    //TODO shuffle questions
    //TODO how to control new line break
    //TODO add hyperlinks
    //TODO add ability to click off more view
    //TODO add id column

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(LOG_TAG, "OnCreate called");


        Button categorySelectionButton = (Button) findViewById(R.id.categorySelectionButton);
        categorySelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO display category checked text box


            }
        });


        Button allCardsButton = (Button) findViewById(R.id.allCardsButton);
        allCardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CardActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

        if (id == R.id.action_add_card) {
            Intent addCardIntent = new Intent(MainActivity.this, AddCardActivity.class);
            startActivity(addCardIntent);

        }

        return super.onOptionsItemSelected(item);
    }

}
