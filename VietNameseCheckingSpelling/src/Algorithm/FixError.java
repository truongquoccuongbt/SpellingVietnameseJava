package Algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class FixError {
	private ArrayList<String> hSetCandidate;
	

	public ArrayList<String> gethSetCandidate() {
		return hSetCandidate;
	}

	private String token;
	private Context context;
	private String candidate = "";
	private static FixError instance = new FixError();
	
	public static FixError GetInstance() {
		return instance;
	}
	
	public FixError() {
		hSetCandidate = new ArrayList<>();
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
	
	public void GetCandidatesWithContext(HashMap<Context, Integer> dictError) {
		this.hSetCandidate.clear();
		if (dictError.size() > 0) {
			for (Context key : dictError.keySet()) {
				this.context = key;
				if (key.equals(context)) {
					// nếu có lỗi trong danh sách
					if (dictError.size() > 0) {
						// lấy lỗi đầu tiên tìm được với start index
						this.token = context.getToken();
						hSetCandidate = Candidate.GetInstance().CreateCandidate(context, false);
					}
					return;
				}
			}
		}
	}
	
	public String getCandidate() {
		return candidate;
	}
	
	private void GetCandidateWithPreviousToken() {
		
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
}
