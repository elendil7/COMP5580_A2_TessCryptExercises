package src.main.java.models;

public class FreqModel {
  private Character character;
  private int count;
  private double frequency;

  public FreqModel(Character character, int count, double frequency) {
    this.character = character;
    this.count = count;
    this.frequency = frequency;
  }

  // getters
  public Character getCharacter() {
    return character;
  }

  public int getCount() {
    return count;
  }

  public double getFrequency() {
    return frequency;
  }

  // setters
  public void setCharacter(Character character) {
    this.character = character;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public void setFrequency(double frequency) {
    this.frequency = frequency;
  }
}
