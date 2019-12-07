package com.vzw.hangman.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.ResourceUtils;

import com.vzw.hangman.exception.IOExceptionGuessException;
import com.vzw.hangman.exception.InvalidCharException;
import com.vzw.hangman.service.Hangman;
import com.vzw.hangman.util.HangmanConstants;

public class HangmanImpl implements Hangman,HangmanConstants {

	private int attempts = 0;
	String message;
	boolean gameOver = false;
	
	

	private String myWords[] = null;

	
	
	private char[] mySecretWord; // what's shown to the user

	private char[] guessedWord;
	
	private int myMisses; // # misses so far
	private int myWordIndex; // which word is being guessed
	private boolean[] myLettersUsed; // tracks letters guessed/used

	private final static char BLANK = '_';
	private final static int MISSES = 8;
	
	public int getAttempts() {
		// TODO Auto-generated method stub
		return attempts;
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public static List<String> fectchWordList(String fileName) throws IOExceptionGuessException  {
		List<String> dictionary = new ArrayList<String>();

		try (Stream<String> stream = Files.lines(Paths.get(ResourceUtils.getFile(fileName).getPath()))) {

			return dictionary = stream.collect(Collectors.toList());
		} catch (IOException e) {
			throw new IOExceptionGuessException("File Not found Please check");
			// e.printStackTrace();
		}

	}

	public static String randomPick(String[] WORDS) {

		String word = WORDS[new Random().nextInt(WORDS.length)];

		return word;
	}

	public boolean play(String randomWord, boolean[] visible, char chr) {
		attempts++;

		if (validateInput(chr)) {
			//throw new InvalidInputException();
		}
		for (int i = 0; i < randomWord.length(); ++i) {
			if (randomWord.charAt(i) == chr && !visible[i]) {
				visible[i] = true;
				return true;
			}
		}

		return false;
	}

	public boolean validateInput(char ch) {

		if ((int) ch == 0) {
			return false;
		}
		return true;
	}

	public HangmanImpl() throws IOExceptionGuessException {
		myMisses = 0;
		//myWordIndex = 0;
		myWords = fectchWordList(HangmanConstants.FILE_NAME).toArray(new String[0]);;
		myLettersUsed = new boolean[Character.MAX_VALUE];

		myWordIndex = new Random().nextInt(myWords.length);
		mySecretWord = myWords[myWordIndex].toCharArray();
		//clear();
	}

	public int getMyMisses() {
		return myMisses;
	}

	/**
	 * clear all variables to beginning-of-game state word shown user will be all
	 * blanks, all letters are eligible for "guessability"
	 */

	private void clear() {
		int k;

		// all letters can be guessed, none are used

		for (k = 0; k < Character.MAX_VALUE; k++) {
			myLettersUsed[k] = false;
		}

		// word shown to user is all blanks

		guessedWord = new char[mySecretWord.length];;
		for (k = 0; k < guessedWord.length; k++) {
			guessedWord[k] = BLANK;
		}
	}

	/**
	 * process a user's guess, update state to reflect the guess. This might change
	 * # misses and the word displayed to the user
	 *
	 * @param ch
	 *            is the character guessed by the user
	 */
	private void guess(char ch) {
		int k;
		boolean charFound = false;

		ch = Character.toLowerCase(ch); // case doesn't matter
		attempts++;
		for (k = 0; k < mySecretWord.length; k++) {
			if (!myLettersUsed[k] && Character.toLowerCase(mySecretWord[k]) == ch) {
				guessedWord[k] = ch;
				myLettersUsed[k] = true;
				charFound = true;
				break;
			}
		}
		if (!myLettersUsed[k] && !charFound) {
			myMisses++;
		}
	}

	/**
	 * display (partially guessed) word to user
	 */
	private void showWord() {
		int k;
		for (k = 0; k < guessedWord.length; k++) {
			System.out.print(guessedWord[k] + " ");
		}
		System.out.println("\n# misses left = " + (MISSES - myMisses));
	}

	public String getGuessedWord() {
		return guessedWord.toString();
	}

	/**
	 * @return true if word has been guessed, else return false
	 */

	private boolean wordGuessed() {
		int k;
		for (k = 0; k < guessedWord.length; k++) {
			if (guessedWord[k] == BLANK) {
				return false;
			}
		}
		return true;
	}
	public boolean validCharCheck(String str) throws InvalidCharException{
		
	if(!Character.isLetter(str.charAt(0))){
		  throw new InvalidCharException("Not Valid Charcter");
	}
	return true;
	}

	/**
	 * plays a game of hangman. Repeated calls of this function will play different
	 * games (different words) up to some internal limit based on the number of
	 * different words. After all words have been used they'll be repeated
	 * @throws InvalidCharException 
	 */

	public void play(String s) throws InvalidCharException {
		if(guessedWord == null)
			clear();

		System.out.print("guess> ");
		
		

		if (s.length() > 0) {// typed something?
			if(validCharCheck(s)){
				guess(s.charAt(0));
			}
		}
		showWord();

		if (myMisses >= MISSES) {
			message = YOU_LOST_IT;
		} else if (wordGuessed()) {
			message = YOU_WON_THE_GAME;
			gameOver = true;
		}
	}

	
	
	public char[] getMySecretWord() {
		return mySecretWord;
	}

	public void setMySecretWord(String mySecretWord) {
		this.mySecretWord = mySecretWord.toCharArray();
	}


	
	
	public static void main(String[] args) throws IOExceptionGuessException, InvalidCharException {
		HangmanImpl hangManObj = new HangmanImpl();
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
		System.out.println(message + " " + hangManObj.getAttempts());
	}

}
