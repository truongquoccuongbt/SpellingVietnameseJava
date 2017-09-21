package Algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
	public ArrayList<String> CreateCandidate(Context context) {
		ArrayList<String> result = new ArrayList<>();
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
						//candidateWithScore = Candidate.GetInstance().SordDict(candidateWithScore);
						//this.candiate = Candidate.GetInstance().GetCandidate(candidateWithScore);
					}
					else if (candidateWithScore.get(GetLastKeyInCandidateScore(candidateWithScore)) < score) {
						candidateWithScore.remove(GetLastKeyInCandidateScore(candidateWithScore));
						candidateWithScore.put(candidate, score);
						//candidateWithScore = Candidate.GetInstance().SordDict(candidateWithScore);
						//this.candiate = Candidate.GetInstance().GetCandidate(candidateWithScore);
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
			LinkedHashMap<String, Double> tmp = SortCandidateWithScore(candidateWithScore);
			for (String key : tmp.keySet()) {
				result.add(key);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * sort hashmap
	 */
	private LinkedHashMap<String, Double> SortCandidateWithScore(HashMap<String, Double> candidateWithScore) {
		Set<Entry<String, Double>> entries = candidateWithScore.entrySet();
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
