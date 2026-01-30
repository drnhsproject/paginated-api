package com.example.pagination.service;

import java.util.Arrays;

/**
 * Service class to demonstrate finding a missing number in an array using
 * sorting.
 * <p>
 * Time Complexity: O(N log N) due to sorting.
 * Space Complexity: O(1) or O(log N) depending on the sorting implementation.
 */
public class MissingArray {

    /**
     * Finds the missing number in an array containing distinct numbers taken from
     * 0, 1, 2, ..., n.
     * <p>
     * Logic:
     * 1. Sort the array.
     * 2. Iterate through the array and check if the index matches the value.
     * 3. The first index where arr[i] != i is the missing number.
     * 4. If all indices match, the missing number is n (array length).
     */
    public void missingArraySort() {
        int[] arr = { 3, 0, 1 };
        Arrays.sort(arr);
        int arrLength = arr.length;

        for (int i = 0; i < arrLength; i++) {
            if (arr[i] != i) {
                System.out.println("Missing number is: " + i);
                return;
            }
        }
    }

    public static void main(String[] args) {
        new MissingArray().missingArraySort();
    }
}
