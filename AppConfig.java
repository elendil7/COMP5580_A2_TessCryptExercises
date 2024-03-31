public class AppConfig {
  private static AppConfig instance = null;

  public static boolean generalLoggingEnabled = true;
  public static boolean cipherLoggingEnabled = true;
  public static boolean plaintextLoggingEnabled = true;
  public static boolean debugLoggingEnabled = false;

  public static boolean runExercise1Enabled = true;
  public static boolean runExercise2Enabled = true;
  public static boolean runExercise3Enabled = true;
  public static boolean runExercise4Enabled = true;
  public static boolean runExercise5Enabled = true;
  public static boolean runExercise6Enabled = true;
  public static boolean runExercise7Enabled = false;

  public static boolean solveOnlyForAragorn = false;

  public static AppConfig getInstance() {
    if (instance == null) {
      instance = new AppConfig();
    }
    return instance;
  }
}
