package processors;

import utils.PrettyStringUtils;

public class BeforeSolvingProcessor {
  public static void process(String methodName, String exerciseFileData) {
    // print the exercise name, passing last 1 char of method name as an int
    PrettyStringUtils.printExerciseTitle(Integer.parseInt(methodName.substring(8)));

    // print the encoded string
    PrettyStringUtils.printWithPrefixAndBlankLines("Encoded string: ", exerciseFileData);
  }
}