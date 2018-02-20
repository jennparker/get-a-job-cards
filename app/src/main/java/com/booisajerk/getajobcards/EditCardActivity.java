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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by jenniferparker on 8/1/17.
 */

public class EditCardActivity extends AppCompatActivity {

    public static final String LOG_TAG = Constants.LOG_TAG_NAME + EditCardActivity.class.getSimpleName();

    private EditText editQuestionText,editAnswerText,editMoreText,editCategoryText;
    private Button submitButton;
    private Card card;
    private String editQuestion,editAnswer,editCategory,editMore;

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

        //Get the intent extras from the Card class
        editQuestionText.setText(editQuestion);
        editAnswerText.setText(editAnswer);
        editMoreText.setText(editMore);
        editCategoryText.setText(editCategory);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(LOG_TAG, "Submitting edited text");

                updateCard(editQuestion, editAnswer, editCategory, editMore);

           //     final FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();

               // CollectionReference cards = firebaseFirestoreDb.collection("cards");

                //TODO edit card in Firebase - probably need to add a search for the question in Firebase here...

//                Map<String, Object> card = new HashMap<>();
//                card.put("question", editQuestionText.getText().toString());
//                card.put("answer", editAnswerText.getText().toString());
//                card.put("category", editCategoryText.getText().toString());
//                card.put("more", editMoreText.getText().toString());
                //TODO How to set the id to the highest card # +1
               // cards.document("card").set(card);


//                //Insert the values into the card model
//                card.setQuestion(editQuestionText.getText().toString());
//                card.setAnswer(editAnswerText.getText().toString());
//                card.setCategory(editCategoryText.getText().toString());
//                card.setMore(editMoreText.getText().toString());

            //    Log.d(LOG_TAG, "Inserting values into cardModel");
                // db.cardModel().insertCard(card);

//                final AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
//                dialog.setPositiveButton("Great!", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();

                //Hide soft keyboard
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        //TODO return to the same card in the flash card list

    }

    private void updateCard(String editQuestion, String editAnswer, String editCategory, String editMore) {
        final FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();

        Log.d(LOG_TAG, "Update card called");

        //TODO need to add a where statement or something here.
        DocumentReference card = firebaseFirestoreDb.collection(Constants.CARD_COLLECTION_NAME).document(Constants.CARD_DOCUMENT_NAME);
        card.update(Constants.CARD_QUESTION, editQuestion);
        card.update(Constants.CARD_ANSWER, editAnswer);
        card.update(Constants.CARD_CATEGORY, editCategory);
        card.update(Constants.CARD_MORE, editMore)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditCardActivity.this, "Updated Successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
