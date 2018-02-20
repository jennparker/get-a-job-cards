package com.booisajerk.getajobcards;

import android.util.Log;

public class Card {

    private int id;
    private String question;
    private String answer;
    private String category;
    private String more;

    public static final String LOG_TAG = Constants.LOG_TAG_NAME + Card.class.getSimpleName();

    public Card() {
    }

    public Card(String question, String answer, String category, String more) {
        setQuestion(question);
        setAnswer(answer);
        setCategory(category);
        setMore(more);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMore(String more) {
        this.more = more;
    }


    public int getId() {
        Log.d(LOG_TAG, "Getting ID");
        return id;
    }

    public String getQuestion() {
        Log.d(LOG_TAG, "Getting question");
        return question;
    }

    public String getAnswer() {
        Log.d(LOG_TAG, "Getting Answer");
        return answer;
    }

    public String getCategory() {
        Log.d(LOG_TAG, "Getting Category");
        return category;
    }

    public String getMore() {
        Log.d(LOG_TAG, "Getting More");
        return more;
    }
}
