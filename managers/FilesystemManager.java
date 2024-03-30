package managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FilesystemManager {
  private String curDir;
  private String baseDir;
  private String specificOutputDir;
  private String pathToExercises;
  private String pathToTexts;
  private String pathToOutput;

  public FilesystemManager() {
    this.curDir = System.getProperty("user.dir");
    this.baseDir = "resources";
    this.specificOutputDir = "aragorn";
    this.pathToExercises = this.baseDir + "/exercises";
    this.pathToTexts = this.baseDir + "/texts";
    this.pathToOutput = this.baseDir + "/output" + "/" + this.specificOutputDir;
  }

  // Method for loading an exercise by reading its contents
  private String readAndReturnFileData(String path) {
    try {
      File myObj = new File(path);
      Scanner myReader = new Scanner(myObj);
      String data = "";
      while (myReader.hasNextLine()) {
        data += myReader.nextLine();
      }
      myReader.close();
      // clear white spaces, and return data
      return data.replaceAll("\\s+", "");
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
      throw new RuntimeException("File not found");
    }
  }

  // getter methods
  public String getExerciseFileData(String exerciseNum) {
    return this
        .readAndReturnFileData(String.format("%s/%s/c%s.txt", curDir, this.pathToExercises, exerciseNum));
  }

  public String getTextFileData(String fileName) {
    return this.readAndReturnFileData(String.format("%s/%s/%s.txt", curDir, this.pathToTexts, fileName));
  }

  // write the decoded text & explanation to the file
  public void writeToFile(String fileName, String decodedTxt, String explanation) {
    try {
      // log all inputs
      // System.out.println(String.format("Writing to file: %s/%s/%s.txt", curDir,
      // this.pathToOutput, fileName));

      // make the output dir, if not exists
      File dir = new File(String.format("%s/%s", curDir, this.pathToOutput));
      dir.mkdirs();

      // create the file
      File myObj = new File(String.format("%s/%s/%s.txt", curDir, this.pathToOutput, fileName));
      myObj.createNewFile();

      // write the first 30 chars of the decoded text to the file
      java.io.FileWriter myWriter = new java.io.FileWriter(myObj);
      myWriter.write(decodedTxt.substring(0, 30));

      // write the explanation to the file
      myWriter.write("\n\n");
      myWriter.write(explanation);

      // close the writer
      myWriter.close();
    } catch (Exception e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
