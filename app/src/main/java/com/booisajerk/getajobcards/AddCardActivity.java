package com.booisajerk.getajobcards;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by jenniferparker on 7/28/17.
 * <p>
 * Allows user to add a new card_activity to the question db
 */

public class AddCardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String LOG_TAG = Constants.LOG_TAG_NAME + AddCardActivity.class.getSimpleName();

    private EditText addQuestionText, addAnswerText, addMoreText;
    private Spinner addCategorySpinner;
    private String selectedCategory;

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
        addCategorySpinner = findViewById(R.id.category_spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCategorySpinner.setAdapter(adapter);
        addCategorySpinner.setOnItemSelectedListener(this);
        Button submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!addQuestionText.getText().toString().isEmpty()) {

                    String questionString = addQuestionText.getText().toString();
                    String answerString = addAnswerText.getText().toString();
                    String categoryString = selectedCategory;
                    String moreString = addMoreText.getText().toString();

                    addNewCard(questionString, answerString, categoryString, moreString);

                    Log.d(LOG_TAG, "Resetting add card fields.");
                    addQuestionText.setText(null);
                    addAnswerText.setText(null);
                    addCategorySpinner.setSelection(0);
                    addMoreText.setText(null);
                }
            }
        });
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCategory = parent.getItemAtPosition(position).toString();
        Log.d(LOG_TAG, "Selected category is: " + selectedCategory);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(LOG_TAG, "Nothing selected - do something with this.");
    }

}