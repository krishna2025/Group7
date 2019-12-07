package com.vzw.hangman.starup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.ResourceUtils;

import com.vzw.hangman.util.HangmanConstants;

@SpringBootApplication
@ComponentScan(basePackages = "com.vzw.hangman")
public class HangmanStartUP {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(HangmanStartUP.class);
		app.run(args);
	}

	public void run(String... args) throws Exception {

		List<String> dictionary = new ArrayList<String>();

		try (Stream<String> stream = Files.lines(Paths.get(ResourceUtils.getFile(HangmanConstants.FILE_NAME).getPath()))) {

			dictionary = stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
