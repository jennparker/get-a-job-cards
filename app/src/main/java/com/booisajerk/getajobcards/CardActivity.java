package com.booisajerk.getajobcards;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jenniferparker on 7/30/17.
 */

public class CardActivity extends AppCompatActivity {

    private CardReaderDbHelper cardHelper;

    private boolean isShowAnswer;
    TextView cardNumberText;
    TextView cardCategoryText;
    TextView cardQuestionText;
    TextView cardAnswerText;
    Button advanceButton;
    TextView moreButton;
    String moreValue;
    int cardCount;
    FloatingActionButton optionsFab;
    FloatingActionButton editCardFab;
    FloatingActionButton deleteCardFab;

    private static final String LOG_TAG = "Parker" + CardActivity.class.getSimpleName();

    private int cardNum = 3;

    Card c;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.card_activity);

        cardHelper = new CardReaderDbHelper(this);

        cardCount = cardHelper.getCardCount();

        Log.d(LOG_TAG, "Card count is: " + cardCount);

        cardNumberText = (TextView) findViewById(R.id.questionNumberText);
        cardCategoryText = (TextView) findViewById(R.id.categoryText);
        cardQuestionText = (TextView) findViewById(R.id.cardQuestionText);
        cardAnswerText = (TextView) findViewById(R.id.cardAnswerText);
        advanceButton = (Button) findViewById(R.id.advanceButton);
        moreButton = (TextView) findViewById(R.id.moreButton);
        optionsFab = (FloatingActionButton) findViewById(R.id.card__options_fab);
        deleteCardFab = (FloatingActionButton) findViewById(R.id.card__delete_fab);
        editCardFab = (FloatingActionButton) findViewById(R.id.card_edit_fab);

        displayQuestion();
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
                        cardNumberText.setText(null);
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

                dialog.setNegativeButton ("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        optionsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsFab.setVisibility(View.INVISIBLE);

                deleteCardFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //TODO add delete activity intent
                        Toast.makeText(CardActivity.this, "Show delete card dialog", Toast.LENGTH_LONG).show();

                    }
                });
                deleteCardFab.setVisibility(View.VISIBLE);

                editCardFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent editCardintent = new Intent(CardActivity.this, EditCardActivity.class);
                        editCardintent.putExtra("current_question", c.getQuestion());
                        editCardintent.putExtra("current_answer", c.getAnswer());
                        editCardintent.putExtra("current_more", c.getMore());
                        editCardintent.putExtra("current_category", c.getCategory());
                        editCardintent.putExtra("current_id", c.getId());

                        startActivity(editCardintent);

                        Toast.makeText(CardActivity.this, "Show edit card dialog", Toast.LENGTH_LONG).show();

                    }
                });
                editCardFab.setVisibility(View.VISIBLE);
            }
        });
    }

    private void displayQuestion() {
        c = cardHelper.getCard(cardNum);
        cardNumberText.setText(Integer.toString(c.getId()));
        cardQuestionText.setText(c.getQuestion());
        cardAnswerText.setVisibility(View.INVISIBLE);
        cardAnswerText.setText(c.getAnswer());
        moreValue = c.getMore();
        cardCategoryText.setText(c.getCategory());
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
}