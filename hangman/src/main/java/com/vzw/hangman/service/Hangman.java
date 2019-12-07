package com.vzw.hangman.service;

import com.vzw.hangman.exception.InvalidCharException;

public interface Hangman {



	public int getAttempts();

	public void play(String guess) throws InvalidCharException;

	public String getMessage();

	public boolean isGameOver();

}
