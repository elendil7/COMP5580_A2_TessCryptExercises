package configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AppConfig {
  private static AppConfig instance = null;

  // all the fields below can be overridden by making a config.properties file in
  // the root directory
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
      instance = new AppConfig(); // create a new instance
      setup(); // setup the config
    }
    return instance;
  }

  // setup method that reads the config file and sets any necessary variables
  private static void setup() {
    // read the config file (config.properties)
    File configFile = new File("config.properties");

    System.out.println(configFile.getAbsolutePath());

    // if the config file does not exist, return (use default values)
    if (!configFile.exists()) {
      System.out.println("Config file not found. Using default values.");
      return;
    }

    // otherwise, open new scanner
    try (Scanner scanner = new Scanner(configFile)) {
      // while there are more lines in the config file
      while (scanner.hasNextLine()) {
        // grab the line
        String line = scanner.nextLine();

        // if the line is empty or a comment, skip it
        if (line.isEmpty() || line.startsWith("#")) {
          continue;
        }

        // split the line by the equals sign
        String[] split = line.split("=");

        // if the split length is not 2, skip the line
        if (split.length != 2) {
          continue;
        }

        // grab the key and value
        String key = split[0].trim();
        String value = split[1].trim();

        // set the value based on the key
        switch (key) {
          case "generalLoggingEnabled":
            generalLoggingEnabled = Boolean.parseBoolean(value);
            break;
          case "cipherLoggingEnabled":
            cipherLoggingEnabled = Boolean.parseBoolean(value);
            break;
          case "plaintextLoggingEnabled":
            plaintextLoggingEnabled = Boolean.parseBoolean(value);
            break;
          case "debugLoggingEnabled":
            debugLoggingEnabled = Boolean.parseBoolean(value);
            break;
          case "runExercise1Enabled":
            runExercise1Enabled = Boolean.parseBoolean(value);
            break;
          case "runExercise2Enabled":
            runExercise2Enabled = Boolean.parseBoolean(value);
            break;
          case "runExercise3Enabled":
            runExercise3Enabled = Boolean.parseBoolean(value);
            break;
          case "runExercise4Enabled":
            runExercise4Enabled = Boolean.parseBoolean(value);
            break;
          case "runExercise5Enabled":
            runExercise5Enabled = Boolean.parseBoolean(value);
            break;
          case "runExercise6Enabled":
            runExercise6Enabled = Boolean.parseBoolean(value);
            break;
          case "runExercise7Enabled":
            runExercise7Enabled = Boolean.parseBoolean(value);
            break;
          case "solveOnlyForAragorn":
            solveOnlyForAragorn = Boolean.parseBoolean(value);
            break;
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException("Error reading config file.");
    }
  }

  // reload the config
  public static void reload() {
    instance = new AppConfig();
  }
}
