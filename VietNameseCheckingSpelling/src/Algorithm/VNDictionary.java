package Algorithm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class VNDictionary {
	// từ điển âm tiết
	public HashMap<String, String> SyllableDict;
	// từ điển ghép
	public ArrayList<String> CompoundDict;
	// dùng từ điển chưa có key
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
	 * Đọc từ điển tiếng âm tiết 
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
	 * đọc từ điển từ ghép tiếng việt
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
	 * Kiểm tra một token có là âm tiết tiếng việt hay không
	 */
	public boolean IsSyllableVN(String token) {
		return this.SyllableDict.containsKey(token.toLowerCase());
	}
	
	/**
	 * trả về từ ghép dạng X X+1 X+2
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
	 * trả về từ ghép liền sau token dạng X-1 X
	 */
	public HashSet<String> FindCompoundVnWord_xXx(Context context) {
		HashSet<String> hSetResult = new HashSet<>();
		String[] iArr;
		if (context.getNext().trim().length() > 0 && context.getPre().trim().length() > 0 && CompoundWordVn.GetInstance().compoundWordVnDict.containsKey(context.getPre().trim().toLowerCase())) {
			// duyệt qua List<string> là value với key là token
			ArrayList<String> arr = CompoundWordVn.GetInstance().compoundWordVnDict.get(context.getPre().toLowerCase());
			for (int i = 0; i < arr.size(); i++) {
				iArr = arr.get(i).trim().split(" ");
				// từ ghép có 2 âm tiết dạng: token iArr[0]
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
	 * trả về từ ghép dạng X-2 X-1 X
	 */
	public HashSet<String> FindCompoundVnWord_xxX(Context context) {
		HashSet<String> hSetResult = new HashSet<>();
		String[] iArr;
		ArrayList<String> arr = CompoundWordVn.GetInstance().compoundWordVnDict.get(context.getPrePre().toLowerCase());
		if (context.getPrePre().trim().length() > 0 && context.getPrePre().trim().length() > 0 && CompoundWordVn.GetInstance().compoundWordVnDict.containsKey(context.getPrePre().trim().toLowerCase())) {
			for (int i = 0; i < arr.size(); i++) {
				iArr = arr.get(i).trim().split(" ");
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
		}
		return hSetResult;
	}
	
	/**
	 * Tìm X: trả về từ ghép liền trước dạng token dạng X X+1
	 */
	public HashSet<String> FindCompoundVnWord_Xx(Context context) {
		HashSet<String> hSetResult = new HashSet<>();
		if (context.getNext().length() > 0) {
			// duyệt qua tất cả trường hợp, với value là token
			for (String str : CompoundWordVn.GetInstance().compoundWordVnDict.keySet()) {
				if (!str.equals(context.getToken().toLowerCase()) && 
					CompoundWordVn.GetInstance().compoundWordVnDict.containsValue(context.getNext()) &&
					Candidate.GetInstance().IsLikely(context.getToken(), str)) {
					hSetResult.add(str);
				}
			}
		}
		return hSetResult;
	}
	
	/**
	 * trả về từ ghép liền sau token dạng X-1 X
	 */
	public HashSet<String> FindCompoundVnWord_xX(Context context) {
		HashSet<String> hSetResult = new HashSet<>();
		String[] tmp;
		if (context.getPre().trim().length() > 0 && CompoundWordVn.GetInstance().compoundWordVnDict.containsKey(context.getPre().trim().toLowerCase())) {
			for (String str : CompoundWordVn.GetInstance().compoundWordVnDict.get(context.getPre().trim().toLowerCase())) {
				tmp = str.trim().split(" ");
				// từ ghép có 2 âm tiết dạng: token iArr[0]
				if (tmp.length == 1) {
					if (!tmp[0].toLowerCase().equals(context.getToken().toLowerCase()) &&
						tmp[0].length() > 0 && Candidate.GetInstance().IsLikely(context.getToken(), tmp[0])) {
						hSetResult.add(tmp[0]);
					}
				}
			}
		}
		return hSetResult;
	}
	
}
