package models;

public class FailedExerciseForUserModel {
  private String userName;
  private String exerciseName;
  private String path;

  public FailedExerciseForUserModel(String userName, String exerciseName, String path) {
    this.userName = userName;
    this.exerciseName = exerciseName;
    this.path = path;
  }

  public String getUserName() {
    return userName;
  }

  public String getExerciseName() {
    return exerciseName;
  }

  public String getPath() {
    return path;
  }

  // QOL methods
  public void prettyPrint() {
    System.out.println(String.format("User: %s, Exercise: %s, Path: %s", userName, exerciseName, path));
  }
}
