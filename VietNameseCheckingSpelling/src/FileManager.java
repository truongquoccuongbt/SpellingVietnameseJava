import java.io.File;

public class FileManager {
	private static FileManager instance = new FileManager();
	private String Directory() {
		return new File("").getAbsolutePath();
	}
	
	public String SyllDict() {
		return Directory() + "/Resources/SyllableDictByViet39K.txt";
	}
	
	public String CompoundWordDict() {
		return Directory() + "/Resources/sortedCompoundWordDict.txt";
	}
	
	public String CompoundWordLinkedList() {
		return Directory() + "/Resources/sortedCompoundWordDict.txt";
	}
	
	public String UniGram() {
		return Directory() + "/Resources/filteredUni.txt";
	}
	
	public String BiGram() {
		return Directory() + "/Resources/filteredBi.txt";
	}
	
	public static FileManager Instance() {
		return instance;
	}
}
