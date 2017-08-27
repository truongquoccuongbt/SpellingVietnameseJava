import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import Algorithm.FindError;

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
		this.input = ReadInput();
	}
	
	public String ReadInput() {
		String strInput = "";
		try {
			String path = new File("").getAbsolutePath() + "/input/input.txt";
			FileInputStream fs = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			String line;
			while ((line = br.readLine()) != null) {
				strInput = line;
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return strInput;
	}
	
	public void Check() {
		FindError.GetInstance().AddSentences(Sentences.GetInstance().SplitSentence(this.input));
		FindError.GetInstance().Find();
		
		int countError = FindError.GetInstance().CountError();
		System.out.println("Số lỗi: " + countError);
	}
}
