package src.main.java.core;

import src.main.java.annotations.AfterSolving;
import src.main.java.annotations.BeforeSolving;
import src.main.java.configuration.AppConfig;
import src.main.java.managers.FilesystemManager;
import src.main.java.models.FreqModel;
import src.main.java.utils.CaesarUtils;
import src.main.java.utils.SubstitutionUtils;
import src.main.java.utils.TranspositionUtils;
import src.main.java.utils.VignereUtils;

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
  public boolean exercise1Enabled = AppConfig.runExercise1Enabled;
  public boolean exercise2Enabled = AppConfig.runExercise2Enabled;
  public boolean exercise3Enabled = AppConfig.runExercise3Enabled;
  public boolean exercise4Enabled = AppConfig.runExercise4Enabled;
  public boolean exercise5Enabled = AppConfig.runExercise5Enabled;
  public boolean exercise6Enabled = AppConfig.runExercise6Enabled;
  public boolean exercise7Enabled = AppConfig.runExercise7Enabled;

  // decoded string so that it can be accessed by the AfterSolvingProcessor
  private String cipherTxt = "";
  private String decodedTxt = "";

  // string field for explanation of the exercise
  private String explanation = "";

  // basic getters & setters
  public String getDecodedTxt() {
    return decodedTxt;
  }

  public void setDecodedTxt(String decodedTxt) {
    this.decodedTxt = decodedTxt;
  }

  public String getExplanation() {
    return explanation;
  }

  public void setExplanation(String explanation) {
    this.explanation = explanation;
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

  // clean fies
  public void cleanFields() {
    cipherTxt = "";
    decodedTxt = "";
    explanation = "";
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

    // set explanation
    setExplanation(
        "I used my java program to brute force the Caesar cipher, rotating the cipher text by 1 letter each time then checking if the decoded text is in tess26.txt. Once the decoded text was found, I set it as the decoded text and wrote it to the output file.");
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

    // set explanation
    setExplanation(
        "I used the Vigenere cipher decryption method to decode the cipher text using the key TESSOFTHEDURBERVILLES in my java program. The vignere key was given in the exercise, so I did not have to find it. The key was repeated to the length of the cipherTxt, then each character of the cipher text was shifted by the corresponding character in the key to decode the text. Once the decoded text was found, I set it as the decoded text and wrote it to the output file.");
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

    // set explanation
    setExplanation(
        "I used the Vigenere cipher decryption method to decode the cipher text using the key I found in my java program. The vignere key was not given in the exercise, so I had to find it. I divided the cipher text into 6 groups using the modulo operator, then found the character frequencies for each group respectively. I then found the key by comparing the character frequencies to the English language character frequencies, determining the optimal shift for each group. Using the newfound key, I decoded the ciphertext in the same way as exercise 2. Once the decoded text was found, I set it as the decoded text in my java program and wrote it to the output file.");
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

    // set explanation
    setExplanation(
        "I used the Vigenere cipher decryption method to decode the cipher text using the key I found in my java program. The vignere key was not given in the exercise, so I had to find it. I divided the cipher text into 4, 5, and 6 groups respectively using the modulo operator, then found the character frequencies for each group respectively. I then found the key by comparing the character frequencies to the English language character frequencies, determining the optimal shift for each group. Using the newfound keys, I decoded the ciphertext in the same way as exercise 2, by decrypting the ciphertext with keys of length (4,5,6) respectively. Once the decoded text was found (by comparing the decoded output to the tess26 plaintext file), I set it as the decoded text in my java program and wrote it to the output file.");
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

    // set explanation
    setExplanation(
        "I used my java program to decode the cipher text using a transposition cipher. I filled a 2D array with the cipher text, row-wise, spanning 4, 5, then 6 columns (per iteration of the loop). For each iteration, I read out the columns from right to left (reverse order) to get the original plaintext. I checked if the decoded text was in tess26.txt. If it was, I set the decoded text. If it was not, I continued to the next iteration (using 4,5,6 columns respectively). Once the decoded text was found, I set it as the decoded text in my java program and wrote it to the output file.");
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

    // set explanation
    setExplanation(
        "I used my java program to decode the cipher text using a transposition cipher. I generated permutations of every possible variation of inserting the ciphertext into the 6 columns. I then decoded the cipher text using every possible permutation (essentially brute force), till the decoded text at the current iteration was located in tess26.txt. If it was, I set the decoded text. If it was not, I continued to the next permutation. Once the decoded text was found, I set it as the decoded text in my java program and wrote it to the output file.");
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

    // set explanation
    setExplanation(
        "I used my java program to decode the cipher text using a general substitution cipher. I compared the decoded text to tess27.txt, seeing which characters matched at every index to get the most probable decoded text. Once the decoded text was found, I set it as the decoded text in my java program and wrote it to the output file.");
  }
}
