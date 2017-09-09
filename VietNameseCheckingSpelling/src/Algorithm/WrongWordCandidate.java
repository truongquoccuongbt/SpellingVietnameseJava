package Algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.naming.spi.DirStateFactory.Result;

public class WrongWordCandidate {
	private static WrongWordCandidate instance = new WrongWordCandidate();
	
	public static WrongWordCandidate GetInstance() {
		return instance;
	}
	
	private WrongWordCandidate() {
		
	}
	
	/**
	 * tạo candidate dựa trên từ điển từ ghép và ngữ cảnh
	 * 
	 * @param prepre
	 * @param pre
	 * @param token
	 * @param next
	 * @param nextnext
	 * @param isMajuscule
	 */
	public HashSet<String> CreateCandidate(Context context) {
		HashSet<String> result = new HashSet<>();
		// giữ cặp <candidate, điểm> để so sánh
		HashMap<String, Double> candidateWithScore = new HashMap<>();
		// giữ cặp <candidate, điểm> với những candidate là từ ghép 3 âm tiết.
		HashMap<String, Double> prioritizedCandidatesWithScore = new HashMap<>();
		// candidate chưa chọn lọc dựa vào số điểm
		HashSet<String> hSetCandidate = new HashSet<>();
		
		hSetCandidate = UnionWith(hSetCandidate, Candidate.GetInstance().CreateCandidateByNgram_NoUseLamdaExp(context));
		hSetCandidate = UnionWith(hSetCandidate, Candidate.GetInstance().CreateCandByCompoundWord(context));
		// giá trị lamda có được do thống kê
		double lamda1 = 0.1, lamda2 = 0.3, lamda3 = 0.6, score = 0;
		// Dictionary
		double D = 0;
		// Language
		double L = 0.0;
		// Similarity
		double S = 0;
		for (String candidate : hSetCandidate) {
			S = Candidate.GetInstance().CalScore_Similarity(context.getToken(), candidate);
			if (S >= Candidate.GetInstance().LIM_SIMILARITY()) {
				D = Candidate.GetInstance().CalScore_CompoundWord(context, candidate);
				L = Candidate.GetInstance().CalScore_Ngram(context, candidate);
				
				score = lamda1 * D + lamda2 * L + lamda3 * S;
				if (score > Candidate.GetInstance().MAX_SCORE()) {
					score = Candidate.GetInstance().MAX_SCORE();
				}
				// ngưỡng để chọn candidate có được do thống kê
				if (score >= Candidate.GetInstance().LIM_SCORE()) {
					if (candidateWithScore.size() < 5) {
						candidateWithScore.put(candidate, score);
						candidateWithScore = Candidate.GetInstance().SordDict(candidateWithScore);
					}
					else if (candidateWithScore.get(GetLastKeyInCandidateScore(candidateWithScore)) < score) {
						candidateWithScore.remove(GetLastKeyInCandidateScore(candidateWithScore));
						candidateWithScore.put(candidate, score);
						candidateWithScore = Candidate.GetInstance().SordDict(candidateWithScore);
					}
				}
			}
		}
		// nếu có từ ghép có 3 âm tiết
		if (prioritizedCandidatesWithScore.size() > 0) {
			for (String key : prioritizedCandidatesWithScore.keySet()) {
				result.add(key);
			}
		}
		else {
			for (String key : candidateWithScore.keySet()) {
				result.add(key);
			}
		}
		return result;
	}
	
	private HashSet<String> UnionWith(HashSet<String> a, HashSet<String> b) {
		HashSet<String> c = new HashSet<>(a);
		String tmp;
		for (Iterator<String> it = b.iterator(); it.hasNext();) {
			tmp = it.next();
			if (!a.contains(tmp)) {
				c.add(tmp);
			}
		}
		return c;
	}
	
	private String GetLastKeyInCandidateScore(HashMap<String, Double> candidateWithScore) {
		String key = "";
		for (String str : candidateWithScore.keySet()) {
			key = str;
		}
		return key;
	}
}
