import annotations.BeforeSolving;
import configuration.AppConfig;
import processors.AfterSolvingProcessor;
import processors.BeforeSolvingProcessor;
import java.lang.reflect.Method;

public class MassExerciseSolverWrapper {
  public boolean run() {
    // make new exercise solver
    ExerciseSolver exSolver = new ExerciseSolver();

    // grab all methods in the class
    Method[] methods = exSolver.getClass().getDeclaredMethods();

    // grab all users by reading the /resources/users/* directory
    String[] users;
    if (AppConfig.solveOnlyForAragorn) {
      users = new String[] { "_aragorn" };
    } else {
      users = exSolver.getUsers();
    }

    // log methods & users if general logging is enabled
    if (AppConfig.generalLoggingEnabled) {
      System.out.println("Methods:");
      for (Method method : methods) {
        System.out.print(method.getName() + " ");
      }
      System.out.println();

      System.out.println("Solving exercises for users:");
      for (String user : users) {
        System.out.print(user + " ");
      }
      System.out.println();
    }

    for (Method method : methods) {
      if (method.isAnnotationPresent(BeforeSolving.class)) {
        // grab method name to lower case
        String methodName = method.getName().toLowerCase();

        // check if runExercise[n] is true. If so, process the method along with its
        // annotation(s)
        try {
          if ((boolean) exSolver.getClass().getField(methodName + "Enabled").get(exSolver)) {
            // for every user
            for (String user : users) {
              // set the user
              exSolver.setUser(user);

              if (AppConfig.generalLoggingEnabled) {
                System.out.println(String.format("Solving %s for user %s", methodName, user));
              }

              // try to run the method & its helper methods
              try {
                // set the cipher & decoded text & explanation to empty strings
                exSolver.setCipherTxt("");
                exSolver.setDecodedTxt("");
                exSolver.setExplanation("");

                // grab the exercise file data
                String exerciseFileData = exSolver.getExerciseFileData(methodName);

                // run .setCipherTxt method, passing exerciseFileData as a parameter
                exSolver.setCipherTxt(exerciseFileData);

                // run before solving processor
                if (AppConfig.cipherLoggingEnabled) {
                  BeforeSolvingProcessor.process(methodName,
                      exSolver.getExerciseFileData(method.getName().toLowerCase()));
                }

                // run the method
                method.invoke(exSolver);

                // if the decoded text is empty, display error
                if (exSolver.getDecodedTxt().isEmpty() && AppConfig.generalLoggingEnabled) {
                  System.out.println(String.format("\nFailed to decode cipher for %s.\nUser: %s\nPath: %s",
                      methodName, user, exSolver.getPathToInput() + "/" + methodName + ".txt"));
                }

                // run after solving processor
                if (AppConfig.plaintextLoggingEnabled) {
                  AfterSolvingProcessor.process(exSolver.getDecodedTxt());
                }

                // write the decoded text & explanation to the /resources/output/ folder
                // note: only aragorn (me) gets my own custom explanation
                exSolver.writeToFile(methodName, exSolver.getDecodedTxt(),
                    user.equals("_aragorn") ? exSolver.getExplanation() : "");
              } catch (Exception e) {
                System.out.println("Error invoking method " + methodName);
                e.printStackTrace();
                return false;
              }
            }

          }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
          System.out.println("Field for toggling exercise " + methodName.substring(8) + " not found.");
          e.printStackTrace();
          return false;
        }
      }
    }

    // return true if all exercises are solved successfully
    return true;
  }
}
