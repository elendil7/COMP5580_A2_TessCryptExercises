import java.lang.reflect.Method;

import annotations.BeforeSolving;
import processors.AfterSolvingProcessor;
import processors.BeforeSolvingProcessor;

public class Launcher {
  public static void main(String[] args) {
    ExerciseSolver exerciseSolver = new ExerciseSolver();
    Method[] methods = exerciseSolver.getClass().getDeclaredMethods();
    for (Method method : methods) {
      if (method.isAnnotationPresent(BeforeSolving.class)) {
        // grab method name to lower case
        String methodName = method.getName().toLowerCase();

        // check if runExercise[n] is true. If so, process the method along with its
        // annotation(s)
        try {
          if ((boolean) exerciseSolver.getClass().getField(methodName + "Enabled").get(exerciseSolver)) {
            try {
              // set the cipher & decoded text & explanation to empty strings
              exerciseSolver.setCipherTxt("");
              exerciseSolver.setDecodedTxt("");
              exerciseSolver.setExplanation("");

              // grab the exercise file data
              String exerciseFileData = exerciseSolver.getExerciseFileData(methodName);

              // run .setCipherTxt method, passing exerciseFileData as a parameter
              exerciseSolver.setCipherTxt(exerciseFileData);

              // run before solving processor
              BeforeSolvingProcessor.process(methodName,
                  exerciseSolver.getExerciseFileData(method.getName().toLowerCase()));

              // run the method
              method.invoke(exerciseSolver);

              // run after solving processor
              AfterSolvingProcessor.process(exerciseSolver.getDecodedTxt());

              // write the decoded text & explanation to the /resources/output/ folder
              exerciseSolver.writeToFile(methodName, exerciseSolver.getDecodedTxt(),
                  exerciseSolver.getExplanation());
            } catch (Exception e) {
              System.out.println("Error invoking method " + methodName);
              e.printStackTrace();
            }
          }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
          System.out.println("Field for toggling exercise " + methodName.substring(8) + " not found.");
          e.printStackTrace();
        }
      }
    }
  }
}