
public class Launcher {
  public static void main(String[] args) {
    // make new mass exercise solver
    MassExerciseSolverWrapper massExerciseSolverWrapper = new MassExerciseSolverWrapper();

    // run the mass exercise solver
    boolean success = massExerciseSolverWrapper.run();

    // if success, print success message
    if (success) {
      System.out.println("All exercises solved successfully!");
    } else {
      System.out.println("An error occurred while solving exercises.");
    }
  }
}