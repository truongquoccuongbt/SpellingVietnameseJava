package Algorithm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class FixError {
	private ArrayList<String> hSetCandidate;
	

	public ArrayList<String> gethSetCandidate() {
		return hSetCandidate;
	}

	private String token;
	private Context context;
	private String candidate = "";
	private static FixError instance = new FixError();
	private int posCurrent;
	
	public static FixError GetInstance() {
		return instance;
	}
	
	public FixError() {
		hSetCandidate = new ArrayList<>();
		posCurrent = 0;
	}
	
	public String GetToken() {
		return this.token;
	}
	
	public void SetToken(String token) {
		this.token = token;
	}
	
	public int Count() {
		return this.hSetCandidate.size();
	}
	
//	private void GetCandidatesWithContext(HashMap<Context, Integer> dictError) {
//		this.hSetCandidate.clear();
//		if (dictError.size() > 0) {
//			for (Context key : dictError.keySet()) {
//				this.context = key;
//				if (key.equals(context)) {
//					// nếu có lỗi trong danh sách
//					if (dictError.size() > 0) {
//						// lấy lỗi đầu tiên tìm được với start index
//						this.token = context.getToken();
//						hSetCandidate = Candidate.GetInstance().CreateCandidate(context, false);
//					}
//					return;
//				}
//			}
//		}
//	}
	
	
	public String getCandidate() {
		return candidate;
	}

	public String GetElementAtIndexHashSet(HashSet<String> a, int index) {
		int pos = 0;
		for (Iterator<String> it = a.iterator(); it.hasNext();) {
			if (pos == index) {
				return it.next();
			}
			pos++;
		}
		return "";
	}
	
	@Override
	public String toString() {
		String pp = this.context.getPrePre().equals(Ngram.GetInstance().START_STRING()) ? "" : this.context.getPrePre();
		String p = this.context.getPre().equals(Ngram.GetInstance().START_STRING()) ? "" : this.context.getPre();
		String n = this.context.getNext().equals(Ngram.GetInstance().END_STRING()) ? "" : this.context.getNext();
		String nn = this.context.getNextNext().equals(Ngram.GetInstance().END_STRING()) ? "" : this.context.getNextNext();
		return String.format("%s %s %s %s %s", pp, p, this.candidate, n, nn);
	}
	
	private String HandleSentencesWithHintOneContext(String sentence, ArrayList<String> listCandidate, String token, int pos, int lengthToken) {
		pos += this.posCurrent;
		String before = sentence.substring(0, pos);
		String after = sentence.substring(pos + lengthToken, sentence.length());
		String candidate = "";
		if (listCandidate.size() > 0) {
			for (int i = 0; i < listCandidate.size(); i++) {
				candidate += listCandidate.get(i) + ", ";
				if (i == 2) break;
			}
			candidate = candidate.substring(0, candidate.length() - 2);
			String mid = "<e> " + token + ": " + candidate + " </e>";
			this.posCurrent += mid.length() - lengthToken;
			sentence = before + mid + after;
		}
		else {
			candidate = "";
			String mid = "<e> " + candidate + "</e>";
			this.posCurrent += mid.length() - lengthToken;
			sentence = before + mid + after;
		}
		return sentence;
	}
	
	
	// Sửa lỗi 1 câu
	public String FixErrorSentence(String input, LinkedHashMap<Context, Integer> listError, HashMap<Context, ArrayList<String>> listCandidate) {
		String output = input;
		ListIterator<Context> itrKey = new ArrayList<Context>(listError.keySet()).listIterator();
		
		while (itrKey.hasNext()) {	
			Context key = itrKey.next();
			boolean checkItrkeyNext = itrKey.hasNext();
			if (checkItrkeyNext) {
				Context keyNext = itrKey.next();
				if (IsTokenWrongNext(key, keyNext)) {
					ArrayList<String> candidateToken1 = listCandidate.get(key);
					ArrayList<String> candidateToken2 = listCandidate.get(keyNext);
					ArrayList<String> generalCand = CompoundCandidate(key, keyNext, candidateToken1, candidateToken2);
					int pos = listError.get(key);
					String token1_2 = key.getToken() + " " + keyNext.getToken();
					output = HandleSentencesWithHintOneContext(output, generalCand, token1_2, pos, token1_2.length());
				}
				else {
					int pos = listError.get(key);
					output = HandleSentencesWithHintOneContext(output, listCandidate.get(key), key.getToken(), pos, key.getToken().length());
					itrKey.previous();
				}
			}
			else {
				int pos = listError.get(key);
				output = HandleSentencesWithHintOneContext(output, listCandidate.get(key), key.getToken(), pos, key.getToken().length());
			}
		}
		return output;
	}
	
	private boolean IsTokenWrongNext(Context current, Context next) {
		if (!current.getNext().equals(next.getToken())) {
			return false;
		}
		return true;
	}
	
	private ArrayList<String> CompoundCandidate(Context c1, Context c2, ArrayList<String> candidate1, ArrayList<String> candidate2) {
		ArrayList<String> generalCandidate = new ArrayList<>();
		HashMap<String, Double> hCandidate = new HashMap<>();
		
		for (String cand1 : candidate1) {
			for (String cand2 : candidate2) {
				double rate = CalBiGramForCandidate(c1.getPre(), cand1, cand2, c2.getNext());
				hCandidate.put(cand1 + " " + cand2, rate);
			}
		}
		LinkedHashMap<String, Double> linkCandidate = SortListCand(hCandidate);
		int i = 0;
		for (String key : linkCandidate.keySet()) {
			if (i == 5) break;
			generalCandidate.add(key);
			i++;
		}
		return generalCandidate;
	}
	
	
	
	private double CalBiGramForCandidate(String tokenPre, String tokenCand1, String tokenCand2, String tokenAfter) {
		double rate1 = Ngram.GetInstance().CalBigram(tokenPre, tokenCand1);
		double rate2 = Ngram.GetInstance().CalBigram(tokenCand1, tokenCand2);
		double rate3 = Ngram.GetInstance().CalBigram(tokenCand2, tokenAfter);
		return (rate1 + rate2 + rate3) / 3;
	}
	
	private LinkedHashMap<String, Double> SortListCand(HashMap<String, Double> listCand) {
		Set<Entry<String, Double>> entries = listCand.entrySet();
		Comparator<Entry<String, Double>> valueComparator = new Comparator<Map.Entry<String, Double>>() {
			
			@Override
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				Double c1 = o1.getValue();
				Double c2 = o2.getValue();
				return c2.compareTo(c1);
			}
		};
		
		List<Entry<String, Double>> listOfEntries = new ArrayList<Entry<String, Double>>(entries);
		Collections.sort(listOfEntries, valueComparator);
		LinkedHashMap<String, Double> sortByValue = new LinkedHashMap<>(listOfEntries.size());
		for (Entry<String, Double> entry : listOfEntries) {
			sortByValue.put(entry.getKey(), entry.getValue());
		}
		return sortByValue;
	}
}
