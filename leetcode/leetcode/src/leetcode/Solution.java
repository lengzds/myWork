package leetcode;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import edu.gmu.trimdroid.gui.TrimDroidGUI;

//!!!!!!   syso+Alt+/

public class Solution {

	public ListNode deleteDuplicates(ListNode head) {

		if (head == null)
			return head;
		ListNode start = new ListNode(0);
		start.next = head;
		ListNode slow = start;
		ListNode fast = head;
		while (fast.next != null) {
			if (slow.next.val != fast.next.val) {
				if (slow.next.next == fast.next) {
					slow = slow.next;
				} else {
					slow.next = fast.next;
				}
			}
			fast = fast.next;
		}
		if (slow.next.next != fast.next) {
			slow.next = fast.next;
		}
		return start.next;
	}

	public static void main(String args[]) {
		String path = "/../../asd";
		System.out.println(path);
	}

	public class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}
}
