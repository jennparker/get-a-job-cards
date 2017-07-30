package com.booisajerk.getajobcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by jenniferparker on 7/18/17.
 */

public class FileHelper extends AppCompatActivity {

    private ArrayList<QuizCard> cardList;
    private QuizCard currentCard;
    private int currentCardIndex;
    private boolean isShowAnswer;
    private boolean isShowMore;
    TextView cardNumberText;
    TextView categoryText;
    TextView cardQuestionText;
    TextView cardAnswerText;
    Button advanceButton;
    TextView moreButton;
    private static final String LOG_TAG = "Parker" + FileHelper.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helper_file);

        Log.d(LOG_TAG, "OnCreate called");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cardList = new ArrayList<QuizCard>();

        cardNumberText = (TextView) findViewById(R.id.questionNumberText);
        categoryText = (TextView) findViewById(R.id.categoryText);
        cardQuestionText = (TextView) findViewById(R.id.cardQuestionText);
        cardAnswerText = (TextView) findViewById(R.id.cardAnswerText);
        advanceButton = (Button) findViewById(R.id.advanceButton);
        moreButton = (TextView) findViewById(R.id.moreButton);

        loadFile();

        showNextCard();
    }

    @Override
    protected void onStart() {
        super.onStart();
        advanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowAnswer) {
                    //cardQuestionText.setText(currentCard.getAnswer());
                    cardAnswerText.setVisibility(View.VISIBLE);
                    cardQuestionText.setTextColor(getResources().getColor(R.color.textBackground));
                    advanceButton.setText(R.string.nextCardButtonText);
                    isShowAnswer = false;
                } else {
                    if (currentCardIndex < cardList.size()) {
                        cardQuestionText.setTextColor(getResources().getColor(R.color.textMain));
                        showNextCard();

                    } else {
                        cardQuestionText.setText(R.string.no_more_cards_button_text);
                        cardAnswerText.setVisibility(View.INVISIBLE);
                        advanceButton.setEnabled(false);
                    }
                }
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO add alert dialog here

                Intent moreDetailsintent = new Intent(FileHelper.this, MoreDetailsActivity.class);
                startActivity(moreDetailsintent);
            }
        });
    }

    protected void loadFile() {
        Log.d(LOG_TAG, "loadFile called");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(
                    "local_cards.txt")));
            String inputString;

            while ((inputString = reader.readLine()) != null) {
                makeCard(inputString);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeCard(String lineToParse) {
        Log.d(LOG_TAG, "makeCard called.");
        Log.d(LOG_TAG, "lineToParse is: " + lineToParse);
        String[] result = lineToParse.split("/");
        QuizCard card = new QuizCard(result[0], result[1], result[2], result[3], result[4]);
        cardList.add(card);
        System.out.println("made a card");
    }

    private void showNextCard() {
        Log.d(LOG_TAG, "Show next card called.");
        currentCard = cardList.get(currentCardIndex);
        currentCardIndex++;
        cardNumberText.setText(currentCard.getNumber());
        cardQuestionText.setText(currentCard.getQuestion());
        cardAnswerText.setVisibility(View.INVISIBLE);
        cardAnswerText.setText(currentCard.getAnswer());
        categoryText.setText(currentCard.getCategory());
        advanceButton.setText(R.string.show_answer_text);
        isShowAnswer = true;

        //TODO move this to first time card is used too
        System.out.println("More column: " + currentCard.getMore());
        if (currentCard.getMore() == "null") {
            moreButton.setClickable(false);
            moreButton.setFocusable(false);
            moreButton.setVisibility(View.INVISIBLE);
        } else {
            moreButton.setClickable(true);
            moreButton.setFocusable(true);
            moreButton.setVisibility(View.VISIBLE);
        }
    }

    public static class QuizCard {
        String number = null;
        String question = null;
        String answer = null;
        String category = null;
        String more = null;

        public QuizCard(String number, String question, String answer, String category, String more) {
            Log.d(LOG_TAG, "QuizCard class called - creating new card");
            this.number = number;
            this.question = question;
            this.answer = answer;
            this.category = category;
            this.more = more;
        }

        public String getNumber() {
            return number;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }

        public String getCategory() {
            return category;
        }

        public String getMore() {
            return more;
        }

    }
}


