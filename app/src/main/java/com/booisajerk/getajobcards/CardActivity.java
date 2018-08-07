package com.booisajerk.getajobcards;

import android.content.Context;
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
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

import static com.booisajerk.getajobcards.Constants.CATEGORY_STATE;

/**
 * Created by jenniferparker on 7/30/17.
 */

public class CardActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = Constants.LOG_TAG_NAME + CardActivity.class.getSimpleName();
    private Toolbar toolbar;

    private boolean isShowAnswer;
    private boolean hasMoreContent = false;
    private boolean isFabOpen = false;
    private boolean isOptionsButtonVisible = false;
    private TextView cardCategoryText, cardQuestionText, cardAnswerText, moreButton;
    private Button advanceButton;
    private String moreValue, category;
    int cardCount, cardNum, cardId;
    private Boolean cardsPopulated = false;
    GestureDetector gestureDetector;
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

        FirebaseApp.initializeApp(this);
        setContentView(R.layout.card_activity);

        cardList = new ArrayList<Card>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d(LOG_TAG, "Creating new instance of firestore");

        cardNum = 1;
        advanceButton = findViewById(R.id.advanceButton);
        moreButton = findViewById(R.id.moreButton);
        optionsFab = findViewById(R.id.fabOptions);
        deleteCardFab = findViewById(R.id.fabDelete);
        editCardFab = findViewById(R.id.fabEdit);
        addCardFab = findViewById(R.id.fabAdd);

        cardCategoryText = findViewById(R.id.categoryText);
        cardQuestionText = findViewById(R.id.cardQuestionText);
        cardQuestionText.setMovementMethod(new ScrollingMovementMethod());
        cardAnswerText = findViewById(R.id.cardAnswerText);
        cardAnswerText.setMovementMethod(new ScrollingMovementMethod());

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
            Log.d(LOG_TAG, "Getting values from Category intent");
            category = getIntent().getExtras().getString(CATEGORY_STATE);
            if (!cardsPopulated) {
                if (category != null) {
                    Log.d(LOG_TAG, "Selected category is " + category);
                    if (category.equals(getString(R.string.all_categories))) {
                        Log.d(LOG_TAG, "Loading ALL categories.");
                        db.collection(Constants.CARD_COLLECTION_NAME)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d(LOG_TAG, document.getId() + " => " + document.getData());
                                                card = document.toObject(Card.class);
                                                cardList.add(card);
                                            }
                                            cardsPopulated = true;
                                            cardCount = cardList.size();
                                        } else {
                                            Log.w(LOG_TAG, "Error getting documents.", task.getException());
                                        }
                                    }
                                });
                    } else {
                        Log.d(LOG_TAG, "Calling category cards for: " + category);
                        db.collection(Constants.CARD_COLLECTION_NAME)
                                .whereEqualTo(Constants.CARD_CATEGORY, category)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d(LOG_TAG, document.getId() + " => " + document.getData());
                                                card = document.toObject(Card.class);
                                                cardList.add(card);
                                            }
                                            cardsPopulated = true;
                                            cardCount = cardList.size();
                                        } else {
                                            Log.w(LOG_TAG, "Error getting documents.", task.getException());
                                        }
                                    }
                                });
                    }
                }
            }
        }

        // Create an object of the AndroidGestureDetector Class
        AndroidGestureDetector androidGestureDetector = new AndroidGestureDetector();
        Log.d(LOG_TAG, "Creating a Gesture Detector object");

        // Create a GestureDetector
        gestureDetector = new GestureDetector(this, androidGestureDetector);
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
                final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                dialog.setPositiveButton(R.string.card_delete_confirmation, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d(LOG_TAG, "Okay, we're gonna delete it...");
                        deleteCard();
                    }
                });

                dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
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

        //TODO move this out of this method so it is called less frequently
        //TODO change this so height is calculated with non-deprecated code
        Display screen = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int tVHeight = screen.getHeight() / 5;


        Random randomGenerator = new Random();

        //Getting a random number
        int randomCardNumber = randomGenerator.nextInt(cardList.size());

        Log.d(LOG_TAG, "Get current card called for card number: " + cardNum);

        card = cardList.get(randomCardNumber);

        Log.d(LOG_TAG, "Card's Category is: " + card.getCategory() + ". Selected category is " + category);

        Log.d(LOG_TAG, "Get Current Card: Current card id is: " + card.getId() + " question is: " + card.getQuestion());

        //Setting a consistent text view height
        cardQuestionText.setHeight(tVHeight);
        cardAnswerText.setHeight(tVHeight);

        //Reset question and answer to top of their views
        cardQuestionText.scrollTo(0, 0);
        cardAnswerText.scrollTo(0, 0);

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
                        dialog.setPositiveButton(android.R.string.ok, null)
                                .setMessage(moreValue)
                                .create();

                        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
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

                        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
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
            closeFab();
        } else {
            openFab();

        }
    }

    private void deleteCard() {
        Log.d(LOG_TAG, "Delete card called");

        Log.d(LOG_TAG, "Current card question is: " + card.getQuestion());

        final FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();

        firebaseFirestoreDb.collection(Constants.CARD_COLLECTION_NAME)
                .whereEqualTo(Constants.CARD_QUESTION, card.getQuestion())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {

                                Log.d(LOG_TAG, "document reference is = " + document.getReference().delete());
                                Toast.makeText(CardActivity.this, "Card Deleted",
                                        Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                        }
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

    private void displayOptionsButton() {
        Log.d(LOG_TAG, "Displaying options button.");
        //   optionsFab.setVisibility(View.VISIBLE);
        isOptionsButtonVisible = true;
    }

    private void hideOptionsButton() {
        Log.d(LOG_TAG, "Hiding options button.");
        //  optionsFab.setVisibility(View.INVISIBLE);
        isOptionsButtonVisible = false;
    }

    private void closeFab() {
        optionsFab.startAnimation(rotate_backward);
        addCardFab.startAnimation(fab_close);
        editCardFab.startAnimation(fab_close);
        deleteCardFab.startAnimation(fab_close);
        addCardFab.setClickable(false);
        editCardFab.setClickable(false);
        deleteCardFab.setClickable(false);
        isFabOpen = false;
        Log.d(LOG_TAG, "closing fabs");
    }

    private void openFab() {
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    class AndroidGestureDetector implements GestureDetector.OnGestureListener,
            GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(LOG_TAG, " onDown");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d(LOG_TAG, " onSingleTapConfirmed");
            if (isOptionsButtonVisible) {
                hideOptionsButton();
            } else {
                displayOptionsButton();
            }
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(LOG_TAG, "onSingleTapUp");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(LOG_TAG, " onShowPress");
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(LOG_TAG, " onDoubleTap");
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d(LOG_TAG, " onDoubleTapEvent");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(LOG_TAG, " onLongPress");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            Log.d(LOG_TAG, " onScroll");
            if (e1.getY() < e2.getY()) {
                Log.d(LOG_TAG, " Scroll Down");
            }
            if (e1.getY() > e2.getY()) {
                Log.d(LOG_TAG, " Scroll Up");
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() < e2.getX()) {
                Log.d(LOG_TAG, "Left to Right swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");
            }
            if (e1.getX() > e2.getX()) {
                Log.d(LOG_TAG, "Right to Left swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");
            }
            if (e1.getY() < e2.getY()) {
                Log.d(LOG_TAG, "Up to Down swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");
            }
            if (e1.getY() > e2.getY()) {
                Log.d(LOG_TAG, "Down to Up swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");
            }
            return true;

        }
    }
}