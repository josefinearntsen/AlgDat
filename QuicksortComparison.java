//package org.example;

import java.util.Random;

public class QuicksortComparison {

  public static void main(String[] args) {
    int size = 50000000;
    int min = 0;
    int max = size - 1;
    int[] randomArray = randomOrder(size);
    int[] randomArrayDual = randomOrder(size);

    int[] duplicateArray = duplicateOrder(size);
    int[] duplicateArrayDual = duplicateOrder(size);

    int[] sortedArray = sortedOrder(size);
    int[] sortedArrayDual = sortedOrder(size);

    int[] reverseSortedArray = reverseSortedOrder(size);
    int[] reverseSortedArrayDual = reverseSortedOrder(size);


    // Test random table
    System.out.println("\nTesting Quicksort with random data");
    runQuicksort(randomArray, min, max);

    System.out.println("\nTesting Dual Pivot Quicksort with random data");
    runDualPivotQuicksort(randomArrayDual, min, max);

    // Test duplicate date table
    System.out.println("\nTesting Quicksort with duplicate data");
    runQuicksort(duplicateArray, min, max);

    System.out.println("\nTesting Dual Pivot Quicksort with duplicate data");
    runDualPivotQuicksort(duplicateArrayDual, min, max);

    // Test sorted data
    System.out.println("\nTesting Quicksort with sorted data");
    runQuicksort(sortedArray, min, max);

    System.out.println("\nTesting Dual Pivot Quicksort with sorted data");
    runDualPivotQuicksort(sortedArrayDual, min, max);

    // Test reverse sorted data
    System.out.println("\nTesting Quicksort with reverse sorted data");
    runQuicksort(reverseSortedArray, min, max);

    System.out.println("\nTesting Dual Pivot Quicksort with reverse sorted data");
    runDualPivotQuicksort(reverseSortedArrayDual, min, max);

  }



  public static int[] randomOrder(int size) {
    Random random = new Random();
    int[] randomArray = new int[size];

    for (int i = 0; i < size; i++) {
      randomArray[i] = random.nextInt();
    }

    return randomArray;
  }

  public static int[] duplicateOrder(int size) {
    int[] array = new int[size];
    for (int i = 0; i < size; i++) {
      array[i] = (i % 2 == 0) ? 42 : i;
    }
    return array;
  }

  public static int[] sortedOrder(int size) {
    int[] array = new int[size];
    for (int i = 0; i < size; i++) {
      array[i] = i;
    }
    return array;
  }

  public static int[] reverseSortedOrder(int size) {
    int[] array = new int[size];
    for (int i = 0; i < size; i++) {
      array[i] = size - i;
    }
    return array;
  }

  public static void swap(int []t, int i, int j) {
    int k = t[j];
    t[j] = t[i];
    t[i] = k;
  }

  public static void quicksort(int []t, int v, int h) {
    if (h - v > 2) {
      int delepos = split(t, v, h);
      quicksort(t, v, delepos - 1);
      quicksort(t, delepos + 1, h);
    } else median3sort(t, v, h);
  }

  private static int median3sort(int []t, int v, int h) {
    int m = (v + h) / 2;
    if (t[v] > t[m]) swap(t, v, m);
    if (t[m] > t[h]) {
      swap(t, m, h);
      if (t[v] > t[m]) swap(t, v, m);
    }
    return m;
  }

  private static int split(int []t, int v, int h) {
    int iv, ih;
    int m = median3sort(t, v, h);
    int dv = t[m];
    swap(t, m, h - 1);
    for (iv = v, ih = h - 1;;) {
      while (t[++iv] < dv);
      while (t[--ih] > dv);
      if (iv >= ih) break;
      swap(t, iv, ih);
    }
    swap(t, iv, h - 1);
    return iv;
  }


  public static void dualPivotQuicksort(int[] arr, int low, int high){

    if(low < high){
      int[] piv;
      piv = dualSplit(arr, low, high);

      dualPivotQuicksort(arr, low, piv[0] - 1);
      dualPivotQuicksort(arr, piv[0] + 1, piv[1] - 1);
      dualPivotQuicksort(arr, piv[1] + 1, high);
    }
  }


  static int[] dualSplit(int[] arr, int low, int high)
  {
    int swapValue1 = arr[low];
    arr[low] = arr[low + (high-low)/3];
    arr[low + (high-low)/3] = swapValue1;

    if (arr[low] > arr[high])
      swap(arr, low, high);

    // p is the left pivot, and q
    // is the right pivot.
    int j = low + 1;
    int g = high - 1, k = low + 1,
        p = arr[low], q = arr[high];

    while (k <= g)
    {

      // If elements are less than the left pivot
      if (arr[k] < p)
      {
        swap(arr, k, j);
        j++;
      }

      // If elements are greater than or equal
      // to the right pivot
      else if (arr[k] >= q)
      {
        while (arr[g] > q && k < g)
          g--;

        swap(arr, k, g);
        g--;

        if (arr[k] < p)
        {
          swap(arr, k, j);
          j++;
        }
      }
      k++;
    }
    j--;
    g++;

    // Bring pivots to their appropriate positions.
    swap(arr, low, j);
    swap(arr, high, g);

    // Returning the indices of the pivots
    // because we cannot return two elements
    // from a function, we do that using an array.
    return new int[] { j, g };
  }



  private static String testSort(int []table) {
    for (int i = 0; i < table.length - 1; i++) {
      if (table[i] > table[i+1]) {
        return "The table is not sorted: ";
      }
    }
    return "The table is sorted";
  }

  private static String testSum(int first, int after){
    if(first == after){
      return ("The sum is equal before and after sorting");
    } else {
      return ("The sum is not equal before and after sorting");
    }
  }


  public static int sumList(int []t) {
    int sum = 0;
    for (int i = 0; i < t.length; i++) {
      sum += t[i];
    }
    return sum;
  }

  private static void runQuicksort(int[] array, int min, int max) {
    // finding sum of the array before sorting
    int sum = sumList(array);

    // timing and running the algorithm
    long startTime = System.nanoTime();
    quicksort(array, min, max);
    long endTime = System.nanoTime();

    long elapsedTime = endTime - startTime;
    double elapsedTimeInSeconds = elapsedTime / 1_000_000_000.0; // Convert to seconds
    System.out.printf("Time taken: %.6f seconds%n", elapsedTimeInSeconds);

    // Checking if the array is sorted
    System.out.println(testSort(array));

    // finding sum of the array after sorting
    int sortedSum = sumList(array);
    System.out.println(
        testSum(sum, sortedSum)); // checking if the sum is the same before and after sorting
    System.out.println();
  }

  private static void runDualPivotQuicksort(int[] array, int min, int max) {
    // finding sum of the array before sorting
    int sum = sumList(array);

    // timing and running the algorithm
    long startTime = System.nanoTime();
    dualPivotQuicksort(array, min, max);
    long endTime = System.nanoTime();

    long elapsedTime = endTime - startTime;
    double elapsedTimeInSeconds = elapsedTime / 1_000_000_000.0; // Convert to seconds
    System.out.printf("Time taken: %.6f seconds%n", elapsedTimeInSeconds);

    // Checking if the array is sorted
    System.out.println(testSort(array));

    // finding sum of the array after sorting
    int sortedSum = sumList(array);
    System.out.println(
        testSum(sum, sortedSum)); // checking if the sum is the same before and after sorting
    System.out.println();
  }
}

