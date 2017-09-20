package com.booisajerk.getajobcards;

import android.app.Activity;
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

    AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "Edit card called");

        setContentView(R.layout.edit_card);

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

        editQuestionText = (EditText) findViewById(R.id.question_text_edit_card);
        editAnswerText = (EditText) findViewById(R.id.answer_text_edit_card);
        editMoreText = (EditText) findViewById(R.id.more_text_edit_card);
        editCategoryText = (EditText) findViewById(R.id.category_text_edit_card);
        submitButton = (Button) findViewById(R.id.submit_button);


        editQuestionText.setText(getIntent().getExtras().getString("current_question"));
        editAnswerText.setText(getIntent().getExtras().getString("current_answer"));
        editMoreText.setText(getIntent().getExtras().getString("current_more"));
        editCategoryText.setText(getIntent().getExtras().getString("current_category"));
        final int currentId = getIntent().getExtras().getInt("current_id");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Log.d(LOG_TAG, "Submitting edited text");

                Card card = new Card();
                card.setQuestion(editQuestionText.getText().toString());
                card.setAnswer(editAnswerText.getText().toString());
                card.setCategory(editCategoryText.getText().toString());
                card.setMore(editMoreText.getText().toString());

                db.cardModel().insertCard(card);

                //TODO need this to refresh

                Toast.makeText(EditCardActivity.this, "Card edited", Toast.LENGTH_LONG).show();

                editQuestionText.setText(null);
                editAnswerText.setText(null);
                editCategoryText.setText(null);
                editMoreText.setText(null);

                //Hide soft keyboard
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
    }

}
