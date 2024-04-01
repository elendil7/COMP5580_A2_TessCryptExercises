package scripts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipAllOutputFolders {
  // credit:
  // https://stackoverflow.com/questions/15968883/how-to-zip-a-folder-itself-using-java
  public static void pack(String sourceDirPath, String zipFilePath) throws IOException {
    // make zipFilePath folder recursively if not exist
    Files.createDirectories(Paths.get(zipFilePath).getParent());

    // delete the zip file if it already exists
    Files.deleteIfExists(Paths.get(zipFilePath));

    Path p = Files.createFile(Paths.get(zipFilePath));
    try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
      Path pp = Paths.get(sourceDirPath);
      Files.walk(pp)
          .filter(path -> !Files.isDirectory(path))
          .forEach(path -> {
            ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
            try {
              zs.putNextEntry(zipEntry);
              Files.copy(path, zs);
              zs.closeEntry();
            } catch (IOException e) {
              System.out.println("Error in pack method:");
              System.err.println(e);
            }
          });
    }
  }

  public static void main(String[] args) {
    try {
      String rootDir = System.getProperty("user.dir");
      String userDir = "/resources/users";
      String absoluteUserDir = rootDir + userDir;
      String outputDirPathWithoutUser = rootDir + "/resources/outputs";

      System.out.println("\n<<<Zipping everyone's output folders located in: " + userDir + ">>>\n");

      // grab the subdirs (users) in the users dir
      File[] users = new File(absoluteUserDir).listFiles(File::isDirectory);

      // for every user
      for (File user : users) {
        // grab the output folder from inside the user dir
        File sourceDir = new File(user.getAbsolutePath() + "/output");

        // create path to the output folder
        String outputDir = String.format("%s/%s.zip", outputDirPathWithoutUser, user.getName());

        System.out.println(String.format("Zipping %s's output folder...", user.getName()));

        // zip the output folder
        pack(sourceDir.getAbsolutePath(), outputDir);

        System.out.println(String.format("Finished zipping %s's output folder", user.getName()));
      }

      System.out.println(String.format("\n<<<Finished zipping the output folders of [%d] users>>>\n", users.length));

    } catch (Exception e) {
      System.out.println("\nError zipping output folders: " + e);
    }
  }
}