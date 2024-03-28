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
  public static String rotateVignereCipher(String encoded, String key) {
    String decoded = "";
    encoded = encoded.toUpperCase();
    for (int i = 0, j = 0; i < encoded.length(); i++) {
      char letter = encoded.charAt(i);
      decoded += (char) ((letter - key.charAt(j) + 26) % 26 + 65);
      j = ++j % key.length();
    }
    return decoded;
  }

