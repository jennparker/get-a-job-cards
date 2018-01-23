package com.booisajerk.getajobcards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by jenniferparker on 8/1/17.
 */

public class EditCardActivity extends AppCompatActivity {

    public static final String LOG_TAG = "Parker " + EditCardActivity.class.getSimpleName();

    EditText editQuestionText;
    EditText editAnswerText;
    EditText editMoreText;
    EditText editCategoryText;
    Button submitButton;
    private Card card;

    AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_card);

        //Instantiate a new card to insert the intent values into
        card = new Card();

        editQuestionText = findViewById(R.id.question_text_edit_card);
        editAnswerText = findViewById(R.id.answer_text_edit_card);
        editMoreText = findViewById(R.id.more_text_edit_card);
        editCategoryText = findViewById(R.id.category_text_edit_card);
        submitButton = findViewById(R.id.submit_button);



        Log.d(LOG_TAG, "Edit card called");


        Log.d(LOG_TAG, "Getting db instance");
        db = AppDatabase.getInMemoryDatabase(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();


        //Get the intent extras from the Card class
        editQuestionText.setText(getIntent().getExtras().getString("current_question"));
        editAnswerText.setText(getIntent().getExtras().getString("current_answer"));
        editMoreText.setText(getIntent().getExtras().getString("current_more"));
        editCategoryText.setText(getIntent().getExtras().getString("current_category"));

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(LOG_TAG, "Submitting edited text");


                //Insert the values into the card model
                card.setQuestion(editQuestionText.getText().toString());
                card.setAnswer(editAnswerText.getText().toString());
                card.setCategory(editCategoryText.getText().toString());
                card.setMore(editMoreText.getText().toString());

                Log.d(LOG_TAG, "Inserting values into cardModel");
                db.cardModel().insertCard(card);

                final AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
                dialog.setPositiveButton("Great!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                //Hide soft keyboard
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        //TODO return to the same card in the flash card list

    }

}
