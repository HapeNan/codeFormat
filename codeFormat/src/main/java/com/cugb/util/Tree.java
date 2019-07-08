package com.cugb.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tree {
	public Tree(int id, String str, int parent, Boolean IsKeyWord, int depth) {
		this.id = id; // 树的结点编号
		this.str = str;
		this.parent = parent;
		this.IsKeyWord = IsKeyWord;
		this.depth = depth;
	}

	int parent; //
	int id;
	String str;
	Boolean IsKeyWord;
	Boolean IsBlock = false;
	int depth;

	void changeIsBlock() {
		IsBlock = true;
	}

	static String getSuojin(int flag) {
		String suojin = "";
		for (int i = 0; i < flag; i++) {
			suojin = suojin + "    ";
		}
		return suojin;
	}

	static String showString(List<Tree> trees) {
		int flag = 0;
		String str = "";
		String suojin = "";
		for (int i = 1; i < trees.size(); i++) {
			if (i != trees.size() - 1) {
				int depth = trees.get(i).depth - trees.get(i + 1).depth;
				if (depth < 0) {
					str = str + suojin + trees.get(i).str + " {\n";
					// System.out.println(trees.get(i).str+" { ");
					flag++;
					suojin = getSuojin(flag);
				} else {
					str = str + suojin + trees.get(i).str + "\n";
					// System.out.println(trees.get(i).str);
					for (int j = 0; j < depth; j++) {
						flag--;
						suojin = getSuojin(flag);
						str = str + suojin + "}\n";
					}
				}
			} else {
				str = str + suojin + trees.get(i).str + "\n";
				for (int j = flag; j > 0; j--) {
					flag--;
					suojin = getSuojin(flag);
					str = str + suojin + "}\n";
				}
			}
		}
		return str;
	}

	/*
	 * keywords数组是关键字数组，substring是原字符串
	 */
	public static List<Tree> CreateTree(String keywords[], String substring, Map<String, String> keyMap) {

		List<Tree> trees = new ArrayList<Tree>();
		Tree tree = new Tree(0, "", 0, false, 0);
		tree.changeIsBlock();
		trees.add(tree);
		Stack<Integer> sparent = new Stack<Integer>(); // 用于找父亲节点
		sparent.add(0);
		int id = 1;// 当前节点编号
		int depth = 1;// 当前深度
		String keyword = "[";
		for (int i = 0; i < keywords.length; i++) {
			if (i < keywords.length - 1)
				keyword = keyword + keywords[i] + ",";
			else
				keyword = keyword + keywords[i] + ",;,{,}]";
		}
		Matcher slashMatcher = Pattern.compile(keyword).matcher(substring);

		while (true) {
			int start = -1;
			boolean flag = false;
			while (slashMatcher.find()) {
				start = slashMatcher.start();// 匹配关键字的首字母下标
				flag = true;
				break;
			}
			// 如果当前匹配已经结束,则把所有剩下的字符归到最后的节点
			if (!flag) {
				break;
			}
			// 如果先匹配到分号，则直接生成子节点
			if (substring.charAt(start) == ';') {
				String substring1 = substring.substring(0, start + 1);
				String substring2 = substring.substring(start + 1, substring.length());
				Tree newTree = new Tree(id, substring1, sparent.peek(), false, depth);
				trees.add(newTree);
				id++;
				if (!trees.get(sparent.peek()).IsBlock) {
					depth--;
					sparent.pop();
				}
				substring = substring2;
				slashMatcher = Pattern.compile(keyword).matcher(substring2);
			}
			// 如果匹配到前括号，则将前一个节点设为语句块节点
			else if (substring.charAt(start) == '{') {
				String substring1 = substring.substring(start + 1, substring.length());
				trees.get(id - 1).changeIsBlock();
				substring = substring1;
				slashMatcher = Pattern.compile(keyword).matcher(substring1);
			}
			// 如果匹配到后括号则弹出一个深度
			else if (substring.charAt(start) == '}') {
				String substring1 = substring.substring(start + 1, substring.length());
				substring = substring1;
				slashMatcher = Pattern.compile(keyword).matcher(substring1);
				depth--;
				sparent.pop();
			}
			// 如果匹配到关键字则生成节点
			else {
				// 先判断关键字的首字母，再截取关键字的结束标志
				String startStr = "" + substring.charAt(start);
				String endStr = "";
				int end2 = 0;// 结束符下标
				if (substring.charAt(start) == 'e') {
					while (true) {
						if (substring.charAt(end2) == ' ' || substring.charAt(end2) == '{')
							break;
						else
							end2++;
					}
					end2--;
				} else if (substring.charAt(start) == 'c') {
					while (true) {
						if (substring.charAt(end2) == ' ' || substring.charAt(end2) == '(')
							break;
						else
							end2++;
					}
					String keyString = substring.substring(start, end2);
					System.out.println(keyString);
					if (keyString.equals("case")) {
						endStr = ":";
					} else if (keyString.equals("catch")) {
						endStr = ")";
					}
					char endChar = endStr.charAt(0);
					while (true) {
						if (substring.charAt(end2) == endChar)
							break;
						else
							end2++;
					}
				} else {
					endStr = keyMap.get(startStr);
					char endChar = endStr.charAt(0);
					while (true) {
						if (substring.charAt(end2) == endChar)
							break;
						else
							end2++;
					}

				}

				if (end2 == 0)
					continue;
				String substring1 = substring.substring(start, end2 + 1);
				String substring2 = substring.substring(end2 + 1, substring.length());

				Tree newTree = new Tree(id, substring1, sparent.peek(), true, depth);
				trees.add(newTree);
				sparent.push(id);
				id++;
				depth++;
				substring = substring2;
				slashMatcher = Pattern.compile(keyword).matcher(substring2);

			}
		}
		return trees;
	}

}
