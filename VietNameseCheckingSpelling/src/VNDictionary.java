import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class VNDictionary {
	//từ điển âm tiết
	public HashMap<String, String> SyllableDict;
	//từ điển từ ghép
	public ArrayList<String> CompoundDict;
	//Dùng từ điển chưa có key
	private VNDictionary() {
		this.SyllableDict = new HashMap<>();
		this.CompoundDict = new ArrayList<>();
	}
	
	public void RunFirst() {
		this.SyllableDict = ReadSyllableDict();
		this.CompoundDict = ReadCompoundWordDict();
	}
	
	/**
	 * Đọc từ điển tiếng âm tiết lên
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
	 * Đọc từ điển từ ghép tiếng Việt
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
	 * 
	 */
	
}
