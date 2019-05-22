package com.freetymekiyan.algorithms.level.hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Given a collection of numbers that might contain duplicates, return all
 * possible unique permutations.
 * <p>
 * For example,
 * [1,1,2] have the following unique permutations:
 * [1,1,2], [1,2,1], and [2,1,1].
 * <p>
 * Tags: Backtracking
 */
class Permutations2 {

  /**
   * Lexicography Order next permutation
   * Find the next permutation in lexicographic order.
   * http://en.wikipedia.org/wiki/Permutation#Generation_in_lexicographic_order
   */
  public List<List<Integer>> permuteUniqueB(int[] num) {
    List<List<Integer>> res = new ArrayList<>();
    if (num == null || num.length == 0) return res;
    Arrays.sort(num);
    List<Integer> row = new ArrayList<>();
    for (int a : num) row.add(a);
    res.add(new ArrayList<>(row)); // first permutation
    while (nextPermutation(row)) { // if there is next permutation
      res.add(new ArrayList<>(row));
    }
    return res;
  }

  /**
   * e.g.: 1234 -> 1243, 1243 -> 1324
   * Traverse backward to get 3
   * Then traverse forward to get furthest number bigger than 3
   * Swap these two digits and reverse from next to last
   */
  private boolean nextPermutation(List<Integer> row) {
    int last = row.size() - 1;
    for (int pos = last - 1; pos >= 0; pos--) {
      if (row.get(pos) < row.get(pos + 1)) {
        int smallIdx = pos;
        int biggerIdx = pos + 1;
        for (int i = pos + 1; i <= last; i++)
          if (row.get(i) > row.get(pos)) biggerIdx = i;
        swap(row, smallIdx, biggerIdx);
        reverse(row, pos + 1, last);
        return true;
      }
    }
    return false;
  }

  private void swap(List<Integer> row, int a, int b) {
    int t = row.get(a);
    row.set(a, row.get(b));
    row.set(b, t);
  }

  private void reverse(List<Integer> row, int s, int e) {
    while (s < e) {
      swap(row, s, e);
      s++;
      e--;
    }
  }

  /**
   * Same idea as Permutation 1 except we skip if duplicate of current element
   * is found in previous sequence
   */
  public List<List<Integer>> permuteUnique(int[] num) {
    if (num == null || num.length == 0) {
      return Collections.emptyList();
    }
    Arrays.sort(num);
    final List<List<Integer>> res = new ArrayList<>();
    dfs(num, 0, res);
    return res;
  }

  private void dfs(int[] num, int pos, List<List<Integer>> res) {
    if (pos == num.length) {
      final List<Integer> row = new ArrayList<>(num.length);
      for (int a : num) {
        row.add(a);
      }
      res.add(row);
      return;
    }
    for (int i = pos; i < num.length; i++) {
      // Skip if we have duplicates of current element before i
      boolean skip = false;
      for (int j = pos; j < i; j++) {
        if (num[j] == num[i]) {
          skip = true;
          break;
        }
      }
      if (skip) continue;
      swap(num, pos, i);
      dfs(num, pos + 1, res);
      swap(num, pos, i); // Reset
    }
  }

  private void swap(int[] num, int i, int j) {
    if (i == j) {
      return;
    }
    num[i] = num[j] - num[i];
    num[j] = num[j] - num[i];
    num[i] = num[j] + num[i];
  }
}
