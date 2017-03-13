package leetcode;
//路径匹配，用正则表达式
import java.util.ArrayList;
import java.util.List;

//!!!!!!   syso+Alt+/

public class SimplifyPath {

	public String simplifyPath(String path) {

		path = path.replaceAll("//+", "/");
		System.out.println(path);
		while (path.contains("/./")) {
			path = path.replace("/./", "/");
			System.out.println(path);
		}
		while (path.contains("/../")) {
			path = path.replaceAll("/\\.?\\w+/\\.\\./", "/");
			if (path.length() > 3 && path.substring(0, 3).equals("/..")) {
				path = path.substring(3, path.length());
			}
			System.out.println(path);
		}
		if (path.matches("/\\.{1,2}") || path.matches("\\.+")) {
			path = "/";
		}
		if (path.matches(".*/\\.?\\w+/\\.\\.")) {
			path = path.replaceAll("/\\.?\\w+/\\.\\.", "/");
		}
		if (path.charAt(path.length() - 1) == '/' && path.length() > 1) {
			path = path.substring(0, path.length() - 1);
		}
		if (path.length() > 2
				&& path.substring(path.length() - 2, path.length())
						.equals("/.")) {
			path = path.substring(0, path.length() - 2);
		}
		return path;
	}

	public static void main(String args[]) {
		String path = "/../../asd";
		System.out.println(path);
	}
}
