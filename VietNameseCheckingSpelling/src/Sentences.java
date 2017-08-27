
public class Sentences {
	private int countSentences;
	private String[] arrSentences;
	private static Sentences instance = new Sentences();
	
	public static Sentences GetInstance() {
		return instance;
	}
	
	public String[] SplitSentence(String paragraph) {
		String[] tmp = paragraph.split("[.]");
		arrSentences = new String[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			arrSentences[i] = tmp[i] + ".";
		}
		this.countSentences = arrSentences.length;
		return this.arrSentences;
	}
	
	public int CountSentences() {
		return this.countSentences;
	}
}
