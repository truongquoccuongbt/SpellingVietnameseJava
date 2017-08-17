import java.util.regex.Pattern;

import javax.management.relation.RelationServiceNotRegisteredException;

public class Context {
	private String prePre;
	private String pre;
	private String token;
	private String next;
	private String nextNext;
	private int wordCount;
	
	public String getPrePre() {
		return prePre;
	}
	public void setPrePre(String prePre) {
		this.prePre = prePre;
	}
	public String getPre() {
		return pre;
	}
	public void setPre(String pre) {
		this.pre = pre;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public String getNextNext() {
		return nextNext;
	}
	public void setNextNext(String nextNext) {
		this.nextNext = nextNext;
	}
	
	public int getWordCount() {
		int count = 1;
		if (this.prePre.length() > 0) {
			count++;
		}
		if (this.pre.length() > 0) {
			count++;
		}
		if (this.next.length() > 0) {
			count++;
		}
		if (this.nextNext.length() > 0) {
			count++;
		}
		return count;
	}
	
	private Pattern patternSPEC = Pattern.compile(StringConstant.GetInstance().patternSPEC);
	private Pattern patternOPEN = Pattern.compile(StringConstant.GetInstance().patternOPEN);
	private Pattern patternCLOSE = Pattern.compile(StringConstant.GetInstance().patternCLOSE);
	
	public Context(int iWord, String[] words) {
		getContext(iWord, words);
	}
	
	/**
	 * getContex by selectionWord
	 */
	public void getContext() {
		
	}
}
