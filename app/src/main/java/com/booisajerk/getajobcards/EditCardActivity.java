package com.booisajerk.getajobcards;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by jenniferparker on 8/1/17.
 */

public class EditCardActivity extends Activity{

    public static final String LOG_TAG = "Parker " + EditCardActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_card);

    }

    @Override
    protected void onStart() {
        super.onStart();

        final CardReaderDbHelper cardHelper = new CardReaderDbHelper(this);

        final EditText editQuestionText = (EditText) findViewById(R.id.question_text_edit_card);
        final EditText editAnswerText = (EditText) findViewById(R.id.answer_text_edit_card);
        final EditText editMoreText = (EditText) findViewById(R.id.more_text_edit_card);
        final EditText editCategoryText = (EditText) findViewById(R.id.category_text_edit_card);
        Button submitButton = (Button) findViewById(R.id.submit_button);


        editQuestionText.setText(getIntent().getExtras().getString("current_question"));
        editAnswerText.setText(getIntent().getExtras().getString("current_answer"));
        editMoreText.setText(getIntent().getExtras().getString("current_more"));
        editCategoryText.setText(getIntent().getExtras().getString("current_category"));
        final int currentId = getIntent().getExtras().getInt("current_id");

        submitButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Card card = new Card(
                    editQuestionText.getText().toString(), editAnswerText.getText().toString(),
                    editMoreText.getText().toString(), editCategoryText.getText().toString());

            Log.d(LOG_TAG, "New Question: " + editQuestionText.getText().toString() +
            " New Answer: " + editAnswerText.getText().toString()
                    + " New More: " + editMoreText.getText().toString()
            + " New Category: " + editCategoryText.getText().toString()
                 +   " New Id: " + currentId);

                    cardHelper.updateCard(card);

            Toast.makeText(EditCardActivity.this, "Card edited", Toast.LENGTH_LONG).show();

            editQuestionText.setText(null);
            editAnswerText.setText(null);
            editCategoryText.setText(null);
            editMoreText.setText(null);

            //Hide soft keyboard
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);        }
    });
    }

}
