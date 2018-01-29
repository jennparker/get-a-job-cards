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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by jenniferparker on 7/28/17.
 * <p>
 * Allows user to add a new card_activity to the question db
 */


public class AddCardActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Parker " + AddCardActivity.class.getSimpleName();
    private EditText addQuestionText;
    private EditText addAnswerText;
    private EditText addCategoryText;
    private EditText addMoreText;
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

        FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();

        DocumentReference docRef = firebaseFirestoreDb.collection("cards").document("2");
//        DocumentReference docRef = firebaseFirestoreDb.collection("cards")
//                .orderBy("id", Query.Direction.DESCENDING).limit(1);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        Log.d(LOG_TAG, "DocumentSnapshot data: " + task.getResult().getData());
                    } else {
                        Log.d(LOG_TAG, "No such document");
                    }
                } else {
                    Log.d(LOG_TAG, "get failed with ", task.getException());
                }
            }
        });

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
                    int id = 0;

                    final FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();

                    Card card = new Card(questionString, answerString, categoryString, moreString, id);
                    firebaseFirestoreDb.collection("cards").add(card);

                    Toast.makeText(getApplicationContext(), "New card added", Toast.LENGTH_LONG).show();

                    Log.d(LOG_TAG, "Resetting add card fields.");
                    addQuestionText.setText(null);
                    addAnswerText.setText(null);
                    addCategoryText.setText(null);
                    addMoreText.setText(null);
                }
            }
        });
    }

    private static void addID() {
        FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();
        for (int i = 1; i < 448; i++) {
            DocumentReference cardRef = firebaseFirestoreDb.collection("cards").document(String.valueOf(i));
            Log.d(LOG_TAG, "Card reference is: " + cardRef.getId() + " updating ID column");
            cardRef.update("id", i);
        }
    }

    private String getMaxID() {
        FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();
        String firstVal = null;

        firstVal = firebaseFirestoreDb.collection("cards").orderBy("id", Query.Direction.DESCENDING)
                .get().getResult().getDocuments().get(0).get("id").toString();
        Log.d(LOG_TAG, firstVal);
        return firstVal;
    }

    //TODO Fix this
//    private Card getResultsByCategory(String category) {
//        FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();
//        firebaseFirestoreDb.collection("cards")
//                .whereEqualTo("category", category)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                Log.d(LOG_TAG, document.getId() + " = " + document.getData());
//                            }
//                        }
//                        else { Log.d(LOG_TAG, "Error getting docs: " + task.getException());
//
//                        }
//                    }
//                });
//        return Card;
//    }
}

