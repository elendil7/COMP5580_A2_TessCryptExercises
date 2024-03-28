package utils;

import java.util.Map;

import models.FreqModel;

import java.util.HashMap;
import java.util.ArrayList;

public class CipherUtils {
  // caesar cipher rotations (we will shift by 1 each time)
  public static String rotateCaesarCipher(String text, int shift) {
    String result = "";
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      if (Character.isLetter(c)) {
        if (Character.isUpperCase(c)) {
          result += (char) ('A' + (c - 'A' + shift) % 26);
        } else {
          result += (char) ('a' + (c - 'a' + shift) % 26);
        }
      } else {
        result += c;
      }
    }
    return result;
  }

  // vignere cipher reversal, using given key, rotations per method run: 1
  // source: https://github.com/mm898/Vigenere-cipher/blob/master/vigCipher.java
  public static String decryptVignereCipher(String encoded, String key) {
    String decoded = "";
    encoded = encoded.toUpperCase();
    for (int i = 0, j = 0; i < encoded.length(); i++) {
      char letter = encoded.charAt(i);
      decoded += (char) ((letter - key.charAt(j) + 26) % 26 + 65);
      j = ++j % key.length();
    }
    return decoded;
  }

  // divide text into N blocks
  public static String[] divideTextIntoBlocksOfNLength(String text, int n) {
    // split the text into an array, to produce n character blocks
    String[] textArray = text.split("(?<=\\G.{" + n + "})");
    return textArray;
  }

  // Count the frequencies of characters at each position in the blocks
  public static ArrayList<FreqModel> analyzeCharacterFrequencies(String[] blocks) {
    // Get the block size (size of each block)
    int blockSize = blocks[0].length();

    // define arraylist for storing all the stuff inside
    ArrayList<FreqModel> freq = new ArrayList<>();

    // from 1 -> N (index 1 of each block, then index 2 of each block, etc)
    for (int i = 0; i < blockSize; i++) {
      // create hashmap for storing char frequencies of each character in
      HashMap<Character, Integer> charFrequency = new HashMap<>();

      // Count occurrences of characters at position i in all blocks
      // for every index N of each block (e.g., index 1 of each block, index 2 of each
      // block, etc)
      for (String block : blocks) {
        char c = block.charAt(i);
        charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
      }

      // add the charFrequencies to the arraylist, deriving the
      for (Map.Entry<Character, Integer> entry : charFrequency.entrySet()) {
        freq.add(new FreqModel(entry.getKey(), entry.getValue(), ((double) entry.getValue() / blocks.length)));
      }
    }

    return freq;
  }

  // sort the frequencies of characters in descending order
  public static ArrayList<FreqModel> sortCharacterFrequencies(ArrayList<FreqModel> freq) {
    freq.sort((a, b) -> b.getFrequency() > a.getFrequency() ? 1 : -1);
    return freq;
  }

  // print counts prettily (B=0.150, E=0.107, X=0.093,)
  public static void printCharacterFrequencies(ArrayList<FreqModel> freq) {
    System.out.println();
    for (FreqModel model : freq) {
      System.out.print(String.format("[%s=%.3f], ", model.getCharacter(), model.getFrequency()));
    }
    System.out.println();
  }

  // grab most frequent character in a group
  public static String mostFrequentCharacter(Map<Character, Integer> freq) {
    return freq.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get()
        .getKey().toString();
  }

  // most common letters, in order
  public static final String COMMON_LETTERS = "ETAOINSHRDLCUMWFGYPBVKJXQZ";
}
