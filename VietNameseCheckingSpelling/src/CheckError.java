import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import Algorithm.Context;
import Algorithm.FindError;
import Algorithm.FixError;
import Algorithm.Ngram;
import Algorithm.VNDictionary;

public class CheckError {
	private static CheckError instance = new CheckError();
	private String input;
	private String output;
	private String[] arrInput;
	private ArrayList<Integer> posTokenError;
	private int countError;
	private int posCurrent;
	private HashMap<Context, Integer> listError;
	private HashMap<Context, ArrayList<String>> listCandidate;
	private int posBefore;
	
	public int getCountError() {
		return countError;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	public static CheckError GetInstance() {
		return instance;
	}
	
	public String GetInput() {
		return this.input;
	}
	
	private CheckError() {
		this.input = "";
		this.output = "";
		this.posCurrent = 0;
		this.posBefore = -1;
		RunFirst();
	}
	
	private void RunFirst() {
		Ngram.GetInstance().RunFirst();
		VNDictionary.GetInstance().RunFirst();
	}
	
	public String ReadInput() {
		try {
			String path = new File("").getAbsolutePath() + "/input/input.txt";
			FileInputStream fs = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			String line;
			while ((line = br.readLine()) != null) {
				this.input = line;
			}
			this.output = new String(this.input);
		//	InitArrInput();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return this.input;
	}
	
	public void Check() {
		FindError.GetInstance().AddSentences(Sentences.GetInstance().SplitSentence(this.input));
		FindError.GetInstance().Find();
		this.listCandidate = FindError.GetInstance().gethMapCandidate();
		
		//FixError.GetInstance().GetCandidatesWithContext(FindError.GetInstance().GetDictContext_ErrorRange());
		this.listError = FindError.GetInstance().GetDictContext_ErrorRange();
		InitPosTokenError();
		this.countError = FindError.GetInstance().CountError();
	}
	
	private void InitPosTokenError() {
		this.posTokenError = new ArrayList<>();
		String[] input = this.input.split(" ");
		int value;
		for (Context c : this.listError.keySet()) {
			value = this.listError.get(c);
			this.posTokenError.add(FindPosTokenInInput(input, value));
		}
	}
	
//	private void InitArrInput() {
//		String[] tmp = this.input.split(" ");
//		this.arrInput = new String[tmp.length];
//		for (int i = 0; i < tmp.length; i++) {
//			arrInput[i] = tmp[i];
//		}
//	}
	
	public void FixError() {
		Context tmp;
		
		if ( this.listError.size() > 0) {
			LinkedHashMap<Context , Integer> tmp1 = SortListError(this.listError);
//			while (tmp1.size() > 0) {
//				tmp = GetElementIndexHashMap(this.listError, 0);
//				//FixError.GetInstance().GetCandidatesWithContext(this.listError);
//				int pos = this.listError.get(tmp);
//				this.output = HandleString(pos, this.listCandidate.get(tmp), tmp);
//				this.listError.remove(tmp);
//			}
			for (Context con : tmp1.keySet()) {
				int pos = tmp1.get(con);
				this.output = HandleString(pos, this.listCandidate.get(con), con);
			}
		}
	}
	
	public String HandleString(int pos, ArrayList<String> arrCandidate, Context c) {
		pos += this.posCurrent;
		String before = this.output.substring(0, pos);
		String after = this.output.substring(pos + c.getToken().length(), this.output.length());
		String candidate = "";
		if (arrCandidate.size() > 0) {
			for (int i = 0; i < arrCandidate.size(); i++) {
				candidate += arrCandidate.get(i) + ", ";
				if (i == 2) break;
			}
			candidate = candidate.substring(0, candidate.length() - 2) + " ";
			String mid = "<e> " + c.getToken() + ": "  + candidate + "</e>";
			this.posCurrent += mid.length() - c.getToken().length();
			this.output = before + mid + after;
		}
		else {
			candidate = " ";
			String mid = "<e> " + c.getToken() + ": "  + candidate + "</e>";
			this.posCurrent += mid.length() - c.getToken().length();
			this.output = before + mid + after;
		}
		posBefore = pos;
		return output;
	}
	
	public Context GetElementIndexHashMap(HashMap<Context, Integer> has, int index) {
		int i = 0;
		for (Context c : has.keySet()) {
			if (i == index) {
				return c;
			}
			i++;
		}
		return null;
	}
	
	/**
	 * 
	 * @param input
	 * @param posChar
	 * 
	 * Xác định vị trí sai của một token trong input
	 * VD: Tôi đị chơi.
	 * 		posChar = 4 => vị trái token sai: 2 (đị)
	 * 
	 */
	private int FindPosTokenInInput(String[] input, int posChar) {
		int length = 0;
		for (int posToken = 0; posToken < input.length; posToken++) {
			length += input[posToken].length() + 1;
			if (posChar < length) {
				return posToken + 1;
			}
		}
		return -1;
	}
	
	public String GetPosTokenError() {
		String tmp = "";
		for (int i = 0; i < this.posTokenError.size(); i++) {
			tmp += this.posTokenError.get(i).toString() + " "; 
		}
		return tmp;
	}
	
	private LinkedHashMap<Context, Integer> SortListError(HashMap<Context, Integer> listError) {
		Set<Entry<Context, Integer>> entries = listError.entrySet();
		Comparator<Entry<Context, Integer>> valueComparator = new Comparator<Map.Entry<Context, Integer>>() {
			
			@Override
			public int compare(Entry<Context, Integer> o1, Entry<Context, Integer> o2) {
				Integer c1 = o1.getValue();
				Integer c2 = o2.getValue();
				return c1.compareTo(c2);
			}
		};
		
		List<Entry<Context, Integer>> listOfEntries = new ArrayList<Entry<Context, Integer>>(entries);
		Collections.sort(listOfEntries, valueComparator);
		LinkedHashMap<Context, Integer> sortByValue = new LinkedHashMap<>(listOfEntries.size());
		for (Entry<Context, Integer> entry : listOfEntries) {
			sortByValue.put(entry.getKey(), entry.getValue());
		}
		return sortByValue;
	}
}
