package com.booisajerk.getajobcards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jenniferparker on 7/28/17.
 * <p>
 * Allows user to add a new card_activity to the question db
 */


public class AddCardActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Parker " + AddCardActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate called.");
        setContentView(R.layout.add_card);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        final EditText addQuestionText = findViewById(R.id.question_text_add_card);
        final EditText addAnswerText = findViewById(R.id.answer_text_add_card);
        final EditText addMoreText = findViewById(R.id.more_text_add_card);
        final EditText addCategoryText = findViewById(R.id.category_text_add_card);
        Button submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!addQuestionText.getText().toString().isEmpty()) {

                    Log.d(LOG_TAG, "AddQuestionText is: " + addQuestionText.getText().toString());

                    //Make new card_activity
                    Log.d(LOG_TAG, "Adding new card.");
                    Card addedCard = new Card();
                    addedCard.setQuestion(addQuestionText.getText().toString());
                    addedCard.setAnswer(addAnswerText.getText().toString());
                    addedCard.setCategory(addCategoryText.getText().toString());
                    addedCard.setMore(addMoreText.getText().toString());

                    Log.d(LOG_TAG, "Creating new db instance");
                    AppDatabase db = AppDatabase.getInMemoryDatabase(getApplicationContext());
                    Log.d(LOG_TAG, "insertCard called - adding new card to db");
                    db.cardModel().insertCard(addedCard);
                    Log.d(LOG_TAG, "Card count is: " + db.cardModel().countAllCards());

                    Toast.makeText(AddCardActivity.this, "Card created", Toast.LENGTH_LONG).show();

                    Log.d(LOG_TAG, "Resetting add card fields.");
                    addQuestionText.setText(null);
                    addAnswerText.setText(null);
                    addCategoryText.setText(null);
                    addMoreText.setText(null);
                } else {
                    Toast.makeText(AddCardActivity.this, "Card not made", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
