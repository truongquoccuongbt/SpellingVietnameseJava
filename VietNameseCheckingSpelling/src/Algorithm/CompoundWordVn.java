package Algorithm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class CompoundWordVn {
	private static CompoundWordVn instance = new CompoundWordVn();
	public static CompoundWordVn GetInstance() {
		return instance;
	}
	public HashMap<String, ArrayList<String>> compoundWordVnDict;
	private CompoundWordVn() {
		ReadCompoundWordVnFile();
	}
	
	private void ReadCompoundWordVnFile() {
		compoundWordVnDict = new HashMap<>();
		String key = "";
		ArrayList<String> value;
		String[] iArr;
		String[] valueArr;
		String line;
		try {
			FileInputStream fs = new FileInputStream(FileManager.GetInstance().CompoundWordLinkedList());
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			while ((line = br.readLine()) != null) {
				iArr = line.split("@");
				key = iArr[0];
				valueArr = iArr[1].split("_");
				value = new ArrayList<>();
				for (int i = 0; i < valueArr.length; i++) {
					value.add(valueArr[i]);
				}
				compoundWordVnDict.put(key, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
