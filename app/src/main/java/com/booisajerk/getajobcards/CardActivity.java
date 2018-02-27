package com.booisajerk.getajobcards;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.booisajerk.getajobcards.Constants.CATEGORY_STATE;
import static com.booisajerk.getajobcards.Constants.EDIT_TEXT_ID_KEY;
//TODO when to use static import for constants

/**
 * Created by jenniferparker on 7/30/17.
 */

public class CardActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = Constants.LOG_TAG_NAME + CardActivity.class.getSimpleName();
    private Toolbar toolbar;

    private boolean isShowAnswer;
    private boolean hasMoreContent = false;
    private Boolean isFabOpen = false;
    private TextView cardCategoryText, cardQuestionText, cardAnswerText, moreButton;
    private Button advanceButton;
    private String moreValue, category, questionId;
    int cardCount, cardNum;
    private Boolean cardsPopulated = false;

    DocumentReference docRef;

    ArrayList<Card> cardList;

    private Card card;
    private FloatingActionButton optionsFab, editCardFab, deleteCardFab, addCardFab;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Log.d(LOG_TAG, "onSaveInstanceState called. Adding current values");
//        outState.putString(Constants.INSTANCE_STATE_CATEGORY_KEY, cardCategoryText.getText().toString());
//        outState.putString(Constants.INSTANCE_STATE_QUESTION_KEY, cardQuestionText.getText().toString());
//        outState.putString(Constants.INSTANCE_STATE_ANSWER_KEY, cardAnswerText.getText().toString());
//        outState.putString(Constants.INSTANCE_STATE_MORE_KEY, cardMoreText.getText().toString());
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "OnCreate Called");

        setContentView(R.layout.card_activity);



        cardList = new ArrayList<Card>();

        Log.d(LOG_TAG, "Creating new instance of firestore");
        FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();

        cardNum = 1;
        advanceButton = (Button) findViewById(R.id.advanceButton);
        moreButton = (TextView) findViewById(R.id.moreButton);
        optionsFab = (FloatingActionButton) findViewById(R.id.fabOptions);
        deleteCardFab = (FloatingActionButton) findViewById(R.id.fabDelete);
        editCardFab = (FloatingActionButton) findViewById(R.id.fabEdit);
        addCardFab = (FloatingActionButton) findViewById(R.id.fabAdd);

