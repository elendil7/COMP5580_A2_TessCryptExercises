package utils;

public class PrettyStringUtils {
  // put prefix of custom word before string
  public static String prefix(String prefix, String string) {
    return String.format("%s%s", prefix, string);
  }

  // put 1 blank line below string
  public static String blankLineBelow(String string) {
    return String.format("%s\n", string);
  }

  // and print with prefix
  public static void printWithPrefix(String prefix, String string) {
    System.out.println(prefix(prefix, string));
  }

  // and print with prefix and blank lines
  public static void printWithPrefixAndBlankLines(String prefix, String string) {
    System.out.println(blankLineBelow(prefix(prefix, string)));
  }

  // pretty print with authority and box, "Exercise #number"
  public static void printExerciseTitle(int exerciseNumber) {
    System.out.println(
        String.format("\n%s\n%s%s\n%s\n", "====================", "EXERCISE #", exerciseNumber,
            "===================="));
  }

  // print with blank line below
  public static void printWithBlankLineBelow(String string) {
    System.out.println(blankLineBelow(string));
  }
}