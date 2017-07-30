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

    //TODO add app bar to card screen
    //TODO create a save question for later button
    //TODO create a add a new question button
    //TODO create a back button
    //TODO switch to fragments
    //TODO handle onRotation
    //TODO card flip animation
    //TODO use ViewPager for cards
    //TODO store questions in SQL db
    //TODO option to select by category
    //TODO how to use a url without messing up parsing
    //TODO shuffle questions
    //TODO how to control new line break

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(LOG_TAG, "OnCreate called");

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, FileHelper.class);
//                startActivity(intent);
//            }
//        });

        Button categorySelectionButton = (Button) findViewById(R.id.categorySelectionButton);
        categorySelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO do something

            }
        });


        Button allCardsButton = (Button) findViewById(R.id.allCardsButton);
        allCardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FileHelper.class);
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
