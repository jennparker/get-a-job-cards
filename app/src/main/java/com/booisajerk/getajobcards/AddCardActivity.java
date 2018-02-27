package com.booisajerk.getajobcards;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by jenniferparker on 7/28/17.
 * <p>
 * Allows user to add a new card_activity to the question db
 */

public class AddCardActivity extends AppCompatActivity {

    private static final String LOG_TAG = Constants.LOG_TAG_NAME + AddCardActivity.class.getSimpleName();

    private EditText addQuestionText, addAnswerText, addCategoryText, addMoreText;
    private Button submitButton;

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

        addQuestionText = findViewById(R.id.question_text_add_card);
        addAnswerText = findViewById(R.id.answer_text_add_card);
        addMoreText = findViewById(R.id.more_text_add_card);
        addCategoryText = findViewById(R.id.category_text_add_card);
        submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!addQuestionText.getText().toString().isEmpty()) {

                    String questionString = addQuestionText.getText().toString();
                    String answerString = addAnswerText.getText().toString();
                    String categoryString = addCategoryText.getText().toString();
                    String moreString = addMoreText.getText().toString();


                    addNewCard(questionString, answerString, categoryString, moreString);

                    Log.d(LOG_TAG, "Resetting add card fields.");
                    addQuestionText.setText(null);
                    addAnswerText.setText(null);
                    addCategoryText.setText(null);
                    addMoreText.setText(null);
                }
            }
        });
    }

    //TODO delete this
    private static void addID() {
        FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();
        for (int i = 1; i < 448; i++) {
            DocumentReference cardRef = firebaseFirestoreDb.collection(Constants.CARD_COLLECTION_NAME).document(String.valueOf(i));
            Log.d(LOG_TAG, "Card reference is: " + cardRef.getId() + " updating ID column");
            cardRef.update("id", i);
        }
    }

    //TODO This crashes
    private String getMaxID() {
        FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();
        String firstVal = null;

        firstVal = firebaseFirestoreDb.collection(Constants.CARD_COLLECTION_NAME).orderBy("id", Query.Direction.DESCENDING)
                .get().getResult().getDocuments().get(0).get("id").toString();
        Log.d(LOG_TAG, firstVal);
        return firstVal;
    }

    private void addNewCard(String question, String answer, String category, String more) {
        FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();

        Map<String, Object> newCard = new HashMap<>();
        newCard.put(Constants.CARD_QUESTION, question);
        newCard.put(Constants.CARD_ANSWER, answer);
        newCard.put(Constants.CARD_CATEGORY, category);
        newCard.put(Constants.CARD_MORE, more);

        firebaseFirestoreDb.collection(Constants.CARD_COLLECTION_NAME).add(newCard)

                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(LOG_TAG, "New card added. DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(LOG_TAG, "Error adding document", e);
                    }
                });
    }
}