package processors;

import utils.PrettyStringUtils;

public class AfterSolvingProcessor {
  public static void process(String decodedString) {
    // construct output string
    String output = String.format("%s %s", "Decoded string:", decodedString);

    // if decoded string is empty, concatenate "No decoded string found."
    if (decodedString.isEmpty()) {
      output = String.format("%s%s", output, "No decoded string found.");
    }

    // print in pretty format
    PrettyStringUtils.printWithBlankLineBelow(output);
  }
}