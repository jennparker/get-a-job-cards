package com.booisajerk.getajobcards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

/**
 * Created by jenniferparker on 8/1/17.
 */

public class EditCardActivity extends AppCompatActivity {

    public static final String LOG_TAG = Constants.LOG_TAG_NAME + EditCardActivity.class.getSimpleName();

    private EditText editQuestionText, editAnswerText, editMoreText, editCategoryText;
    private Button submitButton;
    private Card card;
    private String editQuestion, editAnswer, editCategory, editMore, editIdString;
    private int editId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_card);

        card = new Card();

        editQuestionText = findViewById(R.id.question_text_edit_card);
        editAnswerText = findViewById(R.id.answer_text_edit_card);
        editMoreText = findViewById(R.id.more_text_edit_card);
        editCategoryText = findViewById(R.id.category_text_edit_card);
        submitButton = findViewById(R.id.submit_button);

        Log.d(LOG_TAG, "Edit card called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        editQuestion = getIntent().getExtras().getString(Constants.EDIT_TEXT_QUESTION_KEY);
        editAnswer = getIntent().getExtras().getString(Constants.EDIT_TEXT_ANSWER_KEY);
        editCategory = getIntent().getExtras().getString(Constants.EDIT_TEXT_CATEGORY_KEY);
        editMore = getIntent().getExtras().getString(Constants.EDIT_TEXT_MORE_KEY);
        editId = getIntent().getExtras().getInt(Constants.EDIT_TEXT_ID_KEY);

        editIdString = String.valueOf(editId);

        Log.d(LOG_TAG, "intent Edit id check: " + editId + " questions check: " + editQuestion);

        //Set the intent extras retrieved from the Card class
        editQuestionText.setText(editQuestion);
        editAnswerText.setText(editAnswer);
        editMoreText.setText(editMore);
        editCategoryText.setText(editCategory);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(LOG_TAG, "Submitting edited card");

                String newEditQuestionText = editQuestionText.getText().toString();
                String newEditAnswerText = editAnswerText.getText().toString();
                String newEditCategoryText = editCategoryText.getText().toString();
                String newEditMoreText = editMoreText.getText().toString();
                Log.d(LOG_TAG, "Setting question to: " + newEditQuestionText
                + ", setting answer to: " + newEditAnswerText
                + ", setting category to: " + newEditCategoryText
                + ", setting more to: " + newEditMoreText);

                updateCard(newEditQuestionText, newEditAnswerText, newEditCategoryText, newEditMoreText);

                //Hide soft keyboard
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        //TODO how to return to the same card in the flash card list

    }

    private void updateCard(String editQuestion, String editAnswer, String editCategory, String editMore) {
        final FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();

        Log.d(LOG_TAG, "Update card called");

        card.setQuestion(editQuestion);
        card.setAnswer(editAnswer);
        card.setCategory(editCategory);
        card.setMore(editMore);
        card.setId(editId);

        firebaseFirestoreDb.collection(Constants.CARD_COLLECTION_NAME)
                .document(editIdString)
                .set(card, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(LOG_TAG, "Card edited successfully");
                        Toast.makeText(EditCardActivity.this, "Card Updated",
                                Toast.LENGTH_SHORT).show();

                        returnToCard();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(LOG_TAG, "Card edit failed");
            }
        });
    }

    private void returnToCard() {
        Log.d(LOG_TAG, "Returning to the edited card in the card list");
        Intent returnToCardIntent = new Intent(EditCardActivity.this, CardActivity.class);

        returnToCardIntent.putExtra(Constants.EDIT_TEXT_QUESTION_KEY, card.getQuestion());
        returnToCardIntent.putExtra(Constants.EDIT_TEXT_ANSWER_KEY, card.getAnswer());
        returnToCardIntent.putExtra(Constants.EDIT_TEXT_MORE_KEY, card.getMore());
        returnToCardIntent.putExtra(Constants.EDIT_TEXT_CATEGORY_KEY, card.getCategory());
        returnToCardIntent.putExtra(Constants.EDIT_TEXT_ID_KEY, card.getId());

        startActivity(returnToCardIntent);
    }
}
