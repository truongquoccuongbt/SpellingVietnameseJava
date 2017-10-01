/**
 * 
 */
package dictionary;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer.ConditionObject;
import java.util.logging.Logger;

import Tokenizer.StringConst;
import Tokenizer.Tokenizer;

/**
 * TON DUC THANG UNIVERSITY
 * 
 * @author PHAN VAN HUNG
 * @Instructors Assoc. Prof. LE ANH CUONG
 */
public class Dictionary {
	public static String getDataDir() {
		try {
			String path = new File(
					Dictionary.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getPath();
			File ftmp = new File(path + "/DataBase");
			// System.out.println(path);
			if (ftmp.exists()) {
				// System.out.println(ftmp.getPath());
				return ftmp.getPath() + '/';
			}
			path = new File(path).getParent();
			ftmp = new File(path + "/DataBase");
			if (ftmp.exists()) {
				// System.out.println(ftmp.getPath());
				return ftmp.getPath() + '/';
			}
			path = new File(path).getParent();
			ftmp = new File(path + "/DataBase");
			if (ftmp.exists()) {
				// System.out.println(ftmp.getPath());
				return ftmp.getPath() + '/';
			}
			return path + '/';
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String dir = getDataDir();
	public static String corpusPath = dir + "corpus/";
	public static String testPath = dir + "tests/";
	public static String word_TagPath = dir + "wordTag.dic";
	public static String featurePath = dir + "feature.dic";
	public static String historyPath = dir + "history.dic";
	public static String corpusHistoryPath = dir + "corpusHistory/";
	public static String corpusFeaturePath = dir + "corpusFeature/";
	public static String hmpWordsPath = dir + "hmpWords.dic";
	public static String hmpWordTagsPath = dir + "hmpWordTags.dic";
	public static String hmpFeature_LambdasPath = dir + "hmpFeature_Lambdas.dic";
	public static String hmpFeature_HistoryPath = dir + "hmpFeature_History.dic";
	public static String hmpHistory_FeaturePath = dir + "hmpHistory_Feature.dic";
	public static HashMap<String, String> hsmapWord_Tag = null;
	private static Logger _logger = null;
	public static HashSet<String> abbreviation = null;
	public static HashSet<String> exception = null;
	public static HashMap<String, String> dictionaryAlphabet = null;
	public static HashMap<String, String> dictionaryLN = null;
	public static HashMap<String, Integer> normalizedDocs = null;
	public static HashMap<String, String> dictionaryName = null;
	public static HashSet<String> erroes = null;
	public static String abb_Path = dir + "dictionary/abbreviation.dic";
	public static String exc_Path = dir + "dictionary/exception.dic";
	public static String dic_Path = dir + "dictionary/vn-dic.dic";
	public static String dic_Name_Path = dir + "dictionary/dic-Name.dic";
	public static String loc_Path = dir + "dictionary/vn-loc.dic";
	public static String n_gram1_Path = dir + "N_gram1.txt";
	public static String n_gram2_Path = dir + "N_gram2.txt";
	public static String n_gram3_Path = dir + "N_gram3.txt";
	public static String Name_n_gram1_Path = dir + "Name_N_gram1.txt";
	public static String Name_n_gram2_Path = dir + "Name_N_gram2.txt";
	public static String Name_sum_key_any_predic_key_Path = dir + "Name_SumKey.txt";
	public static String sum_key_any_predic_key_Path = dir + "SumKey_AnyPredictKey.txt";
	public static String normaliezd_Path = dir + "normalizedDocs/";
	public static String count_n_gram_Path = dir + "Count N Gram/";
	public static String text_Path = dir + "TEXT/";
	/*
	 * EX : <KEY>FORMAT_DIC_HM<NUMBER>
	 */
	public static final String FORMAT_DIC_HM = " ";
	/*
	 * EX : <KEY1>FORMAT_DIC_FH<KEY2>...
	 */
	public final static String FORMAT_DIC_FH = "|";
	public final static String FORMAT_CORPUS = "/";
	public final static String EMPTY = "N/A";

	public static String GetFormatRegex(String key) {
		return "[" + key + "]";
	}

	public static Logger getLogger() {
		if (_logger == null)
			_logger = Logger.getLogger("mylog");
		return _logger;
	}

	public static void initDictionary() {

	}

	public static HashMap<String, String> GetWord_Tag() {
		File file = new File(word_TagPath);
		hsmapWord_Tag = new HashMap<>();
		if (file.isFile()) {
			System.out.println("Loading.......");
			List<String> data = Provider.ReadFile(word_TagPath);
			String arr[] = null;
			for (String line : data) {
				String value = "";
				arr = line.split(FORMAT_DIC_HM);
				for (int i = 1; i < arr.length; i++) {
					value += arr[i];
				}
				hsmapWord_Tag.put(arr[0], value);
			}
		} else {
			file = new File(corpusPath);
			String[] files = file.list();
			for (String path : files) {
				List<String> data = Provider.ReadFile(corpusPath + path);
				for (String line : data) {
					String[] arr = line.split(" ");
					for (String tokens : arr) {
						// System.out.println(tokens+"---------------------");
						String[] words = tokens.split(Dictionary.FORMAT_CORPUS);
						words[0] = words[0].toLowerCase();
						if (hsmapWord_Tag.containsKey(words[0])) {
							String tags = hsmapWord_Tag.get(words[0]);
							hsmapWord_Tag.remove(words[0]);
							// System.out.println("tag :"+tags);
							// System.out.println(words[1]);
							String[] arrTags = tags.split(Dictionary.FORMAT_DIC_HM);
							boolean check = false;
							for (String tag : arrTags) {
								if (tag.equals(words[1]))
									check = true;
							}
							if (check == false)
								tags = FORMAT_DIC_HM + words[1];
							hsmapWord_Tag.put(words[0], tags);
						} else {
							hsmapWord_Tag.put(words[0], words[1]);
						}
					}
				}
			}
			Provider.WriteFileSS(hsmapWord_Tag, word_TagPath);
		}
		return hsmapWord_Tag;
	}

	public static HashMap<String, String> UpdateWord_Tag(String fileDictionary) {
		/*
		 * Format Dictionary : word,Tag,Tag,...
		 */
		System.out.println("Loading.......");
		List<String> data = Provider.ReadFile(fileDictionary);
		String arr[] = null;
		for (String line : data) {
			String value = "";
			arr = line.split("[,]");
			String key = arr[0].replace(" ", "_");
			if (hsmapWord_Tag.containsKey(key)) {
				value = hsmapWord_Tag.get(key);
				hsmapWord_Tag.remove(key);
				for (int i = 1; i < arr.length; i++) {
					if (value.indexOf(arr[i]) < 0)
						value += Dictionary.FORMAT_DIC_HM + arr[i];
				}
			} else {
				for (int i = 1; i < arr.length; i++) {
					value += Dictionary.FORMAT_DIC_HM + arr[i];
				}
			}
			hsmapWord_Tag.put(key, value);
		}
		// Provider.WriteFileSS(hsmapWord_Tag, word_TagPath);
		return hsmapWord_Tag;
	}

	public static void SetDir(String filePath) {
		abb_Path = filePath + "dictionary/abbreviation.dic";
		exc_Path = filePath + "dictionary/exception.dic";
		dic_Path = filePath + "dictionary/vn-dic.dic";
		dic_Name_Path = filePath + "dictionary/dic-Name.dic";
		loc_Path = filePath + "dictionary/vn-loc.dic";
		n_gram1_Path = filePath + "N_gram1.txt";
		n_gram2_Path = filePath + "N_gram2.txt";
		Name_n_gram1_Path = filePath + "Name_N_gram1.txt";
		Name_n_gram2_Path = filePath + "Name_N_gram2.txt";
		Name_sum_key_any_predic_key_Path = filePath + "Name_SumKey.txt";
		sum_key_any_predic_key_Path = filePath + "SumKey_AnyPredictKey.txt";
		normaliezd_Path = filePath + "normalizedDocs/";
		count_n_gram_Path = filePath + "Count N Gram/";
		text_Path = filePath + "TEXT/";
	}

	public static HashSet<String> GetAbbreviation() {
		if (abbreviation == null) {
			abbreviation = new HashSet<String>();
			List<String> abbreviationList;
			abbreviationList = Provider.ReadFile(abb_Path);

			for (String s : abbreviationList) {
				abbreviation.add(s);
			}
		}
		return abbreviation;
	}

	public static HashSet<String> GetException() {
		if (exception == null) {
			exception = new HashSet<String>();
			List<String> exceptionList;
			exceptionList = Provider.ReadFile(exc_Path);

			for (String s : exceptionList) {
				exception.add(s);
			}
		}
		return exception;
	}

	public static HashMap<String, String> GetDictionaryAlphabet() {
		dictionaryAlphabet = new HashMap<>();
		List<String> data = Provider.ReadFile(dic_Path);
		String arr[] = null;
		for (String line : data) {
			String value = "";
			arr = line.split(StringConst.COMMA);
			for (int i = 1; i < arr.length; i++) {
				value += arr[i];
			}
			dictionaryAlphabet.put(arr[0], value);
		}
		return dictionaryAlphabet;
	}

	public static HashMap<String, String> UpdateDictionaryAlphabet(String filePath) {
		List<String> data = Provider.ReadFile(filePath);
		String arr[] = null;
		for (String line : data) {
			String value = "";
			arr = line.split(StringConst.COMMA);
			for (int i = 1; i < arr.length; i++) {
				value += arr[i];
			}
			if (!dictionaryAlphabet.containsKey(arr[0]))
				dictionaryAlphabet.put(arr[0], value);
		}
		return dictionaryAlphabet;
	}

	public static HashMap<String, String> GetDictionaryLN() {
		dictionaryLN = new HashMap<>();
		List<String> data = Provider.ReadFile(loc_Path);
		for (String line : data) {
			dictionaryLN.put(line.toLowerCase().trim(), "LOC");
		}
		return dictionaryLN;
	}

	public static HashMap<String, String> GetDictionaryName() {
		dictionaryName = new HashMap<>();
		List<String> data = Provider.ReadFile(dic_Name_Path);
		for (String line : data) {
			dictionaryName.put(line.toLowerCase(), "w");
		}
		return dictionaryName;
	}

//	public static HashMap<String, Integer> CreateN_Gram(N_gramType type) {
//		erroes = new HashSet<>();
//		// normalizedDocs = new HashMap<>();
//		normalizedDocs = N_Gram.CreateCountOfN_Gram(type);
//		if (dictionaryAlphabet == null)
//			GetDictionaryAlphabet();
//		if (type == N_gramType.BIGRAM_NEW) {
//			for (Entry<String, String> entry : dictionaryAlphabet.entrySet()) {
//				if (entry.getKey().split(" ").length == 2 && !normalizedDocs.containsKey(entry.getKey())) {
//					normalizedDocs.put(entry.getKey(), 0);
//				}
//			}
//			File file = new File(normaliezd_Path);
//			String[] filePaths = file.list();
//			int count = 0;
//			for (String filePath : filePaths) {
//				if(!filePath.contains(".segUET"))continue;
//				System.out.println("LINK :" + filePath);
//				HashSet<String>data=Provider.ReadFileHS(normaliezd_Path + filePath);
//				for (String line : data) {
//					try {
//						String [] tokens=line.split(" ");
//						for(String word:tokens){
//							String []words=word.split("_");
//							if(words.length==2){
//								String pharse = word.replace("_"," ");
//								if (normalizedDocs.containsKey(pharse)) {
//									count = (int) normalizedDocs.get(pharse) + 1;
//									normalizedDocs.remove(pharse);
//									normalizedDocs.put(pharse, count);
//									continue;
//								}
//							}
//						}
//					} catch (Exception ex) {
//						System.out.println("LINK :" + filePath + "ERROR" + ex.getMessage() + "line : " + line);
//						erroes.add(filePath);
//						continue;
//					}
//				}
//			}
//		} else if (type == N_gramType.UNIGRAM) {
//			for (Entry<String, String> entry : dictionaryAlphabet.entrySet()) {
//				if (entry.getKey().split(" ").length == 1 && !normalizedDocs.containsKey(entry.getKey())) {
//					normalizedDocs.put(entry.getKey(), 0);
//				}
//
//			}
//			File file = new File(normaliezd_Path);
//			String[] filePaths = file.list();
//			int count = 0;
//			for (String filePath : filePaths) {
//				System.out.println("LINK :" + filePath);
//				for (String line : Provider.ReadFileHS(normaliezd_Path + filePath)) {
//					try {
//						List<String> tokens = Tokenizer.RunTokenize(line);
//						for (int i = 0; i < tokens.size(); i++) {
//
//							String pharse = tokens.get(i);
//							if (normalizedDocs.containsKey(pharse)) {
//								count = (int) normalizedDocs.get(pharse) + 1;
//								normalizedDocs.remove(pharse);
//								normalizedDocs.put(pharse, count);
//								continue;
//							}
//						}
//					} catch (Exception ex) {
//						System.out.println("LINK :" + filePath + "ERROR" + ex.getMessage() + "line : " + line);
//						erroes.add(filePath);
//						continue;
//					}
//				}
//			}
//		} else if (type == N_gramType.BIGRAM) {
//			for (Entry<String, String> entry : dictionaryAlphabet.entrySet()) {
//				if (entry.getKey().split(" ").length == 2 && !normalizedDocs.containsKey(entry.getKey())) {
//					normalizedDocs.put(entry.getKey(), 0);
//				}
//			}
//			File file = new File(normaliezd_Path);
//			String[] filePaths = file.list();
//			int count = 0;
//			for (String filePath : filePaths) {
//				System.out.println("LINK :" + filePath);
//				for (String line : Provider.ReadFileHS(normaliezd_Path + filePath)) {
//					try {
//						List<String> tokens = Tokenizer.RunTokenize(line);
//						for (int i = 0; i < tokens.size() - 1; i++) {
//							String pharse = tokens.get(i) + StringConst.SPACE + tokens.get(i + 1);
//							if (normalizedDocs.containsKey(pharse)) {
//								count = (int) normalizedDocs.get(pharse) + 1;
//								normalizedDocs.remove(pharse);
//								normalizedDocs.put(pharse, count);
//								continue;
//							}
//						}
//					} catch (Exception ex) {
//						System.out.println("LINK :" + filePath + "ERROR" + ex.getMessage() + "line : " + line);
//						erroes.add(filePath);
//						continue;
//					}
//				}
//			}
//		}
//		/*
//		 * File file = new File(normaliezd_Path); String[] filePaths =
//		 * file.list(); int count = 0; for (String filePath : filePaths) {
//		 * System.out.println("LINK :" + filePath); for (String line :
//		 * Provider.ReadFileHS(normaliezd_Path + filePath)) { try { List<String>
//		 * tokens = Tokenizer.RunTokenize(line); for (int i = 0; i <
//		 * tokens.size() - 1; i++) {
//		 * 
//		 * String pharse = ""; switch (type) { case BIGRAM_NEW: { pharse =
//		 * tokens.get(i) + StringConst.SPACE + tokens.get(i + 1) +
//		 * StringConst.SPACE + tokens.get(i + 2); break; } case BIGRAM: { pharse
//		 * = tokens.get(i) + StringConst.SPACE + tokens.get(i + 1); break; }
//		 * case UNIGRAM: { pharse = tokens.get(i); ; break; } } if
//		 * (normalizedDocs.containsKey(pharse)) { count = (int)
//		 * normalizedDocs.get(pharse) + 1; normalizedDocs.remove(pharse);
//		 * normalizedDocs.put(pharse, count); continue; } } } catch (Exception
//		 * ex) { System.out.println("LINK :" + filePath + "ERROR" +
//		 * ex.getMessage() + "line : " + line); erroes.add(filePath); continue;
//		 * } } }
//		 */
//		return normalizedDocs;
//	}

}