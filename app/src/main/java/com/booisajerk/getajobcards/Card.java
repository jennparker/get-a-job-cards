package com.booisajerk.getajobcards;

import android.util.Log;

/**
 * Created by jenniferparker on 7/30/17.
 */

public class Card {

    public static final String LOG_TAG = "Parker" + Card.class.getSimpleName();
    private int id;
    private String question = null;
    private String answer = null;
    private String category = null;
    private String more = null;

    public Card() {
    }

    public Card(String question, String answer, String more, String category) {
        this.question = question;
        this.answer = answer;
        this.more = more;
        this.category = category;
    }

    public Card(int id, String question, String answer, String category, String more) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.more = more;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }
}
