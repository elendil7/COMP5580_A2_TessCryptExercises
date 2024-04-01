import configuration.AppConfig;
import utils.PrettyStringUtils;

public class Launcher {
  public static void main(String[] args) {
    // generate appconfig instance
    AppConfig.getInstance();

    // make new mass exercise solver
    MassExerciseSolverWrapper massExerciseSolverWrapper = new MassExerciseSolverWrapper();

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