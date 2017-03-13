package com.zds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Substitution;
import org.apache.oro.text.regex.Util;

public class Solve {

	private static String readString(String filename) // 按行读文件
	{
		int len = 0;
		StringBuffer str = new StringBuffer("");
		File file = new File(filename);
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isr);
			String line = null;
			while ((line = in.readLine()) != null) {
				if (len != 0) // 处理换行符的问题
				{
					str.append("\n" + line);
				} else {
					str.append(line);
				}
				len++;
			}
			in.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str.toString();
	}

	public static void main(String args[]) {
		String content = readString("sootOutput/a.a.a.b.jimple"); // 文件名
		String str_line; // 读行
		StringBuffer str_out = new StringBuffer("");
		String sign; // 条件符号反置用
		String stemp; // 暂存弹出label用
		int flag;
		Pattern ifpattern = null;
		Pattern linepattern = null;
		Pattern labelpattern = null;
		Pattern gotopattern = null;
		Pattern retpattern = null;
		PatternCompiler compiler = new Perl5Compiler();
		PatternMatcher matcher = null;
		ArrayList<String> route_label = new ArrayList<String>(); // 记录目前为止途径的label
		Stack<Integer> if_stack = new Stack<Integer>(); // if处需要返回读处入栈
		Stack<String> current_stack = new Stack<String>(); // 栈顶表示当前label
		Stack<String> ret = new Stack<String>(); // 需打印的‘}'或“} else {”的个数
		// Stack<String> while_curr = new Stack<String>();
		// ArrayList<String> invaild_if = new ArrayList<String>();
		// //之前是以为内循环相反做的， 后期更正
		// ArrayList<String> label_if = new ArrayList<String>();
		ArrayList<String> udp_if = new ArrayList<String>(); // 同上，这两个合用将不会重复读同一句if
		Map<String, Integer> line_m = new HashMap<String, Integer>(); // 记录每个label的位置，跳转用
		try {
			matcher = new Perl5Matcher();
			linepattern = compiler.compile(".*\n");
			labelpattern = compiler.compile("(label\\d+):");
			ifpattern = compiler.compile(".*if (.*) goto (.*);.*");
			retpattern = compiler.compile(".*return.*");
			gotopattern = compiler.compile("  goto (.*);.*");
			MatchResult result;
			int index = 0;
			int if_i = 0;
			PatternMatcherInput input = new PatternMatcherInput(content);
			while (matcher.contains(input, labelpattern)) {
				result = matcher.getMatch();

				line_m.put(result.group(1).toString(), result.endOffset(0));
			}

			input.setCurrentOffset(0);
			while (matcher.contains(input, linepattern)) {

				result = matcher.getMatch();

				str_line = result.toString();

				index = result.endOffset(0);

				if (matcher.contains(str_line, labelpattern)) { // 处理到达label处
					result = matcher.getMatch();
					if_i = 0;
					if (!current_stack.empty()) // 每到达一个新label弹出上一个
						current_stack.pop();
					if (route_label.contains(result.group(1)) == false)
						route_label.add(result.group(1)); // 当前压栈
					current_stack.push(result.group(1));

				}

				else if (matcher.contains(str_line, ifpattern)) // 处理遇到if goto处
				{

					result = matcher.getMatch(); // 1是if内的语句，2是goto的label

					if (!udp_if.contains(str_line)) {
						if (if_i == 0 && matcher.contains(input, compiler.compile("goto " + current_stack.peek()))) // 判断是不是当前label的第一个if
						{ // 如果是本label下第一个if且下文会回到本label则说明为循环
							// 条件判断部分

							//

							// 正向条件
							// str_out.append("while ("+result.group(1)+") { \n");
							// //输出添加while
							// if_stack.push(index); //保存当前位置
							// input.setCurrentOffset(line_m.get(result.group(2)));
							// //跳转至指向位置
							// ret.push("\n }"); //对应结尾为'}'
							// current_stack.push(result.group(2));
							// if(route_label.contains(result.group(2))==false)
							// route_label.add(result.group(2));

							// 反向条件
							if_i = 1;
							input.setCurrentOffset(index); // 读下面
							str_out.append("while ( not " + result.group(1) + ") { \n");
							ret.push("\n}");
							stemp = current_stack.peek(); // 这里逻辑有问题，但运行反而正常，考虑是不是没影响。

							if (!route_label.contains(result.group(2))) // 如果goto的地方存在过可能是内循环的结束，防止反复
							{
								if_stack.push(line_m.get(result.group(2))); // 保存循环结束位置，结束时返回
								current_stack.push(result.group(2));
							}
							current_stack.push(stemp); // 将结束时跳转的label插入当前label前
							if (route_label.contains(result.group(2)) == false)
								route_label.add(result.group(2));

						} else if (route_label.contains(result.group(2))) { // 如果goto的目标出现过
							if (line_m.get(result.group(2)) < index) // 如果回到之前的位置则可能无限循环这句
								udp_if.add(str_line);

							// 仍然是按条件分循环
							// 先判断正反

							// 正向：

							// 反向：
							str_out.append("while ( not " + result.group(1) + ") { \n");
							if_stack.push(line_m.get(result.group(2))); // 保存循环结束位置，结束时返回
							ret.push("\n}");
							stemp = current_stack.peek();
							current_stack.push(result.group(2));
							current_stack.push(stemp); // 将结束时跳转的label插入当前label前
							if (route_label.contains(result.group(2)) == false)
								route_label.add(result.group(2));
						}

						else { // 真正的if。。。。。。
							str_out.append("if (" + result.group(1) + ") {\n");
							if_stack.push(index);
							current_stack.push(result.group(2));
							input.setCurrentOffset(line_m.get(result.group(2)));
							if (route_label.contains(result.group(1)) == false)
								route_label.add(result.group(1));
							ret.push("\n}");
							ret.push("}\n else {");
							if_i = 0;
						}

					} else {
						// current_stack.pop();
						continue;
					}
				}

				else if (matcher.contains(str_line, gotopattern)) // 处理goto
				{
					result = matcher.getMatch();
					if (route_label.contains(result.group(1)) == false) {
						route_label.add(result.group(1));
						current_stack.pop(); // 判断所goto的label是否存在
						input.setCurrentOffset(line_m.get(result.group(1)));
						if_i = 0; // 不存在则goto
						current_stack.push(result.group(1));

					}

					else { // 如果回到一个出现过的label则说明是某个模块（循环？）的结束

						current_stack.pop();
						if (!ret.empty())
							str_out.append(ret.pop());
						if (!current_stack.isEmpty())
							if (current_stack.peek().equals(result.group(1)) && !ret.empty())
								str_out.append(ret.pop());
						if_i = 1;
						if (!if_stack.isEmpty())
							input.setCurrentOffset(if_stack.pop());
						// current_stack.pop();
						// if_i = 1;
						// if(!if_stack.isEmpty())
						// input.setCurrentOffset(if_stack.pop());
						// //if (invaild_if.contains(result.group(1))) continue;
						// if(!while_curr.isEmpty())
						// {
						// str_out.append(ret.pop());
						// continue;
						// }
						// if(current_stack.isEmpty()){
						// while(!ret.isEmpty()) str_out.append(ret.pop());
						// break;
						// }
						// else if(current_stack.peek().equals(result.group(1)))
						// while(!ret.isEmpty()) str_out.append(ret.pop());
						//
						// // else str_out.append(" else { \n"); //存在则弹出栈顶的if
						// 添加‘}’
					}

				}

				else if (matcher.contains(str_line, retpattern)) // 处理return
				{
					current_stack.pop();
					if (!current_stack.empty() && !if_stack.empty()) // 如果栈顶不为空
					{
						// current_stack.pop();
						if_i = 1;
						input.setCurrentOffset(if_stack.pop()); // 弹出栈顶if添加 ‘}'
						str_out.append(ret.pop());
					} else {
						while (!ret.isEmpty())
							str_out.append(ret.pop());
						break;
					}
				}

				else
					str_out.append(str_line); // 陈述句转入
			}

			System.out.println(str_out);

		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}

	}

}

