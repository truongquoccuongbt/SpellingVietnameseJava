package Tokenizer;

/**
 * TON DUC THANG UNIVERSITY
 * 
 * @author PHAN VAN HUNG
 * @Instructors Assoc. Prof. LE ANH CUONG
 */
public class StringControl {
	public static boolean IsPunctuation(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isLetterOrDigit(str.charAt(i)))
				return true;
		}
		return false;
	}

	public static boolean IsBrace(String str) {
		if (str.equals("\"") || str.equals("�") || str.equals("'") || str.equals(")") || str.equals("}")
				|| str.equals("]")) {
			return true;
		}
		return false;
	}

	public static boolean IsUpperTheFirstChar(String str) {
		for (String token : str.split(" ")) {
			if (!Character.isLetterOrDigit(token.charAt(0)))
				return false;
			if (Character.isLowerCase(token.charAt(0)))
				return false;
		}
		return true;
	}
}
