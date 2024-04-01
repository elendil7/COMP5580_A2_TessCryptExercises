package src.main.java.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import src.main.java.models.FreqModel;

public class VignereUtils {
  // most common letters, in order
  public static final String COMMON_LETTERS = "ETAOINSHRDLCUMWFGYPBVKJXQZ";
  // enlish frequencies
  public static final double[] ENGLISH_FREQUENCIES = { 0.08167, 0.01492, 0.02782, 0.04253, 0.12702, 0.02228, 0.02015,
      0.06094, 0.06966, 0.00153, 0.00772, 0.04025, 0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987, 0.06327,
      0.09056, 0.02758, 0.00978, 0.02360, 0.00150, 0.01974, 0.00074 };

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
  public static String[] divideTextIntoNGroups(String text, int n) {
    // create array of strings for storing the blocks
    String[] blocks = new String[n];

    // initialize the blocks
    for (int i = 0; i < n; i++) {
      blocks[i] = "";
    }

    // for every character in the text
    for (int i = 0; i < text.length(); ++i) {
      // add the character to the correct block at index i % n (index 0 to N, looping
      // back to 0 when N is reached with the modulo operator)
      blocks[i % n] += text.charAt(i);
    }

    // return blocks
    return blocks;
  }

  // Count the frequencies of characters at each position in the blocks
  public static ArrayList<ArrayList<FreqModel>> getCharacterFrequenciesForAllBlocks(String[] blocks) {
    // define an array for holding the frequencies of characters
    ArrayList<ArrayList<FreqModel>> freqs = new ArrayList<ArrayList<FreqModel>>();

    // for every block
    for (String block : blocks) {
      // create a hashmap for storing the frequencies of characters
      HashMap<String, FreqModel> freq = new HashMap<String, FreqModel>();

      // for every character in the block
      for (int i = 0; i < block.length(); i++) {
        // get the character at the current index
        char c = block.charAt(i);

        // if the character is not a letter, skip it
        if (!Character.isLetter(c)) {
          continue;
        }

        // convert the character to uppercase
        c = Character.toUpperCase(c);

        // if the character is not in the hashmap, add it with a count of 1
        if (!freq.containsKey(String.valueOf(c))) {
          freq.put(String.valueOf(c), new FreqModel(c, 1, 0));
        } else {
          // if the character is in the hashmap, increment the count
          freq.get(String.valueOf(c)).setCount(freq.get(String.valueOf(c)).getCount() + 1);
        }
      }

      // calculate & set the frequency of each character in the block
      for (String key : freq.keySet()) {
        FreqModel model = freq.get(key);
        model.setFrequency((double) model.getCount() / block.length());
      }

      // convert hashmap to arraylist of FreqModel objects
      ArrayList<FreqModel> freqList = new ArrayList<FreqModel>(freq.values());

      // sort array by frequency
      freqList.sort((a, b) -> Double.compare(b.getFrequency(), a.getFrequency()));

      // add the arraylist of FreqModel objects to the array
      freqs.add(freqList);
    }

    // return the array of frequencies
    return freqs;
  }

  // derive 1 single charcter of the key, given

  // print counts prettily (B=0.150, E=0.107, X=0.093,)
  public static void printCharacterFrequencies(ArrayList<ArrayList<FreqModel>> freqs) {
    System.out.println("\n<<<Character Frequencies>>>:");
    for (ArrayList<FreqModel> freq : freqs) {
      System.out.println(String.format("\nGroup: %s", freqs.indexOf(freq)));
      for (FreqModel model : freq) {
        System.out.println(model.getCharacter() + " " + model.getCount() + " " + model.getFrequency());
      }
    }
  }

  // method for finding the key for a Vigenere cipher
  public static String findKey(ArrayList<ArrayList<FreqModel>> freqs) {
    String key = "";

    for (int i = 0; i < freqs.size(); i++) {
      // find the best shift for the current group
      int shift = findBestShift(freqs.get(i));
      key += (char) (shift + 'A');
    }

    return key;
  }

  // method for finding the best shift for a single group of characters
  private static int findBestShift(ArrayList<FreqModel> freqGroup) {
    // initialize the best score and shift
    double bestScore = Double.MAX_VALUE;
    int bestShift = 0;

    // try all possible shifts
    for (int shift = 0; shift < 26; shift++) {
      // initialize the score
      double score = 0;
      for (int i = 0; i < 26; i++) {
        // get the character at the current index
        char c = (char) ((i + shift) % 26 + 'A');
        // double groupFrequency = groupFrequencies.getOrDefault(c, 0.0);
        // check if freqGroup has the character, if not, set to 0.0
        double groupFrequency = 0.0;
        for (FreqModel model : freqGroup) {
          if (model.getCharacter() == c) {
            groupFrequency = model.getFrequency();
            break;
          }
        }

        // calculate the score
        score += Math.abs(groupFrequency - VignereUtils.ENGLISH_FREQUENCIES[i]);
      }

      // update the best score and shift if the current score is better
      if (score < bestScore) {
        bestScore = score;
        bestShift = shift;
      }
    }

    // return the best shift
    return bestShift;
  }
}
