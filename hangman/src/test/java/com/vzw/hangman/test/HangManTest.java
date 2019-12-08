package com.vzw.hangman.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.vzw.hangman.exception.GameOverException;
import com.vzw.hangman.exception.IOExceptionGuessException;
import com.vzw.hangman.exception.InvalidCharException;
import com.vzw.hangman.exception.InvalidInputException;
import com.vzw.hangman.serviceImpl.HangmanImpl;
import com.vzw.hangman.util.HangManFileUtil;

public class HangManTest {

	private HangmanImpl hangManObj;

	/**
	 * @throws IOExceptionGuessException
	 * 
	 */
	@Before
	public void setUp() throws IOExceptionGuessException {

		hangManObj = new HangmanImpl();
		
	}

	@Test
	public void testSetup() {
		assertNotNull(hangManObj);

	}

	@Test
	public void testfile() throws IOExceptionGuessException {
		List<String> dictionary = HangManFileUtil.fectchWordList("classpath:words.txt");
		assertTrue(dictionary.size() > 0);

	}

	@Test(expected = IOExceptionGuessException.class)
	public void testFileNotFound() throws IOExceptionGuessException {
		List<String> dictionary = HangManFileUtil.fectchWordList("classpath:word.txt");
		assert(dictionary.size() < 0);

	}

	
	  @Test(expected = InvalidCharException.class)
	  public void testvalidChar() throws InvalidCharException {
	  
	   assertTrue("Not Valid Charcter".equals(hangManObj.validCharCheck("1")));
	  
	  }
	 

	@Test // This is to validate the secret word from file is populated or not
	public void testMySecretWordInitializedInTheBeginning() {
		hangManObj.setMySecretWord("apple");
		assertTrue(hangManObj.getMySecretWord() != null || hangManObj.getMySecretWord().length > 0);

	}

	@Test // This is to validate the attempts are 0 or not at the beginning of
			// the game
	public void testMyAttemptsIsZeroInTheBeginning() {
		assertTrue(hangManObj.getAttempts() == 0);

	}

	@Test // This is to validate the mymistakes are 0 or not at the beginning of
			// the game
	public void testMyMistakesZeroInTheBeginning() {
		assertTrue(hangManObj.getMyMisses() == 0);

	}

	@Test // valid
	public void testAttemptsIsZeroInTheBeginning() {

		int attempts = hangManObj.getAttempts();
		assertTrue(attempts == 0);
	}

	@Test // valid
	public void testIsGameOverIsFalseInTheBeginning() {
		boolean gameOver = hangManObj.isGameOver();

		assertTrue(!gameOver);

	}

	@Test // valid
	public void testMessageIsYouWonTheGame() throws InvalidInputException, GameOverException, InvalidCharException {
		hangManObj.setMySecretWord("apple");
		hangManObj.play("a");
		hangManObj.play("p");
		hangManObj.play("p");
		hangManObj.play("l");
		hangManObj.play("e");
		String message = hangManObj.getMessage();
		assertTrue("You WON the Game".equals(message));
	}

	@Test // valid
	public void testMessageIsYouLostGame() throws InvalidInputException, GameOverException, InvalidCharException {
		hangManObj.setMySecretWord("Apple");
		hangManObj.play("a");
		hangManObj.play("p");
		hangManObj.play("q");
		hangManObj.play("l");
		hangManObj.play("e");
		hangManObj.play("q");
		hangManObj.play("l");
		hangManObj.play("e");
		hangManObj.play("e");
		hangManObj.play("q");
		hangManObj.play("q");
		hangManObj.play("o");

		String message = hangManObj.getMessage();
		assertTrue("You Lost It".equals(message));
	}

	@Test // valid
	public void testMisses() throws InvalidInputException, GameOverException, InvalidCharException {
		hangManObj.setMySecretWord("apple");
		hangManObj.play("y");
		hangManObj.play("e");
		hangManObj.play("l");
		hangManObj.play("l");

		assert (hangManObj.getMyMisses() == 4);
	}

	@Test // valid -- need to check
	public void testPlayGameAfterItsOver() throws GameOverException, InvalidCharException {
		hangManObj.setMySecretWord("apple");

		hangManObj.play("a");
		hangManObj.play("p");
		hangManObj.play("p");
		hangManObj.play("l");
		hangManObj.play("e");
		boolean gameOver = hangManObj.isGameOver();
		assertTrue(gameOver);
	}

}
