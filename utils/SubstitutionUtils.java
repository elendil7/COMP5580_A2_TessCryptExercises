package utils;

import java.util.ArrayList;

import configuration.AppConfig;

public class SubstitutionUtils {
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
      prettyPrintNumOfCharsMatched(maxCount, result.length());

      // print the top N substrings and their counts
      printTopNSubstrings(substrings, 10);
    }

    // return the result
    return result;
  }

  public static void prettyPrintNumOfCharsMatched(int charsMatched, int totalChars) {
    System.out.println(String.format("\nMax number of characters matched: %d/%d\n", charsMatched, totalChars));
  }

  public static void printTopNSubstrings(ArrayList<String> substrings, int n) {
    System.out.println(String.format("\n<<<Top %d substrings>>>:\n", n));
    for (int i = 0; i < n && i < substrings.size(); i++) {
      System.out.println(String.format("\nSubstring #%d: %s", i + 1, substrings.get(i)));
    }
    System.out.println();
  }
}