//        if (savedInstanceState != null) {
//            cardCategoryText.setText(savedInstanceState.getString(Constants.INSTANCE_STATE_CATEGORY_KEY));
//            cardQuestionText.setText(savedInstanceState.getString(Constants.INSTANCE_STATE_QUESTION_KEY));
//            cardAnswerText.setText(savedInstanceState.getString(Constants.INSTANCE_STATE_ANSWER_KEY));
//            moreButton.setText(savedInstanceState.getString(Constants.INSTANCE_STATE_MORE_KEY));
//            Log.d(LOG_TAG, "SavedInstanceState is not null. CareQuestionText = "
//                    + cardQuestionText.getText().toString());
//        } else {
//            Log.d(LOG_TAG, "savedInstanceState is null. No saved values");
//        }

        if (getIntent().getExtras() != null) {

            category = getIntent().getExtras().getString(CATEGORY_STATE);
            Log.d(LOG_TAG, "Getting values from Category intent");

            if (!cardsPopulated) {
                if (category != null) {
                    Log.d(LOG_TAG, "Selected category is " + category);
                    if (category.equals("all")) {
                        Log.d(LOG_TAG, "Loading ALL categories.");

                        firebaseFirestoreDb.collection(Constants.CARD_COLLECTION_NAME)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (DocumentSnapshot document : task.getResult()) {
                                                cardCount = task.getResult().size();
                                                card = document.toObject(Card.class);

                                                Log.d(LOG_TAG, "Card question is: " + card.getQuestion());

                                                cardList.add(card);
                                            }
                                            cardsPopulated = true;

                                            cardCategoryText = (TextView) findViewById(R.id.categoryText);
                                            cardQuestionText = (TextView) findViewById(R.id.cardQuestionText);
                                            cardQuestionText.setMovementMethod(new ScrollingMovementMethod());
                                            cardAnswerText = (TextView) findViewById(R.id.cardAnswerText);
                                            cardAnswerText.setMovementMethod(new ScrollingMovementMethod());

                                        } else {
                                            Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                                            cardsPopulated = false;
                                        }
                                    }
                                });
                    } else {
                        Log.d(LOG_TAG, "Calling category cards for: " + category);
                        firebaseFirestoreDb.collection(Constants.CARD_COLLECTION_NAME)
                                .whereEqualTo(Constants.CARD_CATEGORY, category)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (DocumentSnapshot document : task.getResult()) {
                                                cardCount = task.getResult().size();
                                                card = document.toObject(Card.class);

                                                Log.d(LOG_TAG, "Category specific card question is: " + card.getQuestion());
                                                Log.d(LOG_TAG, "Category card count is: " + cardCount);

                                                cardList.add(card);
                                                cardsPopulated = true;

                                                cardCategoryText = (TextView) findViewById(R.id.categoryText);
                                                cardQuestionText = (TextView) findViewById(R.id.cardQuestionText);
                                                cardQuestionText.setMovementMethod(new ScrollingMovementMethod());
                                                cardAnswerText = (TextView) findViewById(R.id.cardAnswerText);
                                                cardAnswerText.setMovementMethod(new ScrollingMovementMethod());

                                            }

                                        } else {
                                            Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                    }
                }
            } else {
                cardsPopulated = false;
                Log.d(LOG_TAG, "Intent is null. Fix this.");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "OnStart called");

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        optionsFab.setOnClickListener(this);
        addCardFab.setOnClickListener(this);
        editCardFab.setOnClickListener(this);
        deleteCardFab.setOnClickListener(this);

        advanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isShowAnswer) {
                    displayAnswer();
                } else {
                    if (cardNum < cardCount) {
                        Log.d(LOG_TAG, "CardNum of: " + cardNum + " is less than cardCount of "
                                + cardCount + ". Calling displayQuestion");
                        displayQuestion();
                    }
                }
            }
        });

        //TODO Make the button open the link
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fabOptions:

                animateFAB();
                break;

            case R.id.fabAdd:

                Intent addCardIntent = new Intent(this, AddCardActivity.class);
                startActivity(addCardIntent);
                Log.d(LOG_TAG, "Add Fab pressed");
                break;

            case R.id.fabEdit:

                Intent editCardintent = new Intent(CardActivity.this, EditCardActivity.class);
                if (card != null) {
                    editCardintent.putExtra(Constants.EDIT_TEXT_QUESTION_KEY, card.getQuestion());
                    editCardintent.putExtra(Constants.EDIT_TEXT_ANSWER_KEY, card.getAnswer());
                    editCardintent.putExtra(Constants.EDIT_TEXT_MORE_KEY, card.getMore());
                    editCardintent.putExtra(Constants.EDIT_TEXT_CATEGORY_KEY, card.getCategory());
                    editCardintent.putExtra(Constants.EDIT_TEXT_ID_KEY, card.getId());

                    Log.d(LOG_TAG, "This card's id is: " + card.getId());

                    startActivity(editCardintent);

                    Log.d(LOG_TAG, "Edit Fab pressed");
                    break;
                }

            case R.id.fabDelete:

                //TODO delete card in Firebase
                final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                dialog.setPositiveButton(R.string.card_delete_confirmation, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d(LOG_TAG, "Okay, we're gonna delete it...");
                        deleteCard();

                        Toast.makeText(getApplicationContext(),
                                "Hope you didn't want that - it's gone!", Toast.LENGTH_LONG);
                    }
                });

                dialog.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
        }
    }


    private void displayQuestion() {
        Log.d(LOG_TAG, "Display question called");

        getCurrentCard();
        cardAnswerText.setVisibility(View.INVISIBLE);
        advanceButton.setText(R.string.show_answer_text);
        Log.d(LOG_TAG, "Setting isShowAnswer to true");
        isShowAnswer = true;
        cardQuestionText.setTextColor(getResources().getColor(R.color.textMain));
    }

    private void displayAnswer() {
        Log.d(LOG_TAG, "Display answer called");
        cardAnswerText.setVisibility(View.VISIBLE);
        cardQuestionText.setTextColor(getResources().getColor(R.color.textBackground));
        advanceButton.setText(R.string.nextCardButtonText);
        Log.d(LOG_TAG, "Setting isShowAnswer to false");
        isShowAnswer = false;
        cardNum++;
        Log.d(LOG_TAG, "Setting new cardNum to " + cardNum);

    }

    private void getCurrentCard() {

        Log.d(LOG_TAG, "Get current card called.");

        card = cardList.get(cardNum);


        Log.d(LOG_TAG, "Card's Category is: " + card.getCategory() + ". Selected category is " + category);

        Log.d(LOG_TAG, "Get Current Card: Current card id is: " + card.getId() + " question is: " + card.getQuestion());

        cardQuestionText.setText(card.getQuestion());
        cardAnswerText.setText(card.getAnswer());
        cardCategoryText.setText(card.getCategory());
        moreValue = card.getMore();

        if (hasMoreContent()) {
            moreButton.setVisibility(View.VISIBLE);

            if (!isMoreHttp()) {
                moreButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        final AlertDialog.Builder dialog = new AlertDialog.Builder(CardActivity.this);
                        dialog.setPositiveButton(R.string.okay, null)
                                .setMessage(moreValue)
                                .create();

                        dialog.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });

            } else {

                Log.d(LOG_TAG, "Showing a link in dialog");
                moreButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        final AlertDialog.Builder dialog = new AlertDialog.Builder(CardActivity.this);

                        final SpannableString linkString = new SpannableString(moreValue);
                        Linkify.addLinks(linkString, Linkify.ALL);
                        // dialog.setPositiveButton(R.string.open_link_text, null)
                        dialog.setMessage(linkString)
                                .create();


                        dialog.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        //TODO must set movement method after show to make link clickable
                    }
                });
            }

        } else {
            Log.d(LOG_TAG, "Setting more button to invisible");
            moreButton.setVisibility(View.INVISIBLE);
        }
    }

    public void animateFAB() {

        if (isFabOpen) {
            optionsFab.startAnimation(rotate_backward);
            addCardFab.startAnimation(fab_close);
            editCardFab.startAnimation(fab_close);
            deleteCardFab.startAnimation(fab_close);
            addCardFab.setClickable(false);
            editCardFab.setClickable(false);
            deleteCardFab.setClickable(false);
            isFabOpen = false;
            Log.d(LOG_TAG, "closing fabs");

        } else {

            optionsFab.startAnimation(rotate_forward);
            addCardFab.startAnimation(fab_open);
            editCardFab.startAnimation(fab_open);
            deleteCardFab.startAnimation(fab_open);
            addCardFab.setClickable(true);
            editCardFab.setClickable(true);
            deleteCardFab.setClickable(true);
            isFabOpen = true;
            Log.d(LOG_TAG, "opening fabs");
        }
    }

    private void deleteCard() {

        FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();
        firebaseFirestoreDb.collection(Constants.CARD_COLLECTION_NAME)
                .whereEqualTo(Constants.CARD_QUESTION, card.getQuestion())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {

                                questionId = task.getResult().getDocuments().get(0).getId();
                                Log.d(LOG_TAG, "question id is = " + questionId);

                                Log.d(LOG_TAG, "document data is = " + task.getResult().getDocuments().get(0).getData());

                                docRef = task.getResult().getDocuments().get(0).getReference();

                                Log.d(LOG_TAG, "document reference is = " + docRef);
                            }

                        } else {
                            Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //TODO fix this - crashing

        firebaseFirestoreDb.collection(Constants.CARD_COLLECTION_NAME).document(docRef.toString()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(LOG_TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(LOG_TAG, "Error deleting document", e);
                    }
                });
    }

    private boolean hasMoreContent() {
        Log.d(LOG_TAG, "Checking to see if the more field has content");
        if (moreValue.length() < 1) {
            hasMoreContent = false;
            Log.d(LOG_TAG, "Setting hasMoreContent to False");
            Log.d(LOG_TAG, "More is equal to: " + moreValue);
            return false;

        } else {
            hasMoreContent = true;
            Log.d(LOG_TAG, "Setting hasMoreContent to True");
            Log.d(LOG_TAG, "More is equal to: " + moreValue);

            return true;
        }
    }

    private boolean isMoreHttp() {
        Log.d(LOG_TAG, "Checking to see if the more field contains a url");

        if (moreValue.contains("http")) {
            return true;
        } else {
            return false;
        }
    }
}