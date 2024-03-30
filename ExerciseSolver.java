import annotations.AfterSolving;
import annotations.BeforeSolving;
import managers.FilesystemManager;
import models.FreqModel;
import utils.CaesarUtils;
import utils.SubstitutionUtils;
import utils.TranspositionUtils;
import utils.VignereUtils;

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
  public boolean exercise1Enabled = true;
  public boolean exercise2Enabled = true;
  public boolean exercise3Enabled = true;
  public boolean exercise4Enabled = true;
  public boolean exercise5Enabled = true;
  public boolean exercise6Enabled = true;
  public boolean exercise7Enabled = true;

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
    // if the ciphertxt is not the same length as the allegedly decoded text, throw
    // an error
    if (cipherTxt.length() != allegedlyDecodedTxt.length()) {
      throw new Error("The cipher text and allegedly decoded text are not the same length.");
    }

    // if the text contains the allegedly decoded text, set the decoded text (for
    // use in AfterSolvingProcessor)
    if (text.contains(allegedlyDecodedTxt)) {
      setDecodedTxt(allegedlyDecodedTxt);
    }
  }

  // new String method
  public String newStr(String txt) {
    return new String(txt);
  }

  // * Exercise [#1] method
  /* The plaintext comes from tess26.txt and is encoded with a Caesar cipher. */
  @BeforeSolving
  @AfterSolving
  public void exercise1() {
    // while tess26 does not contain the decoded string, keep decoding
    while (!tess26.contains(cipherTxt)) {
      // decode the string
      cipherTxt = CaesarUtils.rotateCaesarCipher(cipherTxt, 1);
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
    cipherTxt = VignereUtils.decryptVignereCipher(cipherTxt, key);

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
    String[] groups = VignereUtils.divideTextIntoNGroups(cipherTxt, 6);

    // get character frequencies for each group
    ArrayList<ArrayList<FreqModel>> freqs = VignereUtils.getCharacterFrequenciesForAllBlocks(groups);

    // find the vignere key
    String key = VignereUtils.findKey(freqs);

    // decode the ciphertext using the key
    cipherTxt = VignereUtils.decryptVignereCipher(cipherTxt, key);

    // if tess26 contains the decoded string, set the decoded string
    textContains(tess26, cipherTxt);
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
    // for every key length between 4 and 6
    for (int i = 4; i <= 6; i++) {
      // temp variable for holding current cipher text (to prevent overwriting the
      // original cipher text on each iteration)
      String cipherTxt = newStr(this.cipherTxt);

      // divide the text into i groups
      String[] groups = VignereUtils.divideTextIntoNGroups(cipherTxt, i);

      // get character frequencies for each group
      ArrayList<ArrayList<FreqModel>> freqs = VignereUtils.getCharacterFrequenciesForAllBlocks(groups);

      // find the vignere key
      String key = VignereUtils.findKey(freqs);

      // decode the ciphertext using the key
      cipherTxt = VignereUtils.decryptVignereCipher(cipherTxt, key);

      // if tess26 contains the decoded string, set the decoded string
      textContains(tess26, cipherTxt);

      // if the decoded text is found, break out of the loop
      if (tess26.contains(cipherTxt)) {
        break;
      }
    }

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
    /*
     * My thoughts:
     * 1. Fill a 2D array with the cipher text, row-wise, spanning 4, 5, then 6
     * columns (per iteration of the loop)
     * 2. For each iteration, read out the columns from right to left (reverse
     * order)
     * 3. Check if the decoded text is in tess26.txt
     * 4. If it is, set the decoded text
     * 5. If it is not, continue to the next iteration
     */

    // for each number of columns between 4 and 6
    for (int i = 4; i <= 6; i++) {
      // temp variable for holding current cipher text (to prevent overwriting the
      // original cipher text on each iteration)
      String cipherTxt = newStr(this.cipherTxt);

      // decode the cipher text using the current number of columns
      cipherTxt = TranspositionUtils.decipherTranspositionCipher(cipherTxt, i);

      // if tess26 contains the decoded string, set the decoded string
      textContains(tess26, cipherTxt);

      // if the decoded text is found, break out of the loop
      if (tess26.contains(cipherTxt)) {
        break;
      }
    }
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
    // generate permutations of the columns (6 columns)
    ArrayList<int[]> permutations = TranspositionUtils.generatePermutations(6);

    // for each permutation
    for (int[] permutation : permutations) {
      // temp variable for holding current cipher text (to prevent overwriting the
      // original cipher text on each iteration)
      String cipherTxt = newStr(this.cipherTxt);

      // decode the cipher text using the current permutation
      cipherTxt = TranspositionUtils.bruteForceDecipherTranspositionCipherWithNColumns(cipherTxt, permutation);

      // if tess26 contains the decoded string, set the decoded string
      textContains(tess26, cipherTxt);

      // if the decoded text is found, break out of the loop
      if (tess26.contains(cipherTxt)) {
        break;
      }
    }
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
    // Website used for decoding the ciphertext: https://planetcalc.com/8047/
    String decodedTxt = "INTEDASAMICCAUPONAHERA|HEEMAHSLFAPERFUN|TORILYAHSLFASCAIFAXECTAHSDANOTAYETAQUITEADIEDAOUTAHERAEYECAVSGUELYARECTEDAUPONATHEAREBOTECTATREECAINATHEALSNEAWHILEATHEAMICCAWSCAGIVENASCATHOUGHACHEAWEREANESRLYAUN|ONC|IOUCAOFAWHSTAHEADIDANOWATHEAOTHERACIDEAFORAOLDAS|QUSINTSN|EACSMEACHEATURNEDAHERAHESDAINATHEACSBEAPSCCIVEAWSYASCAONEABIGHTATURNASTATHEAREQUECTAOFASACMET|HERAORAHSIRDRECCERASNDAHEAMICCEDATHEAOTHERACIDEAHICALIPCATOU|HINGA|HEEMCATHSTAWEREADSBPASNDACBOOTHLYA|HILLASCATHEACMINAOFATHEABUCHROOBCAINATHEAFIELDCASROUNDAYOUADONTAGIVEABEAYOURABOUTHASNDAMICCABEAKS|MAYOUANEVERAWILLINGLYADOATHSTAYOULLANEVERALOVEABEAIAFESRAIAHSVEACSIDACOAOFTENAITAICATRUEAIAHSVEANEVERARESLLYASNDATRULYALOVEDAYOUASNDAIATHINMAIANEVERA|SNACHEASDDEDABOURNFULLYAPERHSPCAOFASLLATHINGCASALIEAONATHICATHINGAWOULDADOATHEABOCTAGOODATOABEANOWAKUTAIAHSVEAHONOURAENOUGHALEFTAL";

    // compare tess27 to the decoded text in full, see which characters match at
    // every index, to get most probable decoded text
    decodedTxt = SubstitutionUtils.compareTess27ToDecoded(tess27, decodedTxt);

    // if tess27 contains the decoded string, set the decoded string
    textContains(tess27, decodedTxt);
  }
}
