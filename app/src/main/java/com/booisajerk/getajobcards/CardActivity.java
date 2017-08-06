package com.booisajerk.getajobcards;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jenniferparker on 7/30/17.
 */

public class CardActivity extends AppCompatActivity implements View.OnClickListener {

    private CardReaderDbHelper cardHelper;

    private boolean isShowAnswer;
    TextView cardCategoryText;
    TextView cardQuestionText;
    TextView cardAnswerText;
    Button advanceButton;
    TextView moreButton;
    String moreValue;
    int cardCount;

    private Boolean isFabOpen = false;
    private FloatingActionButton optionsFab, editCardFab, deleteCardFab, addCardFab;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    private static final String LOG_TAG = "Parker" + CardActivity.class.getSimpleName();

    private int cardNum = 3;

    Card card;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.card_activity);

        cardHelper = new CardReaderDbHelper(this);

        cardCount = cardHelper.getCardCount();

        Log.d(LOG_TAG, "Card count is: " + cardCount);

        cardCategoryText = (TextView) findViewById(R.id.categoryText);
        cardQuestionText = (TextView) findViewById(R.id.cardQuestionText);
        cardAnswerText = (TextView) findViewById(R.id.cardAnswerText);
        advanceButton = (Button) findViewById(R.id.advanceButton);
        moreButton = (TextView) findViewById(R.id.moreButton);
        optionsFab = (FloatingActionButton) findViewById(R.id.fabOptions);
        deleteCardFab = (FloatingActionButton) findViewById(R.id.fabDelete);
        editCardFab = (FloatingActionButton) findViewById(R.id.fabEdit);
        addCardFab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        optionsFab.setOnClickListener(this);
        addCardFab.setOnClickListener(this);
        editCardFab.setOnClickListener(this);
        deleteCardFab.setOnClickListener(this);

        displayQuestion();

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

                Log.d(LOG_TAG, "Add Fab");
                break;

            case R.id.fabEdit:

                Intent editCardintent = new Intent(CardActivity.this, EditCardActivity.class);

                editCardintent.putExtra("current_question", card.getQuestion());
                editCardintent.putExtra("current_answer", card.getAnswer());
                editCardintent.putExtra("current_more", card.getMore());
                editCardintent.putExtra("current_category", card.getCategory());
                editCardintent.putExtra("current_id", card.getId());

                startActivity(editCardintent);

                Log.d(LOG_TAG, "Edit Fab");
                break;

            case R.id.fabDelete:
                Toast.makeText(CardActivity.this, "Show delete card dialog", Toast.LENGTH_LONG).show();
                Log.d(LOG_TAG, "Delete Fab");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        advanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isShowAnswer) {
                    displayAnswer();
                } else {
                    if (cardNum <= cardCount) {
                        displayQuestion();
                    } else {
                        cardQuestionText.setText(R.string.no_more_cards_button_text);
                        cardCategoryText.setText(null);
                        cardAnswerText.setVisibility(View.INVISIBLE);
                        moreButton.setVisibility(View.INVISIBLE);
                        advanceButton.setEnabled(false);
                    }
                }
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(CardActivity.this);
                dialog.setMessage(moreValue);

                dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

    }

    private void displayQuestion() {
        card = cardHelper.getCard(cardNum);
        cardQuestionText.setText(card.getQuestion());
        cardAnswerText.setVisibility(View.INVISIBLE);
        cardAnswerText.setText(card.getAnswer());
        moreValue = card.getMore();
        cardCategoryText.setText(card.getCategory());
        advanceButton.setText(R.string.show_answer_text);
        isShowAnswer = true;
        cardQuestionText.setTextColor(getResources().getColor(R.color.textMain));
        Log.d(LOG_TAG, "Display question called");
    }

    private void displayAnswer() {
        cardAnswerText.setVisibility(View.VISIBLE);
        cardQuestionText.setTextColor(getResources().getColor(R.color.textBackground));
        advanceButton.setText(R.string.nextCardButtonText);
        isShowAnswer = false;
        cardNum++;
        Log.d(LOG_TAG, "Display answer called");
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
}