// 遇到if go 判断是否是相反的，新建函数判断是否相反。反的话按反处理。反即是循环goto不会回到原label
// new 反的不容易判断，改为判断正条件，遇见goto则返回遇到环则为正循环。原本判断第一个if对应后面有循环条件仍可用。
// 在此之前先写一个反条件的测试

// 对于goto与前面label的情况，比较goto label的位置和当前语句位置，如果goto的label在前面则消去这句话。
// 判断正反是记录当前if所在label，然后看是否能直接遇到goto此label，遇到环或则为反，能则为正

// 目前循环判断条件添加：goto的目的已经出现过则为循环，

// 反条件时不能直接消去，不然会丢失一部分循环后面的语句，还是得保存goto的地方

// if结束时会添加else，else结束时只添加反大括号，解决方案v1：试试用栈输出else和}

// 新问题：如果循环中有continue的话会出现类似goto之前的情况，不过会回到本label下的循环，应该可以用这个判断

// 两个新问题：内循环的第一个反条件的if会导致循环反复，试图添加标志while_f,但结尾仍有问题，待检测
// 单一if会导致代码重复，最大影响是if可能会变为while
// 考虑是否加段码判断是否为单个if 可以考虑看if所跳转的label是否不跳转也可以达到，是则为单一if

// 第一问题解决，如果第一个if里goto的地方去过则不去，弹栈顶

// 问题：有种循环是回到此if的上一紧贴的label，用前面探测循环条件无法探测，准备颠覆。
