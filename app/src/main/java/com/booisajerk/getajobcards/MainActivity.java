package com.booisajerk.getajobcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = Constants.LOG_TAG_NAME + MainActivity.class.getSimpleName();

    private Toolbar toolbar;
    private Button categorySelectionButton, allCardsButton;

    //TODO card_activity flip animation
    //TODO use ViewPager for cards
    //TODO how to control new line break
    //TODO add hyperlinks

    //TODO work in background thread
    //TODO questions resetting on rotation
    //TODO use singleton design pattern for accessing db

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(LOG_TAG, "OnCreate called");

        categorySelectionButton = (Button) findViewById(R.id.categorySelectionButton);
        categorySelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                Log.d(LOG_TAG, "Starting CategoryActivity activity");
                startActivity(intent);
            }
        });

        allCardsButton = (Button) findViewById(R.id.allCardsButton);
        allCardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CardActivity.class);
                intent.putExtra(Constants.CATEGORY_STATE, getString(R.string.all_categories));
                Log.d(LOG_TAG,  "All category selected");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "Initialize instance of Firebase");
        FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();

        int id = item.getItemId();

        if (id == R.id.action_add_card) {
            Log.d(LOG_TAG, "Add card selected.");
            Intent addCardIntent = new Intent(MainActivity.this, AddCardActivity.class);
            startActivity(addCardIntent);
        }

        if (id == R.id.action_add_card_to_firebase) {

            // Create a test card
            Map<String, Object> card = new HashMap<>();
            card.put(Constants.CARD_QUESTION, "test question");
            card.put(Constants.CARD_ANSWER, "test answer");
            card.put(Constants.CARD_CATEGORY, "Git");
            card.put(Constants.CARD_MORE, "test more");

            firebaseFirestoreDb.collection(Constants.CARD_COLLECTION_NAME)
                    .add(card)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(LOG_TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(LOG_TAG, "Error adding document", e);
                        }
                    });

            Toast.makeText(this, "Card added to Firebase", Toast.LENGTH_LONG).show();
        }

        if (id == R.id.action_read_card_from_firebase) {

            firebaseFirestoreDb.collection(Constants.CARD_COLLECTION_NAME)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    Log.d(LOG_TAG, document.getId() + " => " + document.getData());
                                }
                            } else {
                                Log.w(LOG_TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
            Toast.makeText(this, "Check Logs to see retrieved card", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
