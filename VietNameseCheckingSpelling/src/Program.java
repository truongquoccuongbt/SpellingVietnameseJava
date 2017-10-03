import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Tokenizer.RegexRules;

public class Program {
	public static void main(String[] args) {
		CheckError.GetInstance().ReadInput();
		CheckError.GetInstance().Check();
		CheckError.GetInstance().FixError();
		
		
		System.out.println("Số lỗi: " + CheckError.GetInstance().getCountError());
		System.out.println("Input: " + CheckError.GetInstance().GetInput());
		System.out.println("Output: " + CheckError.GetInstance().getOutput());
	}
}
