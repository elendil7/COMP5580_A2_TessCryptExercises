package src.main.java.utils;

import java.util.ArrayList;

import src.main.java.configuration.AppConfig;
import src.main.java.models.FreqModel;

public class SubstitutionUtils {
  public static double round(double value, int places) {
    if (places < 0)
      throw new IllegalArgumentException();

    long factor = (long) Math.pow(10, places);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
  }

  // pretty print the result of replacing most common chars
  public static void prettyPrintReplaceMostCommonChars(String result) {
    System.out.println("\n<<<Most common chars in ciphertxt replaced by most common chars of tess27:");
    System.out.println(String.format("\n%s\n", result));
  }

  // pretty print most common frequencies given an ArrayList<FreqModel>
  public static void prettyPrintMostCommonFrequencies(ArrayList<FreqModel> freqs, String name) {
    System.out.println(String.format("\n<<<Most common frequencies of %s>>>:\n", name));
    for (int i = 0; i < freqs.size(); i++) {
      System.out.println(String.format("Character #%d: %s, Count: %d, Frequency: %s", i + 1,
          freqs.get(i).getCharacter(), freqs.get(i).getCount(),
          String.valueOf(round(freqs.get(i).getFrequency(), 3))));
    }
  }

  // replace most common chars in ciphertext with most common chars in tess27
  public static String replaceMostCommonChars(String ciphertext, String tess27,
      ArrayList<FreqModel> cipherTxtFreqs, ArrayList<FreqModel> tess27Freqs) {

    // iterate through most common cipher text frequencies, in order of most common
    // to least common
    for (int i = 0; i < cipherTxtFreqs.size(); i++) {
      // grab the current most common character in the cipher text
      Character cipherChar = cipherTxtFreqs.get(i).getCharacter();

      // grab the current most common character in tess27
      Character tess27Char = tess27Freqs.get(i).getCharacter();

      // replace all occurrences of the most common character in the cipher text with
      // the most common character in tess27
      ciphertext = ciphertext.replace(cipherChar, tess27Char);
    }

    if (AppConfig.debugLoggingEnabled) {
      // pretty print the result
      prettyPrintReplaceMostCommonChars(ciphertext);
    }

    // return the changed ciphertext
    return ciphertext;
  }

  // Iterate through entire given tess27 plaintext, and see how similar it is to
  // the given semi-decoded plaintext
  public static String compareTess27ToDecoded(String plainTxt, String decodedTxt) {
    // arraylist for storing all the substrings and their respective counts
    ArrayList<String> substrings = new ArrayList<>();

    // create a string for storing the result
    String result = "";
    // create counter for storing max number of characters that match
    int maxCount = 0;

    for (int i = 0; i < plainTxt.length() - decodedTxt.length(); i++) {
      // get the substring of the plaintext
      String sub = plainTxt.substring(i, i + decodedTxt.length());

      // check how many characters of the substring match the decoded text
      int count = 0;
      for (int j = 0; j < sub.length(); j++) {
        if (sub.charAt(j) == decodedTxt.charAt(j)) {
          count++;
        }
      }

      // if count is greater than maxCount, update maxCount and result
      if (count > maxCount) {
        maxCount = count;
        result = sub;
      }

      // add the substring and its count to the arraylist
      substrings.add(sub + " " + count);
    }

    // sort the arraylist high to low based on the count
    substrings.sort((a, b) -> {
      String[] aSplit = a.split(" ");
      String[] bSplit = b.split(" ");
      return Integer.parseInt(bSplit[1]) - Integer.parseInt(aSplit[1]);
    });

    // if app config debug logging is enabled, print the top N substrings
    if (AppConfig.debugLoggingEnabled) {
      // print the result and the count of matched characters
      prettyPrintConfidenceLevel(substrings.get(0), substrings.get(1), result.length());

      // print the top N substrings and their counts
      // printTopNSubstrings(substrings, 5);
    }

    // return the result
    return result;
  }

  public static void prettyPrintConfidenceLevel(String bestMatch, String secondBestMatch, int totalChars) {
    // grab best match and second best match
    String[] bestMatchSplit = bestMatch.split(" ");
    String[] secondBestMatchSplit = secondBestMatch.split(" ");

    // grab the count of matched characters for best match and second best match
    int bestMatchCount = Integer.parseInt(bestMatchSplit[1]);
    int secondBestMatchCount = Integer.parseInt(secondBestMatchSplit[1]);

    // print out total number of characters matched
    System.out.println(String.format("Max number of characters matched: [%d/%d]",
        bestMatchCount, totalChars));

    // print out confidence level
    System.out
        .println(
            (String.format("Confidence level: {%.2f%%}\n", round(((double) bestMatchCount / totalChars) * 100, 2))));

    // print out total number of characters matched compared to 2nd best match
    System.out.println(String.format("2nd Max number of characters matched: [%d/%d]",
        secondBestMatchCount, totalChars));

    // print out confidence of best match against 2nd best match
    System.out.println(String.format("Confidence level compared to 2nd best match: {%.2f%%}\n",
        round(((double) bestMatchCount / secondBestMatchCount) * 100, 2)));
  }

  public static void printTopNSubstrings(ArrayList<String> substrings, int n) {
    System.out.println(String.format("\n<<<Top %d substrings>>>:\n", n));
    for (int i = 0; i < n && i < substrings.size(); i++) {
      System.out.println(String.format("\nSubstring #%d: %s", i + 1, substrings.get(i)));
    }
    System.out.println();
  }
}
