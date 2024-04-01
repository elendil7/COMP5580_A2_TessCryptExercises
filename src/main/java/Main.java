package src.main.java;

import src.main.java.configuration.AppConfig;
import src.main.java.core.MassExerciseSolver;
import src.main.java.utils.PrettyStringUtils;

public class Main {
  public static void main(String[] args) {
    // generate appconfig instance
    AppConfig.getInstance();

    // make new mass exercise solver
    MassExerciseSolver massExerciseSolverWrapper = new MassExerciseSolver();

    // run the mass exercise solver
    boolean success = massExerciseSolverWrapper.run();

    // if success, print success message
    if (success) {
      System.out.println(PrettyStringUtils.blankLineBelow("All exercises solved successfully!"));
    } else {
      System.out.println(PrettyStringUtils.blankLineBelow("An error occurred while solving exercises."));
    }
  }
}
