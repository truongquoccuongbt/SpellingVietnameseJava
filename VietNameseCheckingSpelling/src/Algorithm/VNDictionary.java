package Algorithm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;

public class VNDictionary {
	// tá»« Ä‘iá»ƒn Ă¢m tiáº¿t
	public HashMap<String, String> SyllableDict;
	// tá»« Ä‘iá»ƒn tá»« ghĂ©p
	public ArrayList<String> CompoundDict;
	// dĂ¹ng tá»« Ä‘iá»ƒn chÆ°a cĂ³ key
	private VNDictionary() {
		this.SyllableDict = new HashMap<>();
		this.CompoundDict = new ArrayList<>();
	}
	
	public void RunFirst() {
		this.SyllableDict = ReadSyllableDict();
		this.CompoundDict = ReadCompoundWordDict();
	}
	private static VNDictionary instance = new VNDictionary();
	
	public static VNDictionary GetInstance() {
		return instance;
	}
	/**
	 * Ä�á»�c tá»« Ä‘iá»ƒn tiáº¿ng Ă¢m tiáº¿t lĂªn
	 */
	public HashMap<String, String> ReadSyllableDict() {
		HashMap<String, String> result = new HashMap<>();
		try {
			FileInputStream fs = new FileInputStream(new FileManager().SyllDict());
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			String line;
			while ((line = br.readLine()) != null) {
				result.put(line, "");
			}
			br.close();
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Ä�á»�c tá»« Ä‘iá»ƒn tá»« ghĂ©p tiáº¿ng Viá»‡t
	 */
	public ArrayList<String> ReadCompoundWordDict() {
		ArrayList<String> result = new ArrayList<>();
		try {
			FileInputStream fs = new FileInputStream(new FileManager().CompoundWordDict());
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
			br.close();
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Kiá»ƒm tra má»™t token cĂ³ lĂ  Ă¢m tiáº¿t tiáº¿ng Viá»‡t hay khĂ´ng
	 */
	public boolean IsSyllableVN(String token) {
		return this.SyllableDict.containsKey(token.toLowerCase());
	}
	
	/**
	 * tráº£ vá»� tá»« ghĂ©p cĂ³ dáº¡ng X X + 1 X + 2
	 */
	public HashSet<String> FindCompoundVNWord_Xxx(Context context) {
		HashSet<String> hSetResult = new HashSet<>();
		String[] iArr;
		if (context.getNext().trim().length() > 0 && context.getNextNext().trim().length() > 0) {
			// duyá»‡t qua táº¥t cáº£ trÆ°á»�ng há»£p, vá»›i value lĂ  token
			for (String str : CompoundWordVn.GetInstance().compoundWordVnDict.keySet()) {
				ArrayList<String> arr = CompoundWordVn.GetInstance().compoundWordVnDict.get(str);
				for (String tmp : arr) {
					iArr = tmp.trim().split(" ");
					// tá»« ghĂ©p cĂ³ 3 Ă¢m tiáº¿t dáº¡ng: key next nextnext
					if (iArr.length == 2) {
						if (!str.toLowerCase().equals(context.getToken().toLowerCase()) && iArr[0].equals(context.getNext().trim()) && iArr[1].equals(context.getNextNext().trim()) && Candidate.GetInstance().IsLikely(context.getToken(), str)) {
							hSetResult.add(str);
						}
					}
				}
			}
		}
		return hSetResult;
	}
	
	/**
	 * tráº£ vá»� tá»« ghĂ©p dáº¡ng X-1 X X+1
	 */
	public HashSet<String> FindCompoundVnWord_xXx(Context context) {
		HashSet<String> hSetResult = new HashSet<>();
		String[] iArr;
		if (context.getNext().trim().length() > 0 && context.getPre().trim().length() > 0) {
			// duyá»‡t qua ArrayList<string> lĂ  value vá»›i key lĂ  token
			for (String i : CompoundWordVn.GetInstance().compoundWordVnDict.get(context.getPre().toLowerCase())) {
				iArr = i.trim().split(" ");
				// tá»« ghĂ©p cĂ³ 3 Ă¢m tiáº¿t dáº¡ng: w_1 iArr[0]_w_1
				if (iArr.length == 2) {
					if (!iArr[0].toLowerCase().equals(context.getToken().toLowerCase()) && iArr[1].equals(context.getNext().trim()) && iArr[0].length() > 0 && Candidate.GetInstance().IsLikely(context.getToken(), iArr[0])) {
						hSetResult.add(iArr[0]);
					}
				}
			}
		}
		return hSetResult;
	}
	
	/*
	 * tráº£ vá»� tá»« ghĂ©p dáº¡ng X-2 X-1 X
	 */
	public HashSet<String> FindCompoundVnWord_xxX(Context context) {
		HashSet<String> hSetResult = new HashSet<>();
		String[] iArr;
		for (String i : CompoundWordVn.GetInstance().compoundWordVnDict.get(context.getPrePre().toLowerCase())) {
			iArr = i.trim().split(" ");
			// tá»« ghĂ©p cĂ³ 3 Ă¢m tiáº¿t dáº¡ng: w_2 w_1 iArr[1]
			if (iArr.length == 2) {
				if (!iArr[1].toLowerCase().equals(context.getToken().toLowerCase())
						&& iArr[0].equals(context.getPre().trim())
						&& iArr[1].length() > 0
						&& Candidate.GetInstance().IsLikely(context.getToken(), iArr[1])) {
					hSetResult.add(iArr[1]);
				}
			}
		}
		return hSetResult;
	}
	
}
