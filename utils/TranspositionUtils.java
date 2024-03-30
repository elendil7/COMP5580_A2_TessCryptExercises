package utils;

public class TranspositionUtils {
  // decipher a transposition cipher with the number of columns known.
  // The plaintext is writen row-wise across a certain number of columns.
  // The ciphertext is formed by reading out successive columns from left to
  // right.
  public static String decipherTranspositionCipher(String cipherTxt, int numCols) {
    // get the number of rows
    int numRows = (int) Math.ceil((double) cipherTxt.length() / numCols);

    // create a matrix to hold the cipher text
    char[][] matrix = new char[numRows][numCols];

    // fill the matrix with the cipher text, down columns first, left to right
    int index = 0;
    // for each column
    for (int c = 0; c < numCols; c++) {
      // for each row
      for (int r = 0; r < numRows; r++) {
        // if the index is less than the length of the cipher text (to prevent erroring
        // on null characters)
        if (index < cipherTxt.length()) {
          // fill the matrix with the cipher text
          matrix[r][c] = cipherTxt.charAt(index);
          index++;
        }
      }
    }

    // create a string to hold the decoded text
    String decodedTxt = "";

    // read rows left to right, to get the decoded text
    for (int r = 0; r < numRows; r++) {
      for (int c = 0; c < numCols; c++) {
        decodedTxt += matrix[r][c];
      }
    }

    return decodedTxt.trim();
  }

  // print method to print character matrix
  public static void printMatrix(char[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j]);
      }
      System.out.println();
    }
  }
}
