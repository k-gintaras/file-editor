
public class StringHelper {

	public static String getSpacedToCamelback(String str) {
		String[] arr = str.split(" ");
		String res = "";
		for (int i = 0; i < arr.length; i++) {
			String cur = arr[i];
			cur = getToFirstUpperCase(cur);
			if (!isInt(cur)) {
				res += cur;
			}
		}
		return res;
	}

	public static String getCapitalizedWords(String str, String splitter) {
		str=str.toLowerCase().trim();
		String[] arr = str.split(splitter);
		if (arr.length > 0) {
			String res = "";
			for (int i = 0; i < arr.length; i++) {
				String cur = arr[i];
				if (cur.length() > 0) {
					cur = getToFirstUpperCase(cur);
				}
//				if (!isInt(cur)) {
					res += cur;
					if (i < arr.length - 1) {
						res += splitter;
					}
//				}
			}
			return res;
		}
		return str;
	}

	public static String getToFirstUpperCase(String cur) {
		return (cur.charAt(0) + "").toUpperCase() + cur.substring(1, cur.length());
	}

	public static boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String getFirstNumber(String str) {
		String[] arr = str.split(" ");
		for (int i = 0; i < arr.length; i++) {
			String cur = arr[i];
			if (isInt(cur)) {
				return cur;
			}
		}
		return "" + 0;
	}
	
	public static String getWithoutNumbersInFront(String str) {
		if(str.length()>0) {
			boolean start=true;
			String front="";
			String end="";
			for (int i = 0; i < str.length(); i++) {
				char c= str.charAt(i);
				if(Character.isLetter(c)) {
					start=false;
				}
				if(start) {
					front+=c;
				}else {
					end+=c;
				}
			}
			if(str.equals(front)) {
				return str;
			}
			str=end+" - "+front;
			str=str.replaceAll("\\s*-\\s*$", "");
			str=str.replaceAll("\\s*\\.\\s*$", "");
			str=str.trim();
			return str;
		}
		return str;
	}
	
	public static String getWithoutBracketsInFront(String str) {
		if(str.length()>0) {
			String front="";
			String end="";
			if(str.charAt(0)=='[') {
				front=str.substring(str.indexOf("["),str.indexOf("]")+1);
//				System.out.println(front);
				end=str.substring(str.indexOf("]")+1,str.length());
				str= end+" - "+front;
			}
			if(str.charAt(0)=='(') {
				
				front=str.substring(str.indexOf("("),str.indexOf(")")+1);
//				System.out.println(front);

				end=str.substring(str.indexOf(")")+1,str.length());
				str= end+" - "+front;
			}
		}
		str=str.replaceAll("\\s*-\\s*$", "");
		str=str.replaceAll("\\s*\\.\\s*$", "");
		str=str.trim();

		return str;
	}

	public static String getComplexWordsCapitalized(String str) {
		str = str.toLowerCase();
//		str=str.replaceAll("/__+/g", "_");
		str=str.replaceAll("_", "-");
		str=str.replaceAll("--", "-");
		str=str.replaceAll("-", " - ");
		str=str.replaceAll("- -", " - ");
		str=str.replaceAll("-  -", " - ");
		str=str.replaceAll("/\\s+/g", " ");
		str=str.replaceAll("\\[", "[ ");
		str=str.replaceAll("\\(", "( ");
		if(str.length()>2) {
			str = getCapitalizedWords(str, " ");
		}
		str=str.replaceAll("/\\s+/g", " ");
		str=str.trim().replaceAll(" +", " ");

		str=str.replaceAll("\\[ ", "[");//replace double spaces
		str=str.replaceAll("\\( ", "(");//replace double spaces
		return str;
	}
}
