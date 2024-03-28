package processors;

import utils.PrettyStringUtils;

public class BeforeSolvingProcessor {
  public static void process(String exerciseFileData) {
    PrettyStringUtils.printWithPrefixAndBlankLines("Encoded string: ", exerciseFileData);
  }
}