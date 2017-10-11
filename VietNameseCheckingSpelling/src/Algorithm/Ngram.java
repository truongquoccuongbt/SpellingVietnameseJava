package Algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
	
	/**
	 *  Đọc dữ liệu của unigram: key và value (vị trí).
	 */
	private void ReadUni(String path) {
		try {
			FileInputStream fs = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			String line;
			String[] tmp;
			while ((line = br.readLine()) != null) {
				tmp = line.split(" ");
				_uniAmount.put(tmp[0], Integer.parseInt(tmp[2]));
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void ReadBiAmount(String path) {
		try {
			FileInputStream fs = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			String line, firstSyll, secondSyll;
			String[] str;
			while ((line = br.readLine()) != null) {
				str = line.split(" ");
				firstSyll = str[0];
				secondSyll = str[1];
				_biAmount.put(firstSyll + " " + secondSyll, Integer.parseInt(str[2]));
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void SumWordInCorpus() {
		for (String key : _uniAmount.keySet()) {
			_sumUni += this._uniAmount.get(key);
		}
		for (String key : _biAmount.keySet()) {
			_sumBi += this._biAmount.get(key);
		}
	}
	
	public void RunFirst() {
		ReadUni(new File("").getAbsolutePath() + "/Resources/filteredUni.txt");
		ReadBiAmount(new File("").getAbsolutePath() + "/Resources/filteredBi.txt");
		SumWordInCorpus();
	}
}
