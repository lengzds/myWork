import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a collection of integers that might contain duplicates, S, return all
 * possible subsets.
 * 
 * Note: 
 * 
 * Elements in a subset must be in non-descending order. The solution set
 * must not contain duplicate subsets. For example, If S = [1,2,2], a solution
 * is:
 * 
 * [ [2], [1], [1,2,2], [2,2], [1,2], [] ]
 */

public class SubsetsII {
	public List<List<Integer>> subsetsWithDup(int[] nums) {
		ArrayList<List<Integer>> ret = new ArrayList<List<Integer>>();
		ArrayList<List<Integer>> lastLevel = null;
		ret.add(new ArrayList<Integer>());
		Arrays.sort(nums);
		for (int i = 0; i < nums.length; i++) {
			ArrayList<List<Integer>> tmp = new ArrayList<List<Integer>>();
			ArrayList<List<Integer>> prev = i == 0 || nums[i] != nums[i - 1] ? ret : lastLevel;
			for (List<Integer> s : prev) {
				ArrayList<Integer> newSet = new ArrayList<Integer>(s);
				newSet.add(nums[i]);
				tmp.add(newSet);
			}
			ret.addAll(tmp);
			lastLevel = tmp;
		}
		return ret;
	}
}