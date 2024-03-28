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
  public boolean exercise2Enabled = false;
  public boolean exercise3Enabled = true;
  public boolean exercise4Enabled = false;
  public boolean exercise5Enabled = false;
  public boolean exercise6Enabled = false;
  public boolean exercise7Enabled = false;

  // decoded string so that it can be accessed by the AfterSolvingProcessor
  private String cipherTxt = "";
  private String decodedTxt = "";

  // basic getters & setters
  public String getDecodedTxt() {
    return decodedTxt;
  }

  public void setDecodedTxt(String decodedTxt) {
    this.decodedTxt = decodedTxt;
  }

  // basic helper methods
  public void setCipherTxt(String txtData) {
    this.cipherTxt = new String(txtData).trim();
  }

  public void textContains(String text, String allegedlyDecodedTxt) {
    if (text.contains(allegedlyDecodedTxt)) {
      setDecodedTxt(allegedlyDecodedTxt);
    }
  }

  // * Exercise [#1] method
  /* The plaintext comes from tess26.txt and is encoded with a Caesar cipher. */
  @BeforeSolving
  @AfterSolving
  public void exercise1() {
    // while tess26 does not contain the decoded string, keep decoding
    while (!tess26.contains(cipherTxt)) {
      // decode the string
      cipherTxt = CipherUtils.rotateCaesarCipher(cipherTxt, 1);
    }

    // if tess26 contains the decoded string, set the decoded string
    textContains(tess26, cipherTxt);
  }

  // * Exercise [#2] method
  /*
   * The plaintext comes from tess26.txt and is encoded with a Vigenere cipher
   * using
   * the 21-letter key TESSOFTHEDURBERVILLES.
   */
  @BeforeSolving
  @AfterSolving
  public void exercise2() {
    String key = "TESSOFTHEDURBERVILLES";

    // if decode the ciphetext using the given key
    cipherTxt = CipherUtils.decryptVignereCipher(cipherTxt, key);

    // if tess26 contains the decoded string, set the decoded string
    textContains(tess26, cipherTxt);
  }

  // * Exercise [#3] method
  /*
   * The plaintext
   * comes from
   * tess26.txt and
   * is encoded
   * with a
   * Vigenere cipher.
   * The key
   * is an
   * arbitrary sequence
   * of six
   * 
   * letters (i.e. not necessarily forming an English
   * word).
   */
  @BeforeSolving
  @AfterSolving
  public void exercise3() {
    // divide the text into 6 groups
    String[] groups = CipherUtils.divideTextIntoBlocksOfNLength(decodedTxt, 6);

    // for each group, perform frequency analysis
    ArrayList<FreqModel> freq = CipherUtils.analyzeCharacterFrequencies(groups);

    // sort the frequencies
    ArrayList<FreqModel> sortedFreq = CipherUtils.sortCharacterFrequencies(freq);

    // print out the sorted frequencies
    CipherUtils.printCharacterFrequencies(sortedFreq);
  }

  // * Exercise [#4] method
  /*
   * The plaintext
   * comes from
   * tess26.txt and
   * is encoded
   * with a
   * Vigenere cipher.
   * The key
   * is an
   * arbitrary sequence
   * of between 4 and 6 letters.
   */
  @BeforeSolving
  @AfterSolving
  public void exercise4() {

  }

  // * Exercise [#5] method
  /*
   * The plaintext comes from tess26.txt and is encoded with a transposition
   * cipher,
   * as follows: the plaintext is written row-wise across a certain number of
   * columns,
   * between 4 and 6. (You must figure out how many columns were used.) The
   * ciphertext is formed by reading out successive columns from left to right.
   */
  @BeforeSolving
  @AfterSolving
  public void exercise5() {
  }

  // * Exercise [#6] method
  /*
   * The plaintext comes from tess26.txt and is encoded with a transposition
   * cipher,
   * as follows: the plaintext is written row-wise across six columns. The
   * ciphertext is
   * formed by reading out successive columns in an arbitrary order (which you
   * must
   * figure out to decipher the message). Hint:look for common pairs of letters,
   * such as
   * 'th'.
   */
  @BeforeSolving
  @AfterSolving
  public void exercise6() {
  }

  // * Exercise [#7] method
  /*
   * The plaintext comes from tess27.txt and is encoded with a general
   * substitution
   * cipher, using a randomly chosen mapping from the 27-character alphabet onto
   * itself. Note that normally (i.e. except by chance) a vertical bar will be
   * mapped
   * onto some other letter of the alphabet.
   */
  @BeforeSolving
  @AfterSolving
  public void exercise7() {
  }
}
