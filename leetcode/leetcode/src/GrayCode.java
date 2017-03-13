import java.util.ArrayList;

/**
 * The gray code is a binary numeral system where two successive values differ
 * in only one bit.
 * 
 * Given a non-negative integer n representing the total number of bits in the
 * code, print the sequence of gray code. A gray code sequence must begin with
 * 0.
 * 
 * For example, given n = 2, return [0,1,3,2]. Its gray code sequence is:
 * 
 * 00 - 0 01 - 1 11 - 3 10 - 2
 * 
 * Note:
 * 
 * For a given n, a gray code sequence is not uniquely defined.
 * 
 * For example, [0,2,3,1] is also a valid gray code sequence according to the
 * above definition.
 * 
 * For now, the judge is able to judge based on one instance of gray code
 * sequence. Sorry about that.
 */

/**
 * 第一步：产生 0, 1 两个字符串。 第二步：在第一步的基础上，每一个字符串都加上0和1，但是每次只能加一个，所以得做两次。这样就变成了
 * 00,01,11,10 （注意对称）。 第三步：在第二步的基础上，再给每个字符串都加上0和1，同样，每次只能加一个，这样就变成了
 * 000,001,011,010,110,111,101,100。
 * 
 * @author Administrator
 * 
 */
public class GrayCode {
	public ArrayList<Integer> grayCode(int n) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		if (n == 0) {
			result.add(0);
			return result;
		}
		result.add(0);
		result.add(1);
		for (int i = 1; i < n; i++) {
			ArrayList<Integer> tmp = new ArrayList<Integer>(result);
			Integer a = 1 << i;
			for (int k = result.size() - 1; k >= 0; k--) {
				tmp.add(result.get(k) + a);
			}
			result = tmp;
		}
		return result;
	}
}