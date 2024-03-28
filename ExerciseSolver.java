import annotations.AfterSolving;
import annotations.BeforeSolving;
import managers.FilesystemManager;
import models.FreqModel;
import utils.CipherUtils;
import java.util.ArrayList;

public class ExerciseSolver extends FilesystemManager {
  // all data loaded from files
  private String cexercise1 = getExerciseFileData("exercise1");
  private String cexercise2 = getExerciseFileData("exercise2");
  private String cexercise3 = getExerciseFileData("exercise3");
  private String cexercise4 = getExerciseFileData("exercise4");
  private String cexercise5 = getExerciseFileData("exercise5");
  private String cexercise6 = getExerciseFileData("exercise6");
  private String cexercise7 = getExerciseFileData("exercise7");
  private String tess = getTextFileData("tess");
  private String tess26 = getTextFileData("tess26");
  private String tess27 = getTextFileData("tess27");

