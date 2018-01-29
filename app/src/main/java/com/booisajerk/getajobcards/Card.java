package com.booisajerk.getajobcards;

public class Card {

    private int id;
    private String question;
    private String answer;
    private String category;
    private String more;

    public static final String LOG_TAG = "Parker " + Card.class.getSimpleName();

    public Card() {

    }

    public Card(String question, String answer, String category, String more, int id) {
        setQuestion(question);
        setAnswer(answer);
        setCategory(category);
        setMore(more);
        setId(id);
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
        return id;
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
