package utils;

import java.util.ArrayList;
import java.util.List;

public class TranspositionUtils {
  // print method to print character matrix
  public static void printMatrix(char[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j]);
      }
      System.out.println();
    }
  }

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

  // generate permutations helper
  private static void generatePermutationsHelper(int N, List<Integer> list, List<Integer> currentPermutation,
      ArrayList<int[]> permutations) {
    // if the current permutation is the same length as the number of columns
    if (currentPermutation.size() == N) {
      // create an array to hold the current permutation
      int[] permutation = new int[N];
      // for each column
      for (int i = 0; i < N; i++) {
        // set the permutation to the current permutation
        permutation[i] = currentPermutation.get(i);
      }
      // add the permutation to the list of permutations
      permutations.add(permutation);
    } else {
      // for each column
      for (int i = 0; i < list.size(); i++) {
        // add the column to the current permutation
        currentPermutation.add(list.get(i));
        // create a new list to hold the columns
        List<Integer> newList = new ArrayList<Integer>(list);
        // remove the column from the list
        newList.remove(i);
        // recursively call the helper method
        generatePermutationsHelper(N, newList, currentPermutation, permutations);
        // remove the column from the current permutation
        currentPermutation.remove(currentPermutation.size() - 1);
      }
    }
  }

  // generate every permutation of N columns, e.g.,
  // for N=3, the permutations are: 0,1,2, 0,2,1, 1,0,2, 1,2,0, 2,0,1, 2,1,0
  public static ArrayList<int[]> generatePermutations(int N) {
    // create an array to hold the permutations
    ArrayList<int[]> permutations = new ArrayList<int[]>();

    // create an array to hold the columns
    int[] columns = new int[N];
    for (int i = 0; i < N; i++) {
      columns[i] = i;
    }

    // create a list to hold the columns
    List<Integer> list = new ArrayList<Integer>();
    for (int i = 0; i < N; i++) {
      list.add(i);
    }

    // create a list to hold the current permutation
    List<Integer> currentPermutation = new ArrayList<Integer>();

    // generate the permutations
    generatePermutationsHelper(N, list, currentPermutation, permutations);

    return permutations;
  }

  // decipher a transposition cipher with N columns (number of cols is known),
  // where plaintext is written row-wise across N columns. The ciphertext is
  // formed by reading successive columns in an unknown order.
  public static String bruteForceDecipherTranspositionCipherWithNColumns(String cipherTxt,
      int[] permutation) {
    // get the number of columns
    int numCols = permutation.length;
    // get the number of rows
    int numRows = (int) Math.ceil((double) cipherTxt.length() / numCols);

    // create a matrix to hold the cipher text
    char[][] matrix = new char[numRows][numCols];

    // fill the matrix with the cipher text, down columns, using the permutation
    // column number
    int index = 0;
    // for each column
    for (int c = 0; c < numCols; c++) {
      // for each row
      for (int r = 0; r < numRows; r++) {
        // if the index is less than the length of the cipher text (to prevent erroring
        // on null characters)
        if (index < cipherTxt.length()) {
          // fill the matrix with the cipher text
          matrix[r][permutation[c]] = cipherTxt.charAt(index);
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
}
