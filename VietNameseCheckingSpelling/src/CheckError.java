import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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
		
		int countError = FindError.GetInstance().CountError();
		System.out.println("Số lỗi: " + countError);
	}
	
	public void GetCandidate() {
		FixError.GetInstance().GetCandidatesWithContext(FindError.GetInstance()., dictError);
	}
}
