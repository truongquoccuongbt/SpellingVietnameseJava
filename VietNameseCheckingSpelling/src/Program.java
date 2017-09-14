	import Algorithm.FindError;
import Algorithm.Ngram;

public class Program {
	public static void main(String[] args) {
		CheckError.GetInstance().ReadInput();
		CheckError.GetInstance().Check();
		
		String a = CheckError.GetInstance().GetInput();
		System.out.println(a);
	}
}
