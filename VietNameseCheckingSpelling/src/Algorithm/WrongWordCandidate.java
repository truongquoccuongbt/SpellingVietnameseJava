package Algorithm;

import java.util.HashMap;
import java.util.HashSet;

public class WrongWordCandidate {
	private static WrongWordCandidate instance = new WrongWordCandidate();
	
	public static WrongWordCandidate GetInstance() {
		return instance;
	}
	
	private WrongWordCandidate() {
		
	}
	
	/**
	 * Táº¡o candidate dá»±a trĂªn tá»« Ä‘iá»ƒn tá»« ghĂ©p vĂ  ngá»¯ cáº£nh
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
		//giá»¯ cáº·p <candidate, Ä‘iá»ƒm> Ä‘á»ƒ so sĂ¡nh
		HashMap<String, Double> candidateWithScore = new HashMap<>();
		//giá»¯ cáº·p <candidate, Ä‘iá»ƒm> vá»›i nhá»¯ng candidate lĂ  tá»« ghĂ©p 3 Ă¢m liĂªn tiáº¿p
		HashMap<String, Double> prioritizedCandidatesWithScore = new HashMap<>();
		//candidate chÆ°a chá»�n lá»�c dá»±a vĂ o sá»‘ Ä‘iá»ƒm
		HashSet<String> hSetCandidate = new HashSet<>();
		
//		hSetCandidate.addAll(Candidate.GetInstance())
		return result;
	}
}
