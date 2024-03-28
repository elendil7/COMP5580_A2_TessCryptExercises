package managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FilesystemManager {
  private String curDir;
  private String pathToExercises;
  private String pathToTexts;

  public FilesystemManager() {
    this.curDir = System.getProperty("user.dir");
    this.pathToExercises = "resources/exercises";
    this.pathToTexts = "resources/texts";
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

  public String getExerciseFileData(String exerciseNum) {
    return this
        .readAndReturnFileData(String.format("%s/%s/c%s.txt", curDir, this.pathToExercises, exerciseNum));
  }

  public String getTextFileData(String fileName) {
    return this.readAndReturnFileData(String.format("%s/%s/%s.txt", curDir, this.pathToTexts, fileName));
  }
}
