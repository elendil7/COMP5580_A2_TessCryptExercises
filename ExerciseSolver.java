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

  // bool toggles for which exercises I want to run
  public boolean exercise1Enabled = false;
  public boolean exercise2Enabled = true;
  public boolean exercise3Enabled = false;
  public boolean exercise4Enabled = false;
  public boolean exercise5Enabled = false;
  public boolean exercise6Enabled = false;
  public boolean exercise7Enabled = false;

  // decoded string so that it can be accessed by the AfterSolvingProcessor
  private String decodedString = "";

  // basic getters & setters
  public String getDecodedString() {
    return decodedString;
  }

  public void setDecodedString(String decodedString) {
    this.decodedString = decodedString;
  }

  // * Exercise [#1] method
  /* The plaintext comes from tess26.txt and is encoded with a Caesar cipher. */
  @BeforeSolving
  @AfterSolving
  public void exercise1() {
    // make new variable holding cexercise1
    String decoded = new String(cexercise1);

    // while tess26 does not contain the decoded string, keep decoding
    while (!tess26.contains(decoded)) {
      // decode the string
      decoded = CipherUtils.rotateCaesarCipher(decoded, 1);
    }

    // set the decoded string
    setDecodedString(decoded);
  }

