package leetcode;

import java.util.ArrayList;
import java.util.Scanner;

public class solutions {

	public static void main(String args[]) {
		ArrayList<Stu> re = new ArrayList<Stu>();
		Scanner in = new Scanner(System.in);
		String s = new String();
		while (true) {
			System.out.println("input");
			s = in.nextLine();
			if (s.equals("exit")) {
				break;
			}
			String[] ss = s.split(" ");
			Stu input = new Stu();
			input.name = ss[0];
			input.number = Long.valueOf(ss[1]);
			input.score = Integer.valueOf(ss[2]);
			re.add(input);
		}
		DubbleSort(re);
	}

	public static void DubbleSort(ArrayList<Stu> re) {
		int n = re.size();
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n - 1 - j; i++) {
				// 把最大的交换到最后面去
				try {
					if (new Stu().compare(re.get(i), re.get(i + 1)) == 1) {
						Stu temp = re.remove(i);
						re.add(i + 1, temp);
					}
				} catch (Exception e) {

					// TODO: handle exception

				}

			}
		}
	}

}
