package Algorithm;

import java.awt.List;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Candidate {
	public double LIM_SIMILARITY() {
		return 0.8;
	}
	
	public double LIM_LANGUAGEMODEL() {
		return 0.3;
	}
	
	public double LIM_COMPOUNDWORD() {
		return 0.7;
	}
	
	public double LIM_SCORE() {
		return 0.6;
	}
	
	public double MAX_SCORE() {
		return 1;
	}
	
	public double MIN_SCORE() {
		return 0;
	}
	
	private static Candidate instance = new Candidate();
	public static Candidate GetInstance() {
		return instance;
	}
	
	/**
	 * Sinh candidate cho token
	 */
	public HashSet<String> CreateCandidate(Context context, boolean isRightError) {
		if (isRightError) {
			return RightWordCandidate.GetInstance().CreateCandidate(context);
		}
		return WrongWordCandidate.GetInstance().CreateCandidate(context);
	}
	
	public HashSet<String> CreateCandidate(Context context) {
		if (VNDictionary.GetInstance().IsSyllableVN(context.getToken())) {
			return RightWordCandidate.GetInstance().CreateCandidate(context);
		}
		return WrongWordCandidate.GetInstance().CreateCandidate(context);
	}
	
	/**
	 * tạo candidate dựa trên từ ghép.
	 */
	public HashSet<String> CreateCandByCompoundWord(Context context) {
		HashSet<String> hset = new HashSet<>();
		// tìm x
		UnionWith(hset, VNDictionary.GetInstance().FindCompoundVNWord_Xxx(context));
		UnionWith(hset, VNDictionary.GetInstance().FindCompoundVnWord_xXx(context));
		UnionWith(hset, VNDictionary.GetInstance().FindCompoundVnWord_xxX(context));
		if (hset.size() > 0) {
			return hset;
		}
		UnionWith(hset, VNDictionary.GetInstance().FindCompoundVnWord_Xx(context));
		UnionWith(hset, VNDictionary.GetInstance().FindCompoundVnWord_xX(context));
		return hset;
	}
	
	private HashSet<String> UnionWith(HashSet<String> a, HashSet<String> b) {
		HashSet<String> c = new HashSet<>(a);
		String tmp;
		for (Iterator<String> it = b.iterator(); it.hasNext();) {
			tmp = it.next();
			if (!a.contains(tmp)) {
				c.add(tmp);
			}
		}
		return c;
	}
	
	/**
	 * tạo candicate dựa trên ngữ cảnh
	 */
	public HashSet<String> CreateCandidateByNgram_NoUseLamdaExp(Context context) {
		HashSet<String> lstCandidate = new HashSet<>();
		for (String key : Ngram.GetInstance().get_biAmount().keySet()) {
			if (key.contains(Ngram.GetInstance().START_STRING()) || key.contains(Ngram.GetInstance().END_STRING()))
				continue;
			
			if (IsLikely(context.getToken(), key) && (key.contains(context.getPre()) || key.contains(context.getNext()))) {
				String[] word = key.split(" ");
				if (!word[1].equals(context.getToken().toLowerCase()) && word[0].equals(context.getPre()) && word[1].length() > 0) {
					lstCandidate.add(word[1]);
				}
				// check
				if (key.equals("dịch chợ")) {
					System.out.println(word[0].equals(context.getToken().toLowerCase()));
					System.out.println(word[1].equals(context.getNext().toLowerCase()));
				}
				/////
				
				else if (!word[0].equals(context.getToken().toLowerCase()) && word[1].equals(context.getNext()) && word[0].length() > 0) {
					lstCandidate.add(word[0]);
				}
			}
		}
		return lstCandidate;
	}
	
	public boolean IsLikely(String syll, String cand) {
		int lenght = syll.length();
		boolean isLongWord = lenght > 3 ? true : false;
		int count = 0;
		
		//khoong vĂ  khĂ´ng
		for (int i = 0; i < lenght; i++) {
			for (int j = 0; j < cand.length(); j++) {
				if (syll.charAt(i) == cand.charAt(j)) {
					count++;
					if ((isLongWord && count >= lenght - 2) || (!isLongWord && count >= lenght - 1)) {
						return true;
					}
					break;
				}
			}
		}
		return false;
	}
	
	/**
	 * sắp xếp candicate dựa trên số điểm, canđiate có điểm cao nhất sẽ ở vị trí đầu tiên.
	 */
	public HashMap<String, Double> SordDict(HashMap<String, Double> dict) {
		HashMap<String, Double> tmp = new HashMap<>();
		ArrayList<Double> arrVal = new ArrayList<>(dict.values());
		ArrayList<String> arrKey = new ArrayList<>(dict.keySet());
		Collections.sort(arrVal);
		Iterator<Double> value = arrVal.iterator();
		Iterator<String> keyIt;
		String key;
		Double val, comp1, comp2;
		while (value.hasNext()) {
			val = value.next();
			keyIt = arrKey.iterator();
			while (keyIt.hasNext()) {
				key = keyIt.next();
				comp1 = dict.get(key);
				comp2 = val;
				
				if (comp1 == comp2) {
					keyIt.remove();
					tmp.put(key, val);
					break;
				}
			}
		}
		return tmp;
	}
	
	public double CalScore_NgramForFindError(Context context) {
		double calBiGram_PreToken = Ngram.GetInstance().CalBigram(context.getPre(), context.getToken());
		double calBiGram_TokenNext = Ngram.GetInstance().CalBigram(context.getToken(), context.getNext());
		
		double ret = (0.5 * calBiGram_PreToken + 0.5 * calBiGram_TokenNext) * 155;
		return ret;
	}
	
	/**
	 * tính điểm cho candidate dựa vào ngữ cảnh
	 */
	public double CalScore_Ngram(Context context, String candidate) {
		double calBiGram_PreCand = Ngram.GetInstance().CalBigram(context.getPre(), candidate);
		double calBiGram_CandNext = Ngram.GetInstance().CalBigram(candidate, context.getNext());
		double calBiGram_PreToken = Ngram.GetInstance().CalBigram(context.getPre(), context.getToken());
		double calBiGram_TokenNext = Ngram.GetInstance().CalBigram(context.getToken(), context.getNext());
		
		if (calBiGram_PreCand == 0 && calBiGram_CandNext == 0) {
			return 0;
		}
		if (calBiGram_PreToken == 0 && calBiGram_TokenNext == 0) {
			return 1;
		}
		double lamda1 = 0, lamda2 = 0;
		if (calBiGram_PreCand < 1E-5 && calBiGram_CandNext < 1E-5) {
			return 0;
		}
		lamda1 = calBiGram_PreCand / calBiGram_PreToken;
		lamda2 = calBiGram_CandNext / calBiGram_TokenNext;
		if (lamda1 > 100 || lamda2 > 100) {
			return 1;
		}
		if (lamda1 > 80 || lamda2 > 80) {
			return 0.8;
		}
		if (lamda1 > 50 || lamda2 > 50) {
			return 0.5;
		}
		return 0;
	}
	
	/**
	 * tính điểm cho candidate dựa vào từ ghép
	 */
	public double CalScore_CompoundWord(Context context, String candidate) {
		String _3SyllComWord1 = String.format("{0} {1} {2}", context.getPrePre(), context.getPre(), candidate).trim().toLowerCase();
		String _3SyllComWord2 = String.format("{0} {1} {2}", context.getPre(), candidate, context.getNext()).trim().toLowerCase();
		String _3SyllComWord3 = String.format("{0} {1} {2}", candidate, context.getNext(), context.getNextNext()).trim().toLowerCase();
		String _2SyllComWord1 = String.format("{0} {1}", context.getPre(), candidate);
		String _2SyllComWord2 = String.format("{0} {1}", candidate, context.getNext()).trim().toLowerCase();
		
		if (context.getPrePre().length() > 0 && context.getPre().length() > 0 && VNDictionary.GetInstance().CompoundDict.contains(_3SyllComWord1)) {
			return MAX_SCORE();
		}
		else if (context.getPre().length() > 0 && context.getNext().length() > 0 && VNDictionary.GetInstance().CompoundDict.contains(_3SyllComWord2)) {
			return MAX_SCORE();
		}
		else if (context.getNext().length() > 0 && context.getNextNext().length() > 0 && VNDictionary.GetInstance().CompoundDict.contains(_3SyllComWord3)) {
			return MAX_SCORE();
		}
		else if (context.getPre().length() > 0 && VNDictionary.GetInstance().CompoundDict.contains(_2SyllComWord1)) {
			return 0.7;
		}
		else if (context.getNextNext().length() > 0 && VNDictionary.GetInstance().CompoundDict.contains(_2SyllComWord2)) {
			return 0.7;
		}
		return MIN_SCORE();
	}
	
	/**
	 * tính điểm cho candidate dựa vào độ tương tự với token
	 * điểm càng cao thì candidate càng tốt.
	 */
	public double CalScore_Similarity(String token, String candidate) {
		double score = MAX_SCORE();
		token = token.toLowerCase();
		candidate = candidate.toLowerCase();
		//double simScore = CalStringSim(token, candidate);
		double diffScore = CalScore_StringDiff(token, candidate);
		score = diffScore;
		if (score > MAX_SCORE()) {
			return MAX_SCORE();
		}
		if (score < MIN_SCORE()) {
			return MIN_SCORE();
		}
		return score;
	}
	
	/**
	 * tính điểm cho những ký tự khác nhau trong token và candidate
	 * điểm càng cao candidate càng giống với token
	 * 
	 */
	public double CalScore_StringDiff(String token, String candidate) {
		double diffScore;
		// tách dấu ---> tachs dâus
		String[] extTokenArr = ExtractSignVnNotFully(token);
		String[] extCandidateArr = ExtractSignVnNotFully(candidate);
		
		String extToken = extTokenArr[0];
		String signToken = extTokenArr[1];
		
		String extCandidate = extCandidateArr[0];
		String signCandidate = extCandidateArr[1];
		
		int denominator = CalDenominatorForStringDiff(extToken, signToken, extCandidate, signCandidate);
		double numerator = CalNumeratorForStringDiff(extToken, extCandidate) + CalNumeratorForStringDiff(extCandidate, extToken)
							+ CalNumeratorForStringDiff_Sign(signToken, signCandidate) + CalNumeratorForStringDiff_Sign(signCandidate, signToken);
		// tử số khác nhau càng nhiều, điểm càng cao
		diffScore = 1 - numerator / denominator;
		if (diffScore < MIN_SCORE()) {
			return MIN_SCORE();
		}
		return diffScore;
	}
	
	private double CalNumeratorForStringDiff_Sign(String signToken, String signCandidate) {
		if (signToken.length() == 0 && signCandidate.length() == 0) {
			return 0;
		}
		if (signToken.equals(signCandidate)) {
			return 0;
		}
		if (signToken.equals("s") && signCandidate.equals("x") ||
			signToken.equals("x") && signCandidate.equals("s") ||
			signToken.equals("j") && signCandidate.equals("x") ||
			signToken.equals("x") && signCandidate.equals("j")) {
			return 0.1;
		}
		if (signToken.length() > 0 && signCandidate.length() > 0) {
			if (IsKeyBoardMistake(signToken.charAt(0), signCandidate.charAt(0))) {
				return 0.2;
			}
		}
		return 0.5;
	}
	
	private double CalNumeratorForStringDiff(String extX, String extY) {
		double numerator = 0;
		int lengthExtX = extX.length();
		int lengthExtY = extY.length();
		int j = 0;
		boolean isRegion = false;
		for (int i = 0; i < lengthExtX; i++, j++) {
			if (j < lengthExtY) {
				if (i + 1 < lengthExtX && j < lengthExtY) {
					if (isTelexSign(extX.charAt(i), extX.charAt(i + 1), extY.charAt(j))) {
						numerator += 0.1;
						continue;
					}
				}
				else if (i + 1 < lengthExtX && j + 1 < lengthExtY) {
					if (IsRegionMistake(extX.charAt(i), extX.charAt(i + 1), extY.charAt(j), extY.charAt(j + 1))) {
						numerator += 0.1;
						i++;
						j++;
						isRegion = true;
						continue;
					}
				}
				
				if (extX.charAt(i) == extY.charAt(j)) {
					continue;
				}
				if (IsRegionMistake(extX.charAt(i), extY.charAt(j))) {
					numerator += 0.1;
					continue;
				}
				int index;
				if (j > 0)
					index = extY.indexOf(extX.charAt(i), j - 1);
				else
					index = extY.indexOf(extX.charAt(i), j);
				if (index != -1) {
					if (Math.abs(i - index) == 1) {
						numerator += 0.1;
						continue;
					}
					else if (Math.abs(i - index) == 2) {
						numerator += 0.2;
						continue;
					}
				}
				else if (IsVowelVNMistake(extX.charAt(i), extY.charAt(j))) {
					numerator += 0.3;
				}
				else if (IsKeyBoardMistake(extX.charAt(i), extY.charAt(j))) {
					j--;
					numerator += 0.5;
				}
				else {
					j--;
					numerator += 1;
				}
			}
			else {
				numerator += 1;
			}
		}
		return numerator;
	}
	
	private boolean IsKeyBoardMistake(char c1, char c2) {
		int iC1 = -1, iC2 = -1, jC1 = -1, jC2 = -1;
		boolean isFoundC1 = false, isFoundC2 = false;
		for (int i = 0; i < StringConstant.MAX_KEYBOARD_ROW; i++) {
			for (int j = 0; j < StringConstant.MAX_KEYBOARD_COL; j++) {
				if (!isFoundC1) {
					if (StringConstant.GetInstance().keyBoardMatrix_LowerCase[i][j] == c1) {
						isFoundC1 = true;
						iC1 = i;
						iC2 = j;
					}
					if (!isFoundC2) {
						if (StringConstant.GetInstance().keyBoardMatrix_LowerCase[i][j] == c2) {
							isFoundC2 = true;
							jC2 = i;
							jC2 = j;
						}
					}
					if (isFoundC1 && isFoundC2) {
						if (Math.abs(iC1 - iC2) == 0 || Math.abs(jC1 - jC2) == 0) {
							return true;
						}
						else return false;
					}
				}
			}
		}
		return false;
	}
	
	private boolean IsVowelVNMistake(char c1, char c2) {
		int iC1 = -1, iC2 = -1;
		boolean isFoundC1 = false, isFoundC2 = false;
		for (int i = 0; i < StringConstant.MAXGROUP_VNCHARMATRIX; i++) {
			for (int j = 0; j < StringConstant.MAXCASE_VNCHARMATRIX; j++) {
				if (!isFoundC1) {
					if (StringConstant.GetInstance().vnCharacterMatrix[i][j] == c1) {
						isFoundC1 = true;
						iC1 = i;
					}
				}
				if (!isFoundC2) {
					if (StringConstant.GetInstance().vnCharacterMatrix[i][j] == c2) {
						isFoundC2 = true;
						iC2 = i;
					}
				}
				if (isFoundC1 && isFoundC2) {
					if (iC1 == iC2) {
						return true;
					}
					else return false;
				}
			}
		}
		return false;
	}
	
	private boolean IsRegionMistake(char c1, char c2) {
		int iC1 = -1, iC2 = -1;
		boolean isFoundC1 = false, isFoundC2 = false;
		String c = "";
		for (int i = 0; i < StringConstant.MAXGROUP_REGION_CONFUSED; i++) {
			for (int j = 0; j < StringConstant.MAXCASE_REGION_CONFUSED; j++) {
				c = StringConstant.GetInstance().vnRegion_Confused_Matrix_LowerCase[i][j];
				if (!isFoundC1) {
					if (c.equals(c1 + "")) {
						isFoundC1 = true;
						iC1 = i;
					}
				}
				if (!isFoundC2) {
					if (c.equals(c2 + "")) {
						isFoundC2 = true;
						iC2 = i;
					}
				}
				if (isFoundC1 && isFoundC2) {
					if (iC1 == iC2) {
						return true;
					}
					else return false;
				}
			}
		}
		return false;
	}
	
	private boolean IsRegionMistake(char c1, char c12, char c2, char c22) {
		int iC1 = -1, iC2 = -1;
		boolean isFoundC1_12 = false, isFoundC2_22 = false;
		String ijRegion;
		for (int i = 0; i < StringConstant.MAXGROUP_REGION_CONFUSED; i++) {
			for (int j = 0; j < StringConstant.MAXCASE_REGION_CONFUSED; j++) {
				ijRegion = StringConstant.GetInstance().vnRegion_Confused_Matrix_LowerCase[i][j];
				if (!isFoundC1_12) {
					// Trường hợp ch tr gi
					if (ijRegion.equals(c1 + "" + c12) || ijRegion.equals(c1 + "")) {
						isFoundC1_12 = true;
						iC1 = i;
					}
				}
				if (!isFoundC2_22) {
					if (ijRegion.equals(c2 + "" + c22) || ijRegion.equals(c2 + "")) {
						isFoundC2_22 = true;
						iC2 = i;
					}
				}
				// tr ~ ch, d ~ gi
				if (isFoundC1_12 && isFoundC2_22) {
					if (iC1 == iC2) {
						return true;
					}
					else return false;
				}
			}
		}
		return false;
	}
	
	private boolean isTelexSign(char c1, char c2, char v1) {
		if ((c1 + "" + c2).equals(v1 + "")) {
			return true;
		}
		return false;
	}
	
	private int CalDenominatorForStringDiff(String extToken, String signToken, String extCandidate, String signCandidate) {
		return extToken.length() + signToken.length() + extCandidate.length() + signCandidate.length();
	}
	
	public String[] ExtractSignVnNotFully(String word) {
		String extWord = "";
		String[] ret = new String[2];
		int iSource = 0;
		char vnChar;
		String sign = "";
		for (int i = 0; i < word.length(); i++) {
			iSource = StringConstant.GetInstance().source.indexOf(word.charAt(i));
			// không mang dấu tiếng Việt
			if (iSource == -1) {
				extWord += word.charAt(i);
			}
			else {
				vnChar = StringConstant.GetInstance().dest.charAt(iSource);
				sign = StringConstant.GetInstance().vnSign.charAt(iSource % 5) + "";
				extWord += vnChar;
			}
		}
		
		// Kiểm tra telex
		// nếu ký tự cuối của extWord là s f r x j thì extWord còn dính lỗi telex về dấu
		if (StringConstant.GetInstance().vnSign.indexOf(extWord.charAt(extWord.length() - 1)) != -1) {
			sign = extWord.charAt(extWord.length() - 1) + "";
			// Bỏ ký tự cuối
			extWord = extWord.substring(0, extWord.length() - 1);
		}
		ret[0] = extWord;
		ret[1] = sign;
		return ret;
	}
	
	/**
	 * đo độ tương tự chuỗi, điểm càng cao, càng giống nhau
	 */
	private double CalStringSim(String token, String candidate) {
		double simEdit = CalEditDist(token, candidate);
		
		double simTri = CalTri(token, candidate);
		return simEdit + simTri;
	}
	
	/**
	 * càng lớn càng tốt
	 */
	private double CalEditDist(String token, String candidate) {
		double ret = 0;
		int editDist = Levenshtein(token, candidate);
		ret = (double)1 / (1 + editDist);
		return ret;
	}
	
	private int Levenshtein(String a, String b) {
		if (a.isEmpty()) {
			if (!b.isEmpty()) {
				return b.length();
			}
			return 0;
		}
		
		if (b.isEmpty()) {
			if (!a.isEmpty()) {
				return a.length();
			}
			return 0;
		}
		
		int cost;
		int[][] d = new int[a.length() + 1][b.length() + 1];
		int min1, min2, min3;
		
		for (int i = 0; i < a.length() + 1; i++) {
			d[i][0] = i;
		}
		
		for (int i = 0; i < b.length() + 1; i++) {
			d[0][i] = i;
		}
		
		for (int i = 1; i < a.length() + 1; i++) {
			for (int j = 1; j < b.length() + 1; j++) {
				cost = !(a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
				
				min1 = d[i - 1][j] + 1;
				min2 = d[i][j - 1] + 1;
				min3 = d[i - 1][j - 1] + cost;
				d[i][j] = Math.min(Math.min(min1, min2), min3);
			}
		}
		return d[a.length()][b.length()];
	}
	
	/**
	 * càng lớn càng tốt
	 */
	private double CalTri(String x, String y) {
		double ret = 0;
		int triX = Tri(x);
		int triY = Tri(y);
		int triXY = TriIntersection(x, y);
		ret = (double)1 / (1 + triX + triY - 2 * (triXY));
		return ret;
	}
	
	private int Tri(String x) {
		return GetHsetTri(x).size();
	}
	
	private HashSet<String> GetHsetTri(String x) {
		HashSet<String> tmp = new HashSet<>();
		if (x.length() < 4) {
			return tmp;
		}
		if (x.equals("nghiêng")) {
			System.out.println();
		}
		try {
		for (int i = 0; i < x.length() - 2; i++) {
			tmp.add(x.substring(i, 3));
		}
		} catch (Exception e) {
			System.out.println(x);
		}
		return tmp;
	}
	
	private int TriIntersection(String x, String y) {
		HashSet<String> hSetTriX = GetHsetTri(x);
		HashSet<String> hSetTriY = GetHsetTri(y);
		return CompareHashSet(hSetTriX, hSetTriY);
	}
	
	private int CompareHashSet(HashSet<String> a, HashSet<String> b) {
		int count = 0;
		for (String str1 : a) {
			for (String str2 : b) {
				if (str1.equals(str2)) {
					count++;
				}
			}
		}
		return count;
	}
}



