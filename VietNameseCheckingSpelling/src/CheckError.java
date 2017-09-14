import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

import Algorithm.Context;
import Algorithm.FindError;
import Algorithm.FixError;
import Algorithm.Ngram;
import Algorithm.VNDictionary;

public class CheckError {
	private static CheckError instance = new CheckError();
	private String input;
	
	public static CheckError GetInstance() {
		return instance;
	}
	
	public String GetInput() {
		return this.input;
	}
	
	private CheckError() {
		this.input = "";
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
		}catch (IOException e) {
			e.printStackTrace();
		}
		return this.input;
	}
	
	public void Check() {
		FindError.GetInstance().AddSentences(Sentences.GetInstance().SplitSentence(this.input));
		FindError.GetInstance().Find();
		
		FixError.GetInstance().GetCandidatesWithContext(FindError.GetInstance().GetDictContext_ErrorRange());
		
//		int countError = FindError.GetInstance().CountError();
//		System.out.println("Số lỗi: " + countError);
		FixError();
	}
	
	public void FixError() {
		HashMap<Context, Integer> a = FindError.GetInstance().GetDictContext_ErrorRange();
		String output;
		Context tmp;
		if ( a.size() > 0) {
			while (a.size() > 0) {
				tmp = GetElementIndexHashMap(a, 0);
				int pos = a.get(tmp);
				this.input = HandleString(pos, FixError.GetInstance().getCandidate(), tmp);
				a.remove(tmp);
				FixError.GetInstance().GetCandidatesWithContext(a);
			}
//			System.out.println(this.input);
		}
		else {
//			System.out.println(this.input);
		}
	}
	
	public String HandleString(int pos, String candidate, Context c) {
		while (this.input.charAt(pos - 1) != ' ') {
			pos++;
		}
		
		
		String before = this.input.substring(0, pos);
		String after = this.input.substring(pos + c.getToken().length(), this.input.length());
		String output = before + candidate + after;
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
	
}
