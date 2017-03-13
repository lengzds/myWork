package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class subsetsWithDup {
	public static List<List<Integer>> subsetsWithDup(int[] nums) {
		HashSet<String> ar = new HashSet<String>();
		ArrayList<List<Integer>> ret = new ArrayList<List<Integer>>();
		Arrays.sort(nums);
		for (int i : nums) {
			ar.add("");
			Object[] ss = ar.toArray();
			for (Object s : ss) {
				ar.add((String) s + i);
			}
		}
		for (Object s : (Object[]) ar.toArray()) {
			System.out.println(s);
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			char[] cc = s.toString().toCharArray();
			for (int i = 0; i < cc.length; i++) {
				char c = cc[i];
				if (c == '-') {
					i++;
					c = cc[i];
					tmp.add(0 - (c - '0'));
				} else {
					tmp.add(c - '0');
				}
			}
			ret.add(tmp);
		}
		return ret;

	}

	public static void main(String args[]) {
		int[] i = { 1, 2, 2 };
		subsetsWithDup(i);
	}
}
