package com.booisajerk.getajobcards;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jenniferparker on 7/28/17.
 * <p>
 * Allows user to add a new card_activity to the question db
 */


public class AddCardActivity extends Activity {

    public static final String LOG_TAG = "Parker " + AddCardActivity.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final CardReaderDbHelper cardHelper = new CardReaderDbHelper(this);

        final EditText addQuestionText = (EditText) findViewById(R.id.question_text_add_card);
        final EditText addAnswerText = (EditText) findViewById(R.id.answer_text_add_card);
        final EditText addMoreText = (EditText) findViewById(R.id.more_text_add_card);
        final EditText addCategoryText = (EditText) findViewById(R.id.category_text_add_card);
        Button submitButton = (Button) findViewById(R.id.submit_button);
        Button testButton = (Button) findViewById(R.id.test_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!addQuestionText.getText().toString().isEmpty()) {

                    Log.d(LOG_TAG, "AddQuestionText is: " + addQuestionText.getText().toString());
                    //Make new card_activity
                    Card card = new Card(

                            //TODO need to get the latest card # and increment one to add ID field
                            addQuestionText.getText().toString(),
                            addAnswerText.getText().toString(),
                            addMoreText.getText().toString(),
                            addCategoryText.getText().toString()
                    );


                    cardHelper.addCard(card);

                    Toast.makeText(AddCardActivity.this, "Card created", Toast.LENGTH_LONG).show();


                    addQuestionText.setText(null);
                    addAnswerText.setText(null);
                    addCategoryText.setText(null);
                    addMoreText.setText(null);
                    hideSoftKeyboard(view);

                } else {
                    Toast.makeText(AddCardActivity.this, "Card not made", Toast.LENGTH_LONG).show();
                }
            }
        });


        testButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                Log.d("Reading: ", "Reading all cards..");

                List<Card> cards = cardHelper.getAllCards();

                Toast.makeText(AddCardActivity.this, "Check logs for read our of all db rows", Toast.LENGTH_LONG).show();


                for (Card c : cards) {
                    String log = "Id: " + c.getId() + " ,Question: " + c.getQuestion() + " ,Answer: " +
                            c.getAnswer() + " ,Category: " + c.getCategory() + " ,More: " + c.getMore();
                    Log.d(LOG_TAG, "Result from db: " + log);
                }
            }
        });


    }


    //Utility Methods

    public void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
