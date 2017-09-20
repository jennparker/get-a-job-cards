package com.booisajerk.getajobcards;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by jenniferparker on 9/20/17.
 */

@Dao
public interface CardDAO {

    public static final String LOG_TAG = "Parker " + CardDAO.class.getSimpleName();

    @Query("SELECT * FROM card")
    List<Card> loadAllCards();

    @Query("SELECT * FROM card where question LIKE :question")
    Card findByQuestion(String question);

    @Query("SELECT COUNT(*) from card")
    int countAllCards();

    @Query("SELECT COUNT(*) FROM card WHERE category LIKE :category")
    int countCategoryCards(String category);


    @Query("SELECT * FROM card WHERE category LIKE :category")
    List<Card> getCategoryCards(String category);

    @Insert
    void insertCard(Card card);

    @Insert
    void insertAll(Card... cards);

    @Delete
    void delete(Card card);

    @Query("DELETE FROM Card")
    void deleteAll();

    @Update
    public void updateCards(Card... cards);
}
