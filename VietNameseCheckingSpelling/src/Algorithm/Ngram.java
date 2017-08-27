package Algorithm;

import java.util.HashMap;

public class Ngram {
	private int _sumUni = 0, _sumBi = 0;
	
	private HashMap<String, Integer> _uniAmount;
	private HashMap<String, Integer> _biAmount;
	
	public HashMap<String, Integer> get_uniAmount() {
		return _uniAmount;
	}
	public void set_uniAmount(HashMap<String, Integer> _uniAmount) {
		this._uniAmount = _uniAmount;
	}
	public HashMap<String, Integer> get_biAmount() {
		return _biAmount;
	}
	public void set_biAmount(HashMap<String, Integer> _biAmount) {
		this._biAmount = _biAmount;
	}
	
	public String START_STRING() {
		return "<s>";
	}
	
	public String END_STRING() {
		return "</s>";
	}
	
	private Ngram() {
		this._uniAmount = new HashMap<>();
		this._biAmount = new HashMap<>();
	}
	
	private static Ngram instance = new Ngram();
	
	public static Ngram GetInstance() {
		return instance;
	}
	
	public double CalBigram(String w1, String w2) {
		String key = w1 + " " + w2;
		int Cw1 = 0;
		int Cw1w2 = 0;
		double alpha = 0.1;
		if (_uniAmount.containsKey(w1.toLowerCase())) {
			Cw1 = _uniAmount.get(w1.toLowerCase());
		}
		if (_biAmount.containsKey(key.toLowerCase())) {
			Cw1w2 = _biAmount.get(key.toLowerCase());
		}
		double ret = (double)(Cw1w2 + alpha) / (Cw1 + _sumUni * alpha);
		return ret;
	}
	
}
