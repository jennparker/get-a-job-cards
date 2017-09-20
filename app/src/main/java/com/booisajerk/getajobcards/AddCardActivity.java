package com.booisajerk.getajobcards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jenniferparker on 7/28/17.
 * <p>
 * Allows user to add a new card_activity to the question db
 */


public class AddCardActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Parker " + AddCardActivity.class.getSimpleName();

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

        final EditText addQuestionText = findViewById(R.id.question_text_add_card);
        final EditText addAnswerText = findViewById(R.id.answer_text_add_card);
        final EditText addMoreText = findViewById(R.id.more_text_add_card);
        final EditText addCategoryText = findViewById(R.id.category_text_add_card);
        Button submitButton = findViewById(R.id.submit_button);
        Button listCardsButton = findViewById(R.id.show_questions_button);
        Button addCardsButton = findViewById(R.id.add_questions_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!addQuestionText.getText().toString().isEmpty()) {

                    Log.d(LOG_TAG, "AddQuestionText is: " + addQuestionText.getText().toString());

                    //Make new card_activity
                    Log.d(LOG_TAG, "Adding new card.");
                    Card addedCard = new Card();
                    addedCard.setQuestion(addQuestionText.getText().toString());
                    addedCard.setAnswer(addAnswerText.getText().toString());
                    addedCard.setCategory(addCategoryText.getText().toString());
                    addedCard.setMore(addMoreText.getText().toString());

                    Log.d(LOG_TAG, "Creating new db instance");
                    AppDatabase db = AppDatabase.getInMemoryDatabase(getApplicationContext());
                    Log.d(LOG_TAG, "insertCard called - adding new card to db");
                    db.cardModel().insertCard(addedCard);
                    Log.d(LOG_TAG, "Card count is: " + db.cardModel().countAllCards());

                    Toast.makeText(AddCardActivity.this, "Card created", Toast.LENGTH_LONG).show();

                    Log.d(LOG_TAG, "Resetting add card fields.");
                    addQuestionText.setText(null);
                    addAnswerText.setText(null);
                    addCategoryText.setText(null);
                    addMoreText.setText(null);
                } else {
                    Toast.makeText(AddCardActivity.this, "Card not made", Toast.LENGTH_LONG).show();
                }
            }
        });


        addCardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  populateWithTestData(AppDatabase db) {

                AppDatabase db = AppDatabase.getInMemoryDatabase(getApplicationContext());
                Log.d(LOG_TAG, "Deleting all existing questions");
                db.cardModel().deleteAll();

                Log.d(LOG_TAG, "Adding  questions to db");

                addCard(db, " If your app needs to read the external storage (but not write to it), then you will need to declare the ___________ permission. ", "READ_EXTERNAL_STORAGE", "General", "https:///developer.android.com//training//basics//data-storage//files.html");
                addCard(db, " True or False: You can define an interface in the Fragment class and implement it within the Activity.", "TRUE", "Android", "https://developer.android.com/training/basics/fragments/communicating.html");
                addCard(db, " When your LoaderCallbacks receives a callback to onLoadFinished(), you update your ______  with the new Cursor .", "Adapter", "Java", "");
                addCard(db, "________ storage is best when you want to be sure that neither the user nor other apps can access your files.", "Internal", "Android", "https://developer.android.com/training/basics/data-storage/files.html");
                addCard(db, "_________ storage is the best place for files that don't require access restrictions and for files that you want to share with other apps or allow the user to access with a computer.", "External", "Android", "https://developer.android.com/training/basics/data-storage/files.html");
                addCard(db, "A host activity can capture a fragment instance by calling ____________.", "findFragmentById();", "JavaScript", "https://developer.android.com/training/basics/fragments/communicating.html");
                addCard(db, "An activity can be embedded inside of another activity with a(an) ___________.", "ActivityGroup", "Android", "");
                addCard(db, "Describe the activity lifecycle", "", "Android", "");
                addCard(db, "Describe the fragment activity lifecycle", "", "Android", "");
                addCard(db, "During which fragment lifecycle method does a fragment capture an interface?", "onAttach();", "General", "https://developer.android.com/training/basics/fragments/communicating.html");

                db.cardModel().updateCards();
                Log.d(LOG_TAG, "Updating cards.");
                Log.d(LOG_TAG, "Card count = " + db.cardModel().countAllCards());
            }

        });


        listCardsButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                Log.d("Reading: ", "Reading all cards..");

                AppDatabase db = AppDatabase.getInMemoryDatabase(getApplicationContext());
                List<Card> cards = db.cardModel().loadAllCards();

                Toast.makeText(AddCardActivity.this, "Check logs for read our of all db rows", Toast.LENGTH_LONG).show();

                for (Card c : cards) {
                    String log = "Id: " + c.getId() + " ,Question: " + c.getQuestion() + " ,Answer: " +
                            c.getAnswer() + " ,Category: " + c.getCategory() + " ,More: " + c.getMore();
                    Log.d(LOG_TAG, "Result from db: " + log);
                }
            }
        });
    }

    private static Card addCard(final AppDatabase db, final String question, final String answer
            , final String category, final String more) {
        Card card = new Card();
        card.setQuestion(question);
        card.setAnswer(answer);
        card.setCategory(category);
        card.setMore(more);
        db.cardModel().insertCard(card);
        return card;
    }
}
