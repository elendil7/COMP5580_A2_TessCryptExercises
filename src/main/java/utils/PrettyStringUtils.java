package src.main.java.utils;

public class PrettyStringUtils {
  // put prefix of custom word before string
  public static String prefix(String prefix, String string) {
    return String.format("%s%s", prefix, string);
  }

  // put 1 blank line below string
  public static String blankLineBelow(String string) {
    return String.format("%s\n", string);
  }

  // put 1 blank line above string
  public static String blankLineAbove(String string) {
    return String.format("\n%s", string);
  }

  // put 1 blank line above and below string
  public static String blankLineAboveAndBelow(String string) {
    return String.format("\n%s\n", string);
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
  public static void printExerciseTitle(int exerciseNumber, String username) {
    System.out.println(
        String.format("\n%s\nSOLVING %s%s FOR USER [%s]\n%s\n", "====================================", "EXERCISE #",
            exerciseNumber,
            username,
            "===================================="));
  }

  // print with blank line below
  public static void printWithBlankLineBelow(String string) {
    System.out.println(blankLineBelow(string));
  }
}