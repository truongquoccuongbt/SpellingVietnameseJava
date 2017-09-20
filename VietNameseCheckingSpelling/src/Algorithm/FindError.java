package Algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindError {
	private HashMap<Context, Integer> dictContext_ErrorString;
	// số từ trong một câu
	private String[] wordsInSentence;
	// từ đang được kiểm tra lỗi
	private String word;
	//
	private Context originalContext;
	// partern chức những ký tự đăc biệt
	private Pattern patternCheckSpecial = Pattern.compile(StringConstant.GetInstance().patternCheckSpecialChar, Pattern.UNICODE_CHARACTER_CLASS);
	// giữ từ đang kiểm lỗi sau khi đã bỏ dấu câu
	private String iWordReplaced;
	// danh sách các câu
	private String[] arrSentences;
	// câu hiện tại đang kiểm lỗi
	private String sentence;
	// mảng chứa những từ trong câu đang kiểm lỗi
	// không thay đổi trong quá trình kiểm tra
	private String[] originWords;
	// độ dài câu hiện tại
	private int length;
	// Hashset chứa những từ gợi ý cho lỗi đang kiểm tra
	private HashSet<String> hSetCand;
	// Từ điển với key là ngử cảnh, value là câu lỗi
	private HashMap<Context, Integer> dictContext_ErrorRange;
	// vị trí đầu đoạn.
	private int start;
	// vị trí cuối đoạn
	private int end;
	// Cờ đánh dấu dừng kiểm lỗi
	private boolean isStopFindError;
	
	private static FindError instance = new FindError();
	
	public static FindError GetInstance() {
		return instance;
	}
	
	public HashMap<Context, Integer> GetDictContext_ErrorRange() {
		return this.dictContext_ErrorRange;
	}
	
	private FindError() {
		this.isStopFindError = false;
		this.dictContext_ErrorString = new HashMap<>();
		this.originalContext = new Context();
		this.hSetCand = new HashSet<>();
		this.dictContext_ErrorRange = new HashMap<>();
	}
	
	public boolean isStopFindError() {
		return isStopFindError;
	}

	public void setStopFindError(boolean isStopFindError) {
		this.isStopFindError = isStopFindError;
	}

	public int CountError() {
		return dictContext_ErrorString.size();
	}
	
	public void AddSentences(String[] arrSentences) {
		this.arrSentences = new String[arrSentences.length];
		for (int i = 0; i < arrSentences.length; i++) {
			this.arrSentences[i] = arrSentences[i];
		}
		this.start = 0;
		
	}
	
	public void Find() {
		for (int j = 0; j < this.arrSentences.length; j++) {
			if (isStopFindError) break;
			this.sentence = arrSentences[j].trim();
			wordsInSentence = this.sentence.split(" ");
			this.originWords = this.sentence.split(" ");
			this.length = this.wordsInSentence.length;
			for (int i = 0; i < this.length; i++) {
				word = this.wordsInSentence[i];
				originalContext.setToken(word.toLowerCase());
				
				//Kiểm tra các ký tự đặc biệt, mail, số, tên riếng
				// Nếu có chứa thì bỏ qua
				Matcher m = patternCheckSpecial.matcher(originalContext.getToken());
				if (m.matches()) {
					continue;
				}
				// Viết hoa giữa câu thì bỏ qua vì là danh từ riêng
				if (Character.isUpperCase(word.trim().charAt(0)) && i != 0) {
					continue;
				}
				
				else {
					Context context = new Context(i, wordsInSentence);
					iWordReplaced = context.getToken().replaceAll(StringConstant.GetInstance().patternSignSentence, "");
					if (!word.contains(iWordReplaced) || iWordReplaced.length() == 0) {
						
					}
					if (wordsInSentence[i].length() != iWordReplaced.length()) {
						context.setToken(iWordReplaced);
					}
					if (!word.contains("\r")) {
						
					}
					originalContext.CopyForm(context);
					if (!VNDictionary.GetInstance().IsSyllableVN(iWordReplaced.trim().toLowerCase())) {
						if (i < length - 1) {
							CheckError(context, i, false);
						}
						else {
							// là từ cuối câu nên không ảnh hưởng từ phía sau
							if (HasCandidate(context, false)) {
								AddError(context, false);
							}
						}						
					}
					// End if wrong word
					// Kiểm tra token có khả năng sai ngữ cảnh hay không
						
					else if (!RightWordCandidate.GetInstance().CheckRightWord(context)){
						if (i < length - 1) {
							CheckError(context, i, true);
						}
						else {
							// là từ cuối câu nên không ảnh hưởng từ phía sau
							if (HasCandidate(context, true)) {
								AddError(context, true);
							}
						}		} // end else if right word
				}
				start += word.length() + 1;
			} // end for : duyệt từng từ trong câu
		}// end for: duyệt từ câu
		isStopFindError = true;
		
		for (Context ct : dictContext_ErrorRange.keySet()) {
//			int a = FindIndexSentenceOfWord(dictContext_ErrorRange.get(ct));
//			System.out.println(a);
			dictContext_ErrorString.put(ct, start);
		}
	}
	
	private void CheckError(Context context, int i, boolean isRightError) {
		// Ngữ cảnh chỉ có một từ nhưng thuộc trường hợp sai ngữ cảnh
		if (context.getWordCount() == 1 && isRightError) {
			return;
		}
		if (this.wordsInSentence[i + 1].length() > 0) {
			if (Character.isUpperCase(this.wordsInSentence[i + 1].charAt(0))) {
				if (HasCandidate(context, isRightError)) {
					AddError(context, isRightError);
				}
				return;
			}
			
			// Kiểm tra từ hiện tại có sai do từ tiếp theo hay không
			context.GetContext(i + 1, wordsInSentence);
		
			// Nếu ngữ cảnh từ tiếp theo, có chứa từ hiện tại thì kiểm tra 
			if (context.toString().contains(originalContext.getToken())) {
				hSetCand.clear();
				hSetCand = Candidate.GetInstance().CreateCandidate(context, isRightError);
				
				// từ thứ  i + 1 có candidate thay thế
				// nếu không, thì từ hiện tại là sai
				if (hSetCand.size() > 0) {
					context.CopyForm(originalContext);
					context.setNext(GetElementAtIndexHashSet(hSetCand, 0));
					
					// dùng candidate tốt nhất để làm ngữ cảnh
					// kiểm tra từ hiện tại có sai do từ sau hay không
					
					if (HasCandidate(context, isRightError)) {
						// từ hiện tại sai mà không phải do từ phía sau
						// tránh làm sai những gram phía sau
						wordsInSentence[i] = GetElementAtIndexHashSet(hSetCand, 0);
						context.CopyForm(originalContext);
						AddError(context, isRightError);
					}
					else {
						context.CopyForm(originalContext);
						// nếu từ hiện tại có candidate thay thế thì thêm vào đó thành một lỗi
						if (HasCandidate(context, isRightError)) {
							AddError(context, isRightError);
						}
					}
				}
				else {
					context.CopyForm(originalContext);
					// Nếu từ hiện tại có candidate thay thế thì thêm vào đó thành một lỗi
					if (HasCandidate(context, isRightError)) {
						AddError(context, isRightError);
					}
				}
			}
			else {
				context.CopyForm(originalContext);
				// Nếu từ hiện tại có candidate thay thế thì thêm vào đó thành một lỗi
				if (HasCandidate(context, isRightError)) {
					AddError(context, isRightError);
				}
			}
		}
	}
	
	private String GetElementAtIndexHashSet(HashSet<String> a, int index) {
		int pos = 0;
		for (Iterator<String> it = a.iterator(); it.hasNext();) {
			if (pos == index) {
				return it.next();
			}
			pos++;
		}
		return "";
	}
	
	private boolean HasCandidate(Context context, boolean isRightError) {
		this.hSetCand.clear();
		if (isRightError) {
			hSetCand = RightWordCandidate.GetInstance().CreateCandidate(context);
		}
		else {
			hSetCand = WrongWordCandidate.GetInstance().CreateCandidate(context);
		}
		if (hSetCand.size() > 0) {
			return true;
		}
		return false;
	}
	
	private void AddError(Context context, boolean isRightError) {
		if (isRightError) {
			this.dictContext_ErrorRange.put(context, start);
		}
		else {
			dictContext_ErrorRange.put(context, start);
		}
	}
	
	private int FindIndexSentenceOfWord(int word) {
		String[] words;
		int indexSentence = 0;
		int indexWord = 0;
		for (String str : this.arrSentences) {
			words = str.split(" ");
			for (int i = 0; i < words.length; i++) {
				if (word == indexWord) {
					return indexSentence;
				}
				indexWord++;
			}
			indexSentence++;
		}
		return -1;
	}
}
