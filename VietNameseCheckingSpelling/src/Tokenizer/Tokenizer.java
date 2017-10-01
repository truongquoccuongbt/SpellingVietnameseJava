package Tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dictionary.*;
/**
 * TON DUC THANG UNIVERSITY
 * @author PHAN VAN HUNG
 * @Instructors Assoc. Prof. LE ANH CUONG 
 */
public class Tokenizer {
	public static List<String> RunTokenize(String str) {
		List<String> tokensList = new ArrayList<String>();
		String[] tempTokens = str.split(StringConst.SPACE);
		String trimed = str.trim();

		if (trimed.equals(StringConst.EMPTY) || trimed.equals(StringConst.SPACE) || str == null
				|| tempTokens.length == 0) {
			return tokensList;
		}

		for (String token : tempTokens) {

			if (token.equals(StringConst.SPACE) || token.equals(StringConst.EMPTY) || token == null) {
				continue;
			}

			if (token.length() == 1 || !StringControl.IsPunctuation(token)) {
				tokensList.add(token);
				continue;
			}

			if (token.endsWith(",")) {
				tokensList.addAll(RunTokenize(token.substring(0, token.length() - 1)));
				tokensList.add(",");
				continue;
			}

			if (Dictionary.GetAbbreviation().contains(token)) {
				tokensList.add(token);
				continue;
			}

			if (token.endsWith(".") && Character.isAlphabetic(token.charAt(token.length() - 2))) {
				if (token.length() == 2 && Character.isUpperCase(token.charAt(token.length() - 2))) {
					tokensList.add(token);
					continue;
				}
				tokensList.addAll(RunTokenize(token.substring(0, token.length() - 1)));
				tokensList.add(".");
				continue;
			}

			if (Dictionary.GetException().contains(token)) {
				tokensList.add(token);
				continue;
			}

			boolean check = false;
			for (String e : Dictionary.GetAbbreviation()) {
				int start = token.indexOf(e);
				if (start < 0)
					continue;
				check = true;
				tokensList = recursive(tokensList, token, start, start + e.length());
				break;
			}
			if (check)
				continue;
			check = false;
			for (String e : Dictionary.GetException()) {
				int start = token.indexOf(e);
				if (start < 0)
					continue;
				check = true;
				tokensList = recursive(tokensList, token, start, start + e.length());
				break;
			}
			if (check)
				continue;
			check = false;
			List<String> regexes = RegexRules.GetRegexList();
			for (String iRegex : regexes) {
				Pattern pattern = Pattern.compile(iRegex);
				Matcher matcher = pattern.matcher(token);
				if (matcher.find()) {
					//System.out.println(RegexRules.GetNameRules(iRegex));
					if (matcher.start() > 0 || matcher.end() < token.length()) {	
						tokensList = recursive(tokensList, token, matcher.start(), matcher.end());
						check = true;
					}
					break;
				}
			}
			if (check)
				continue;
			else {
				tokensList.add(token);
			}

		}
		return tokensList;
	}

	private static List<String> recursive(List<String> tokens, String token, int beginMatch, int endMatch) {
		if (beginMatch > 0) {
			tokens.addAll(RunTokenize(token.substring(0, beginMatch)));
		}
		tokens.addAll(RunTokenize(token.substring(beginMatch, endMatch)));
		if (endMatch < token.length())
			tokens.addAll(RunTokenize(token.substring(endMatch, token.length())));

		return tokens;
	}
	
	public boolean IsExistRegexRules(String token) {
		List<String> regexs = RegexRules.GetRegexList();
		for (String regex : regexs) {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(token);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}
}
