package urlshortener.application;

import java.util.Random;

public class Utils {
	private static final Random random = new Random();
	private static final String chars = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890";
	private static final Integer shortURLLength = 5;

	public static String generateShortURL() {
		StringBuilder shortURL = new StringBuilder(shortURLLength);
		for (int i = 0; i < shortURLLength; i++) {
			shortURL.append(chars.charAt(random.nextInt(chars.length())));
		}
		return shortURL.toString();
	}
	
	public static String toCanonicalURL(String URL) {
		if(!URL.startsWith("http")) {
			return "http://" + URL;
		}
		return URL;
	}
}
