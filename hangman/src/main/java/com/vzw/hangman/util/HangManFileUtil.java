package com.vzw.hangman.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.ResourceUtils;

import com.vzw.hangman.exception.IOExceptionGuessException;

public class HangManFileUtil {
	
	public static List<String> fectchWordList(String fileName) throws IOExceptionGuessException  {
		List<String> dictionary = new ArrayList<String>();

		try (Stream<String> stream = Files.lines(Paths.get(ResourceUtils.getFile(fileName).getPath()))) {

			return dictionary = stream.collect(Collectors.toList());
		} catch (IOException e) {
			throw new IOExceptionGuessException("File Not found Please check");
			// e.printStackTrace();
		}

	}

}
