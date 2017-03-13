package com.zds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

class message {
}

public class MySolve {

	private static ArrayList<String> route_label = new ArrayList<String>(); // 记录目前为止途径的label
	private static HashMap<String, String> label = new HashMap<>();
	private static ArrayList<String> while_label = new ArrayList<String>(); // 记录while的label

	public static void main(String args[]) {
		String end=init();
		dochange();

		// judge(5, "label04", "label04");
		for (String ss : route_label) {
			System.out.println(ss + ":");
			System.out.println(label.get(ss));
		}
		System.out.println(end);
	}

	private static String init() {
		String theEnd;
		// Pattern linepattern = null;
		Pattern labelpattern = null;
		Pattern ifpattern = null;
		Pattern gotopattern = null;
		PatternCompiler compiler = new Perl5Compiler();
		PatternMatcher matcher = null;
		route_label.clear();
		label.clear();
		boolean flag = true;
		MatchResult result;
		int index = 0;
		File file = new File("myapk/com.example.hello.MainActivity.jimple");
		matcher = new Perl5Matcher();

		try {
			// linepattern = compiler.compile(".*\n");
			labelpattern = compiler.compile("(label\\d+):");
			ifpattern = compiler.compile(".*if (.*) goto (.*);.*");
			gotopattern = compiler.compile("  goto (.*);.*");
		} catch (MalformedPatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PatternMatcherInput input = new PatternMatcherInput(readFileToString(file));
		if (matcher.contains(input, ifpattern)) {
			result = matcher.getMatch();
			index = result.beginOffset(0);
		}

		while (matcher.contains(input, labelpattern)) {

			result = matcher.getMatch();
			if (index >= result.beginOffset(0)) {
				index = result.beginOffset(0);
			}

			String s = input.substring(index, result.beginOffset(0));

			if (flag) {
				System.out.println(input.substring(0, index));
				label.put("label00", s);
				route_label.add("label00");
				flag = false;
			} else {
				String[] ss = s.split(":\\n");
				label.put(ss[0], ss[1]);
				route_label.add(ss[0]);
			}
			index = result.beginOffset(0);
		}
		String end = input.substring(index);
		theEnd=end.substring(end.indexOf("}"));
		end = end.replace("}", "");
		String[] ss = end.split(":\\n");
		label.put(ss[0], ss[1]);
		route_label.add(ss[0]);
		System.out.println("end");

		// for (String s : route_label) {
		// System.out.println(s);
		// System.out.println(label.get(s));
		// }
		// Iterator iter = label.entrySet().iterator();
		// while (iter.hasNext()) {
		// Map.Entry entry = (Map.Entry) iter.next();
		// Object key = entry.getKey();
		// Object val = entry.getValue();
		// System.out.println(key + "  " + val);
		// }
		return theEnd;
	}

	private static void dochange() {
		// Pattern gopattern = null;
		Pattern ifpattern = null;
		Pattern gotopattern = null;
		PatternCompiler compiler = new Perl5Compiler();
		PatternMatcher matcher = null;
		MatchResult result = null;
		matcher = new Perl5Matcher();
		try {
			// gopattern = compiler.compile("goto (.*);.*");
			ifpattern = compiler.compile(".*if (.*) goto (.*);.*");
			// retpattern = compiler.compile(".*return.*");
			gotopattern = compiler.compile("  goto (.*);.*");
		} catch (MalformedPatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < route_label.size(); i++) {
			String s = route_label.get(i);
			while_label.clear();
			if (judge(5, s, s)) {
				changeWhile(s);
			}
		}
		for (int i = 0; i < route_label.size(); i++) {

			String s = route_label.get(i);
			if (!judge(5, s, s)) {
				System.out.println(s + "--------------------");
				if (matcher.contains(label.get(s), ifpattern)) {
					result = matcher.getMatch();
					String body = label.get(result.group(2));
					String nextbody = null;
					if (matcher.contains(body, gotopattern)) {
						body = body.replace(matcher.getMatch().toString(), "");
						String next = matcher.getMatch().group(1);
						if (!label.get(next).equals("")) {
							nextbody = label.get(next);
							label.put(next, "");
						}

					}
					label.put(result.group(2), "");
					String others = label.get(s).substring(result.endOffset(0));
					if (others.contains("}")) {
						label.put(s, label.get(s).substring(0, result.beginOffset(0)) + "if(" + result.group(1)
							+ "){\n" + body + "}" + "else{" + others + nextbody + "}\n");
					} else {
						label.put(s, label.get(s).substring(0, result.beginOffset(0)) + "if(" + result.group(1)
							+ "){\n" + body + "}" + "else{" + others + "}\n" + nextbody);
					}

				}
			}
		}
		// for (String s : route_label) {
		// System.out.println(s);
		// System.out.println(label.get(s));
		// }
	}

	private static ArrayList<String> nextLab(String l) {
		ArrayList<String> labels = new ArrayList<String>();
		String body = label.get(l);
		Pattern gopattern = null;
		PatternCompiler compiler = new Perl5Compiler();
		PatternMatcher matcher = null;
		MatchResult result = null;
		matcher = new Perl5Matcher();
		try {
			gopattern = compiler.compile(".*goto (.*);.*");
		} catch (MalformedPatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("nextbody: "+body);
		if (body == null) {
			return labels;
		}
		PatternMatcherInput input = new PatternMatcherInput(body);
		while (matcher.contains(input, gopattern)) {
			result = matcher.getMatch();
			labels.add(result.group(1).toString());
		}

		if (labels.size() == 0) {
			labels.add(getMessage(l, "goto"));
		}
		return labels;
	}

	private static boolean judge(int i, String l, String next) {
		i--;
		if (!next.equals("null")) {
			ArrayList<String> ss = nextLab(next);
			// System.out.println("next:  " + next);
			while_label.add(next);
			for (String s : ss) {
				// System.out.println("s+l: "+s+"  "+l);
				if (s.equals(l)) {
					return true;
				}
			}

			if (i > 0) {
				for (String s : ss) {
					return judge(i, l, s);
				}
			}
		}
		return false;
	}

	private static void changeWhile(String s) {
		// Pattern gopattern = null;
		Pattern ifpattern = null;
		Pattern gotopattern = null;
		PatternCompiler compiler = new Perl5Compiler();
		PatternMatcher matcher = null;
		MatchResult result = null;
		matcher = new Perl5Matcher();
		try {
			// gopattern = compiler.compile("goto (.*);.*");
			ifpattern = compiler.compile(".*if (.*) goto (.*);.*");
			// retpattern = compiler.compile(".*return.*");
			gotopattern = compiler.compile("  goto (.*);.*");
		} catch (MalformedPatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (while_label.size() == 2) {
			if (matcher.contains(label.get(s), ifpattern)) {
				result = matcher.getMatch();
				String body = label.get(result.group(2));

				if (matcher.contains(body, gotopattern)) {
					System.out.println("contains");
					body = body.replace(matcher.getMatch().toString(), "continue");
				}

				label.put(result.group(2),
					label.get(s).substring(0, result.beginOffset(0)) + "while(" + result.group(1) + "){\n" + body + "}"
						+ label.get(s).substring(result.endOffset(0)));
				label.put(s, "");

			}
			// for (String ss : while_label) {
			// System.out.println(label.get(ss));
			// }
		} else if (while_label.size() > 2) {
			int changelabel = 0;

			StringBuffer buffer = new StringBuffer();
			if (matcher.contains(label.get(s), ifpattern)) {
				result = matcher.getMatch();

				for (int j = 1; j < while_label.size(); j++) {
					String body = label.get(while_label.get(j));
					if (matcher.contains(body, ifpattern)) {
						changelabel = j;
						if (matcher.getMatch().group(2).equals(s)) {
							String judgement = result.group(1).toString();
							body = "if !" + judgement + " goto null;" + body.substring(matcher.getMatch().endOffset(0));
						}
						buffer.append(body);

						if (j < while_label.size() - 1) {
							for (int m = j + 1; m < while_label.size(); m++) {
								String b = label.get(while_label.get(m));
								if (matcher.contains(b, gotopattern)) {
									if (matcher.getMatch().group(1).equals(s)) {
										b = b.replace(matcher.getMatch().toString(), "continue");
										label.put(while_label.get(m), b);
									}
								}
							}
						}
						break;
					} else if (matcher.contains(body, gotopattern)) {
						changelabel = j;
						if (matcher.getMatch().group(1).equals(s)) {
							body = body.replace(matcher.getMatch().toString(), "continue");
						} else {
							body = body.replace(matcher.getMatch().toString(), "");
						}
						buffer.append(body);
					} else {
						changelabel = j;
						buffer.append(body);
					}
				}
				// System.out.println("while(" + result.group(1) +
				// "){\n" + buffer + "}"
				// + label.get(s).substring(result.endOffset(0)));

				label.put(while_label.get(changelabel), label.get(s).substring(0, result.beginOffset(0)) + "while("
					+ result.group(1) + "){\n" + buffer + "}" + label.get(s).substring(result.endOffset(0)));
				for (int k = 0; k < changelabel; k++) {
					label.put(while_label.get(k), "   goto " + while_label.get(k + 1) + ";");
				}

				// for (String ss : while_label) {
				// System.out.println(ss+": ");
				// System.out.println(label.get(ss));
				// }
			}
			label.put(s, "");
		} else if (while_label.size() == 1) {
			String body = label.get(s);
			if (matcher.contains(body, ifpattern)) {
				result = matcher.getMatch();
				if (result.group(2).equals("null")) {
					body = body.replace(result.toString(), "while(" + result.group(1) + "){\n");
				}
			}
			if (matcher.contains(body, gotopattern)) {
				result = matcher.getMatch();
				body = body.replace(result.toString(), "}");
			}
			label.put(s, body);
			// System.out.println(body);
		}

	}

	private static String getMessage(String l, String s) {
		ArrayList<String> if_label = new ArrayList<String>(); // 记录if的label
		ArrayList<String> while_label = new ArrayList<String>(); // 记录while的label

		String body = label.get(l);
		Pattern ifpattern = null;
		Pattern gotopattern = null;
		Pattern retpattern = null;
		Pattern labelpattern = null;
		Pattern gopattern = null;

		PatternCompiler compiler = new Perl5Compiler();
		PatternMatcher matcher = null;
		matcher = new Perl5Matcher();
		String judgement = null, nextLabel = null, gotoLabel;
		int index;
		MatchResult result = null;
		try {
			labelpattern = compiler.compile("(label)(\\d+):");
			ifpattern = compiler.compile(".*if (.*) goto (.*);.*");
			retpattern = compiler.compile(".*return.*");
			gotopattern = compiler.compile("  goto (.*);.*");
			gopattern = compiler.compile("goto (.*);.*");
		} catch (MalformedPatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PatternMatcherInput input = new PatternMatcherInput(body);
		if (matcher.contains(body, ifpattern)) {
			result = matcher.getMatch();
			judgement = result.group(1).toString();
			nextLabel = result.group(2).toString();
		}
		if (matcher.contains(body, gotopattern)) {
			result = matcher.getMatch();
			gotoLabel = result.group(1).toString();
		} else {
			String ss = l.substring(5);
			int i = Integer.valueOf(ss) + 1;
			ss = String.format("%1$02d", i);
			gotoLabel = "label" + ss;
		}

		// System.out.println("-------------------");
		// System.out.println(input.postMatch());
		// System.out.println("-------------------");

		if (s == "goto") {
			return gotoLabel;
		}
		if (s == "judge") {
			return judgement;
		}
		if (s == "next") {
			return nextLabel;
		}
		// System.out.println("judgement:  " + judgement);
		// System.out.println("nextLabel:  " + nextLabel);
		// System.out.println("gotoLabel:  " + gotoLabel);
		// System.out.println();
		return null;
	}

	private static String readFileToString(File file) // 按行读文件
	{
		StringBuffer out = new StringBuffer("");
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isr);
			String line = null;
			while ((line = in.readLine()) != null) {
				out.append(line + "\n");
			}
			in.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out.toString();
	}

}
