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
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Parker " + MainActivity.class.getSimpleName();

    //TODO card_activity flip animation
    //TODO use ViewPager for cards
    //TODO option to select by category
    //TODO shuffle questions
    //TODO how to control new line break
    //TODO add hyperlinks
    //TODO long strings being cut off - fix
    //TODO work in background thread
    //TODO handle delete
    //TODO handle edit
    //TODO handle add
    //TODO add questions to the db
    //TODO add scrolling for long answers
    //TODO questions resetting on rotation
    //TODO use singleton design pattern for accessing db

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

                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                Log.d(LOG_TAG, "Starting CategoryActivity activity");
                startActivity(intent);
            }
        });

        Button allCardsButton = (Button) findViewById(R.id.allCardsButton);
        allCardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CardActivity.class);
                intent.putExtra("origin", "all");
                Log.d(LOG_TAG, "Starting Card activity.");
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
        int id = item.getItemId();

        if (id == R.id.action_add_card) {
            Log.d(LOG_TAG, "Add card selected.");
            Intent addCardIntent = new Intent(MainActivity.this, AddCardActivity.class);
            startActivity(addCardIntent);
        }

        if (id == R.id.action_add_study_cards) {
            Log.d(LOG_TAG, "Add study cards called.");
            Intent loadCardIntent = new Intent(this, LoadCards.class);
            startActivity(loadCardIntent);
        }

        if (id == R.id.action_show_questions) {
            Log.d("Reading: ", "Reading all cards..");

            AppDatabase db = AppDatabase.getInMemoryDatabase(getApplicationContext());
            List<Card> cards = db.cardModel().loadAllCards();

            Toast.makeText(this, "Check logs for read our of all db rows", Toast.LENGTH_LONG).show();

            for (Card c : cards) {
                String log = "Id: " + c.getId() + " ,Question: " + c.getQuestion() + " ,Answer: " +
                        c.getAnswer() + " ,Category: " + c.getCategory() + " ,More: " + c.getMore();
                Log.d(LOG_TAG, "Result from db: " + log);
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
