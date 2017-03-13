package leetcode;

import java.util.Comparator;

public class Stu implements Comparator {
	String name;
	long number;
	int score;

	public String tostring() {
		return name + " " + number + " " + score;
	}

	public int compare(Object o1, Object o2) {
		Stu s1 = (Stu) o1;
		Stu s2 = (Stu) o2;
		if (s1.score > s2.score)
			return 1;
		return 0;
	}
}
