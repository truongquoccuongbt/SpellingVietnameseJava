package Algorithm;

import java.util.HashMap;
import java.util.HashSet;

public class RightWordCandidate {
	private static RightWordCandidate instance = new RightWordCandidate();
	
	private RightWordCandidate() {
	}
	
	public static RightWordCandidate GetInstance() {
		return instance;
	}
	
	/**
	 * Kiá»ƒm tra 1 tá»« Ä‘Ăºng Ă¢m tiáº¿t tiáº¿ng Viá»‡t cĂ³ há»£p ngá»¯ cáº£nh hay khĂ´ng
	 * @param prepre
	 * @param pre
	 * @param token
	 * @param next
	 * @param nextnext
	 */
	public boolean CheckRightWord(Context context) {
		return true;
	}
	
	/**
	 * táº¡o nhá»¯ng candidate dá»±a trĂªn ngá»¯ cáº£nh vĂ  Ä‘á»™ tÆ°Æ¡ng tá»±
	 * @param prepre
	 * @param pre
	 * @param token
	 * @param next
	 * @param nextnext
	 * @param isMajuscule
	 */
	public HashSet<String> CreateCandidate(Context context) {
		HashSet<String> result = new HashSet<>();
		// giá»¯ cáº·p <candidate, Ä‘iá»ƒm> Ä‘á»ƒ so sĂ¡nh
		HashMap<String, Double> candidateWithScore = new HashMap<>();
		// giá»¯ cáº·p <candidate, Ä‘iá»ƒm> vá»›i nhá»¯ng candidate lĂ  tá»« ghĂ©p 3 Ă¢m tiáº¿t
		HashMap<String, Double> prioritizedCandidatesWithScore = new HashMap<>();
		// candidate chÆ°a Ä‘Æ°á»£c chá»�n lá»�c dá»±a vĂ o sá»‘ Ä‘iá»ƒm
		HashSet<String> hSetCandidate = new HashSet<>();
//		hSetCandidate.addAll(Candidate.GetInstance().)
		return result;
	}
	
	
}
