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
	
	/**
	 * dùng để khởi tạo bộ ngram, và sinh bộ xác suất
	 */
	
}
