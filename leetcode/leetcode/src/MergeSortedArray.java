/**
 * Given two sorted integer arrays A and B, merge B into A as one sorted array.
 * 
 * Note: 
 * 
 * You may assume that A has enough space to hold additional elements from B. 
 * The number of elements initialized in A and B are m and n respectively.
 */

public class MergeSortedArray {
	public void merge(int nums1[], int m, int nums2[], int n) {
		int i = m - 1, j = n - 1;
		int k = m + n - 1;
		while (i >= 0 && j >= 0) {
			nums1[k--] = nums1[i] >= nums2[j] ? nums1[i--] : nums2[j--];
		}
		while (j >= 0) {
			nums1[k--] = nums2[j--];
		}
	}
}