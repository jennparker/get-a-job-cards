package com.booisajerk.getajobcards;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {

    public class SampleTest {
        @Test
        public void addition_isCorrect() throws Exception {
            assertEquals(4, 2 + 2);
        }
    }

    public class LoadFileTests {

        @Test
        public void file_is_empty() {
            //What is returned when input file is empty
            //arrange
            //act
            //assert
        }

        @Test
        public void file_has_too_few_fields() {
            //What is returned when file has only 3 input fields
            //arrange
            //act
            //assert
        }

        @Test
        public void file_has_too_many_fields() {
            //What is returned when file has 8 input fields
            //arrange
            //act
            //assert
        }
    }


    public class MakeCardTests {

        @Test
        public void card_passed_to_() {
            //cardList.add(card);

            //What is returned when file has only 3 input fields
            //arrange
            //act
            //assert
        }
    }

    public class QuizCardTests {

        @Test
        public void card_returns_three_values() {
            //arrange
            //act
            //assert
        }

        @Test
        public void card_get_question_returns_question() {
            //arrange
            //act
            //assert
        }

        @Test
        public void card_get_answer_returns_answer() {
            //arrange
            //act
            //assert
        }

        @Test
        public void card_get_category_returns_category() {
            //arrange
            //act
            //assert
        }

        @Test
        public void quiz_card_one_null_input() {
            //what is returned when one of the input values is null
            //arrange
            //act
            //assert
        }

        @Test
        public void quiz_card_all_null_inputs() {
            //what is returned when all of the input values are null
            //arrange
            //act
            //assert
        }
    }

    public class ShowNextCardTests {

        @Test
        public void card_index_null() {
            //what is returned when the card index is null
            //arrange
            //act
            //assert
        }

        @Test
        public void card_index_out_of_range() {
            //what is returned when the card index is out of range
            //arrange
            //act
            //assert
        }

        @Test
        public void card_index_increments() {
            //card index increments correctly
            //arrange
            //act
            //assert
        }
    }

}