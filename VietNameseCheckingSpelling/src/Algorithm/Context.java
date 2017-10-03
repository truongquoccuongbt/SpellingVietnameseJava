package Algorithm;

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
		GetContext(iWord, words);
	}
	
	public Context() {
		this.prePre = this.pre = this.next = this.nextNext = "";
	}
	
	/**
	 * getContex by selectionWord
	 */
	public void GetContext(int iword, String[] words) {
		this.prePre = this.pre = this.token = this.next = this.nextNext = "";
		String token = words[iword].trim();
		
		this.token = token.replaceAll(StringConstant.GetInstance().patternSPEC, "");
		if (this.token.length() < token.length()) {
			this.prePre = this.pre = this.next = this.nextNext = "";
			return;
		}
		else {
			this.token = token.replaceAll(StringConstant.GetInstance().patternOPEN, "");
			// chứa ký tự thuộc nhóm OPEN
			if (this.token.length() < token.length()) {
				this.pre = this.prePre = "";
				FindNext_NextNext(iword, words);
				return;
			}
			else {
				this.token = token.replaceAll(StringConstant.GetInstance().patternCLOSE, "");
				if (this.token.length() < token.length()) {
					this.next = this.nextNext = "";
					return;
				}
				else {
					FindPre_PrePre(iword, words);
					return;
				}
			}
		}
	}
	
	private void FindPre_PrePre(int iWord, String[] words) {
		if (iWord > 0) {
			this.pre = words[iWord - 1].trim();
			if (this.pre.length() == 0) {
				return;
			}
			String preSPEC, preClose, preOpen;
			preSPEC = this.pre.replaceAll(StringConstant.GetInstance().patternSPEC, "");
			preClose = this.pre.replaceAll(StringConstant.GetInstance().patternCLOSE, "");
			preOpen = this.pre.replaceAll(StringConstant.GetInstance().patternOPEN, "");
			boolean isUpper = Character.isUpperCase(this.pre.charAt(0)) ? true : false;
			
			if (preSPEC.length() < this.pre.length() || preClose.length() < this.pre.length() || (isUpper && iWord != 2)) {
				this.pre = this.prePre = "";
				FindNext_NextNext(iWord, words);
				return;
			}
			else {
				if (preOpen.length() < this.pre.length()) {
					this.pre = preOpen;
					this.prePre = "";
					CheckTokenContainsClose_FindNext(iWord, words);
				}
				else {
					if (iWord > 1) {
						this.prePre = words[iWord - 2].trim();
						if (this.prePre.length() == 0) {
							return;
						}
						String prePreSPEC, prePreClose, prepreOpen;
						prePreSPEC = this.prePre.replaceAll(StringConstant.GetInstance().patternSPEC, "");
						prePreClose = this.prePre.replaceAll(StringConstant.GetInstance().patternCLOSE, "");
						prepreOpen = this.prePre.replaceAll(StringConstant.GetInstance().patternOPEN, "");
						isUpper = Character.isUpperCase(this.prePre.charAt(0));
						
						if (prePreSPEC.length() < this.prePre.length() || prePreClose.length() < this.prePre.length() || (isUpper && iWord != 2)) {
							this.prePre = "";
							CheckTokenContainsClose_FindNext(iWord, words);
						}
						else if (prepreOpen.length() < this.prePre.length()) {
							this.prePre = prepreOpen;
						}
						CheckTokenContainsClose_FindNext(iWord, words);
					}
					else {
						this.prePre = "";
						CheckTokenContainsClose_FindNext(iWord, words);
					}
				}
			}
		}
		else {
			this.pre = this.prePre = "";
			FindNext_NextNext(iWord, words);
			return;
		}
	}
	
	private void CheckTokenContainsClose_FindNext(int iWord, String[] words) {
		String tokenClose = words[iWord].trim().replaceAll(StringConstant.GetInstance().patternCLOSE, "");
		if (tokenClose.length() < words[iWord].length()) {
			return;
		}
		else {
			FindNext_NextNext(iWord, words);
			return;
		}
	}
	
	private void FindNext_NextNext(int iword, String[] words) {
		// Kiểm tra không phải âm tiết cuối câu.
		if (iword < words.length - 1){
			this.next = words[iword + 1].trim();
			if (this.next.length() == 0) {
				return;
			}
			String nextSPEC, nextClose, nextOpen;
			nextSPEC = this.next.replaceAll(StringConstant.GetInstance().patternSPEC, "");
			nextClose = this.next.replaceAll(StringConstant.GetInstance().patternCLOSE, "");
			nextOpen = this.next.replaceAll(StringConstant.GetInstance().patternOPEN, "");
			
			boolean isUpper = Character.isUpperCase(this.next.charAt(0)) ? true : false;
			if (nextSPEC.length() < this.next.length() || nextOpen.length() < this.next.length() || isUpper) {
				this.next = this.nextNext = "";
				return;
			}
			else {
				if (nextClose.length() < this.next.length()) {
					this.next = nextClose;
					this.nextNext = "";
					return;
				}
				else {
					if (iword < words.length - 2) {
						this.nextNext = words[iword + 2].trim();
						if (this.nextNext.length() == 0) {
							return;
						}
						String nextNextSPEC, nextNextOpen, nextNextClose;
						nextNextSPEC = this.nextNext.replaceAll(StringConstant.GetInstance().patternSPEC, "");
						nextNextOpen = this.nextNext.replaceAll(StringConstant.GetInstance().patternOPEN, "");
						nextNextClose = this.nextNext.replaceAll(StringConstant.GetInstance().patternCLOSE, "");
						
						isUpper = Character.isUpperCase(this.nextNext.charAt(0)) ? true : false;
						if (nextNextSPEC.length() < this.nextNext.length() || nextNextOpen.length() < this.nextNext.length() || isUpper) {
							this.nextNext = "";
							return;
						}
						else {
							if (nextNextClose.length() < this.nextNext.length()) {
								this.nextNext = nextNextClose;
								return;
							}
						}
					}
					else {
						this.nextNext = "";
						return;
					}
				}
			}
		}
		else {
			this.next = this.nextNext = "";
			return;
		}
	}
	
	public void CopyForm(Context c) {
		this.prePre = c.prePre;
		this.pre = c.pre;
		this.token = c.token;
		this.next = c.next;
		this.nextNext = c.nextNext;
	}
	
	@Override
	public String toString() {
		if (this.prePre == null) {
			this.prePre = "";
		}
		if (this.pre == null) {
			this.pre = "";
		}
		if (this.next == null) {
			this.next = "";
		}
		if (this.nextNext == null) {
			this.nextNext = "";
		}
		String pp = this.prePre.equals(Ngram.GetInstance().START_STRING()) ? "" : this.prePre;
		String p = this.pre.equals(Ngram.GetInstance().START_STRING()) ? "" : this.pre;
		String n = this.next.equals(Ngram.GetInstance().END_STRING()) ? "" : this.next;
		String nn = this.nextNext.equals(Ngram.GetInstance().END_STRING()) ? "" : this.nextNext;
		return String.format("%s %s %s %s %s", pp, p, this.token, n, nn).trim();
	}
	
	@Override
	public boolean equals(Object obj) {
		Context c;
		if (obj.getClass().getName() != this.getClass().getName()) {
			return false;
		}
		else c = (Context)obj;
		if (this.token.equals(c.token)) {
			return true;
		}
		return false;
	}
}
