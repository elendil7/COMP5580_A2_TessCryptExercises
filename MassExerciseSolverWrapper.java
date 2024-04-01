import annotations.BeforeSolving;
import configuration.AppConfig;
import models.FailedExerciseForUserModel;
import processors.AfterSolvingProcessor;
import processors.BeforeSolvingProcessor;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MassExerciseSolverWrapper {
  private ArrayList<FailedExerciseForUserModel> failedExercises;

  public MassExerciseSolverWrapper() {
    this.failedExercises = new ArrayList<>();
  }

  // getter for failedExercises
  public ArrayList<FailedExerciseForUserModel> getFailedExercises() {
    return failedExercises;
  }

  // method that creates and adds failed exercises to the failedExercises
  // arraylist
  public void createAndAddFailedExercise(String user, String methodName, String path) {
    failedExercises.add(new FailedExerciseForUserModel(user, methodName, path));
  }

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
        if (method.getName().toLowerCase().contains("exercise")) {
          System.out.print(method.getName() + " ");
        }
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

              // try to run the method & its helper methods
              try {
                // set the cipher & decoded text & explanation to empty strings
                exSolver.cleanFields();

                // grab the exercise file data
                String exerciseFileData = exSolver.getExerciseFileData(methodName);

                // run .setCipherTxt method, passing exerciseFileData as a parameter
                exSolver.setCipherTxt(exerciseFileData);

                // run before solving processor
                if (AppConfig.cipherLoggingEnabled && AppConfig.generalLoggingEnabled) {
                  BeforeSolvingProcessor.process(methodName,
                      exSolver.getExerciseFileData(method.getName().toLowerCase()), user);
                }

                // run the method
                method.invoke(exSolver);

                // run after solving processor
                if (AppConfig.plaintextLoggingEnabled) {
                  AfterSolvingProcessor.process(exSolver.getDecodedTxt());
                }

                // if the decoded text is empty, display error
                if (exSolver.getDecodedTxt().isEmpty()) {
                  // add to failed exercises arraylist
                  failedExercises.add(new FailedExerciseForUserModel(user, methodName,
                      exSolver.getPathToInput() + "/" + methodName + ".txt"));

                  if (AppConfig.generalLoggingEnabled) {
                    System.out.println(String.format("\nFailed to decode cipher for %s.\nUser: %s\nPath: %s",
                        methodName, user, exSolver.getPathToInput() + "/" + methodName + ".txt"));
                  }
                }

                // write the decoded text & explanation to the /resources/output/ folder, with
                // empty string if no decoded text
                // matter if errored or not
                // note: only aragorn (me) gets my own custom explanation
                exSolver.writeToFile(methodName, exSolver.getDecodedTxt(),
                    user.equals("_aragorn") ? exSolver.getExplanation() : "");
              } catch (Exception e) {
                System.out.println("Error invoking method " + methodName);
                throw new RuntimeException(e);
              }
            }

          }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
          System.out.println("Field for toggling exercise " + methodName.substring(8) + " not found.");
          e.printStackTrace();
          throw new RuntimeException(e);
        }
      }
    }

    // print out failed exercises
    if (!failedExercises.isEmpty()) {
      System.out.println("<<<<<<<<Failed exercises:>>>>>>>>");
      for (FailedExerciseForUserModel failedExercise : failedExercises) {
        failedExercise.prettyPrint();
      }

      // return false, as there are failed exercises
      return false;
    }

    // if all exercises were solved successfully, return true
    return true;
  }
}
