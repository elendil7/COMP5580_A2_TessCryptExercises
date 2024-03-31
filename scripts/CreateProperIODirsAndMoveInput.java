package scripts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import java.util.List;
import java.util.stream.Collectors;

public class CreateProperIODirsAndMoveInput {
  // main class
  public static void main(String[] args) {
    // get current path + path to users folder
    String curDir = System.getProperty("user.dir");
    String baseDir = "resources/users";

    // for every directory in this path
    File dir = new File(curDir + "/" + baseDir);

    // for every user directory
    try {
      // get each folder inside dir
      File[] userDirs = dir.listFiles(File::isDirectory);

      // for each user directory
      for (File userDir : userDirs) {
        // print user dir name
        System.out.println(userDir.getName());

        // create a new input and output directory
        String inputDir = userDir.getPath() + "/input";
        String outputDir = userDir.getPath() + "/output";
        new File(inputDir).mkdirs();
        new File(outputDir).mkdirs();

        // get files in the folder
        List<File> filesInFolder = Files.walk(Paths.get(userDir.getPath()))
            .filter(Files::isRegularFile)
            .map(Path::toFile)
            .collect(Collectors.toList());

        // paste these files into the input directory, then delete them
        for (File file : filesInFolder) {
          // move the file to the input directory
          file.renameTo(new File(inputDir + "/" + file.getName()));
        }
      }
      // print all the files in the directory
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
