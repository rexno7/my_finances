package mywebsite.aoc;

import java.io.File;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class Day01 {

	private final Map<String,String> NUM_DICT = Map.of("one", "1", "two", "2", "three", "3", "four", "4", "five", "5", "six", "6", "seven", "7", "eight", "8", "nine", "9", "zero", "0");
	
	public void execute() {
		try {
			File day01Input = new ClassPathResource("aoc/day01_test1.dat").getFile();
			Scanner reader = new Scanner(day01Input);
			int sum = 0;
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				sum += convertWords(line);
			}
			reader.close();
			System.out.println("sum: " + sum);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	private int convertWords(String line) {
		System.out.println(line);
		Pattern pattern = Pattern.compile("(one|two|three|four|five|six|seven|eight|nine|\\d)");
		Matcher matcher = pattern.matcher(line);
		matcher.find();
		String firstMatch = matcher.group(1);
		String lastMatch = matcher.group(1);
		while (matcher.find()) {
			lastMatch = matcher.group(1);
		}
		String firstInt = firstMatch.length() > 1 ? NUM_DICT.get(firstMatch) : firstMatch;
		String lastInt = lastMatch.length() > 1 ? NUM_DICT.get(lastMatch) : lastMatch;
		int sum = Integer.parseInt(firstInt + lastInt);
		System.out.println(sum);
		return sum;
	}
}
