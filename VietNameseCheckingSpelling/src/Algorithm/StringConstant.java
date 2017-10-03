package Algorithm;
public class StringConstant {
	private static StringConstant instance = new StringConstant();
	
	public static StringConstant GetInstance() {
		return instance;
	}
	
	public static final int MAX_KEYBOARD_ROW = 3;
	public static final int MAX_KEYBOARD_COL = 10;
	public static final int MAXGROUP_REGION_CONFUSED = 11;
	public static final int MAXCASE_REGION_CONFUSED = 4;
	public static final int QUESTION_MASK = 2;
	public static final int TILDE = 3;
	public static final int MAX_VOWEL_NO = 12;
	public static final int MAX_SIGN_NO = 5;
	public static final int MAXGROUP_VNCHARMATRIX = 6;
	public static final int MAXCASE_VNCHARMATRIX = 3;
	public char[][] keyBoardMatrix_LowerCase = new char[][] {
		{'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p' },
        {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k' ,'l', ';' },
        {'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/' }
	};
	
	public char[][] keyBoardMatrix_UperCase = new char[][] {
		{'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P' },
        {'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K' ,'L', ';' },
        {'Z', 'X', 'C', 'V', 'B', 'N', 'M', ',', '.', '/' }
	};
	
	public char[] vnAlphabetArr_LowerCase = new char[] {
			 'a', 'ă', 'â', 'b', 'c', 'd', 'đ', 'e', 'ê', 'g', 'h', 'i',
	          'k', 'l', 'm', 'n', 'o','ô', 'ơ', 'p','q', 'r', 's', 't',
	           'u','ư', 'v', 'x', 'y'
	};
	
	public char[] vnAlphabetArr_UpperCase = new char[] {
			'A', 'Ă', 'Â', 'B', 'C', 'D', 'Đ', 'E', 'Ê', 'G', 'H', 'I',
            'K', 'L', 'M', 'N', 'O','Ô', 'Ơ', 'P','Q', 'R', 'S', 'T',
            'U','Ư', 'V', 'X', 'Y'			
	};
	
	// nguyên âm
	public char[] VNVowelArr_LowerCase = new char[] {
			'a', 'ă', 'â', 'e', 'ê',  'o','ô', 'ơ', 'i','u','ư', 'y'
	};
	
	public char[] VNVowelArr_UpperCase = new char[] {
			'A', 'Ă', 'Â', 'E', 'Ê', 'O','Ô', 'Ơ', 'I', 'U','Ư', 'Y'
	};
	
	public char[] VNVowelWithSignArr_LowerCase = new char[] {
			'á', 'ắ', 'ấ', 'é', 'ế',
            'ó','ố', 'ớ', 'í',
            'ú','ứ', 'ý', // thanh sắc
            'à', 'ằ', 'ầ', 'è', 'ề',
            'ò','ồ', 'ờ','ì',
            'ù','ừ', 'ỳ', // thanh huyền
            'ả', 'ẳ', 'ẩ', 'ẻ', 'ể',
            'ỏ','ổ', 'ở','ỉ',
            'ủ','ử', 'ỷ', // thanh hỏi
            'ã', 'ẵ', 'ẫ', 'ẽ', 'ễ',
            'õ','ỗ', 'ỡ','ĩ',
            'ũ','ữ', 'ỹ', // thanh ngã
            'ạ', 'ặ', 'ậ', 'ẹ', 'ệ',
            'ọ','ộ', 'ợ','ị',
            'ụ','ự', 'ỵ' // thanh nặng
	};
	
	public String source = "àảãáạằẳẵắặầẩẫấậèẻẽéẹềểễếệìỉĩíịòỏõóọồổỗốộờởỡớợùủũúụừửữứựỳỷỹýỵ";
	public String dest = "aaaaaăăăăăâââââeeeeeêêêêêiiiiioooooôôôôôơơơơơuuuuuưưưưưyyyyy";
	public String vnSign = "frxsj";
	public char[]	vnVowelWithSignArr_UpperCase = new char[] {
			'Á', 'Ắ', 'Ấ', 'É', 'Ế','Ó','Ố', 'Ớ', 'Í','Ú','Ứ', 'Ý', // thanh sắc
            'À', 'Ằ', 'Ầ', 'È', 'Ề', 'Ò','Ồ', 'Ờ','Ì','Ù','Ừ', 'Ỳ', // thanh huyền
            'Ả', 'Ẳ', 'Ẩ', 'Ẻ', 'Ể','Ỏ','Ổ', 'Ở', 'Ỉ','Ủ','Ử', 'Ỷ', // thanh hỏi
            'Ã', 'Ẵ', 'Ẫ', 'Ẽ', 'Ễ','Õ','Ỗ', 'Ỡ', 'Ĩ','Ũ','Ữ', 'Ỹ', // thanh ngã
            'Ạ', 'Ặ', 'Ậ', 'Ẹ', 'Ệ','Ọ','Ộ', 'Ợ', 'Ị','Ụ','Ự', 'Ỵ' // thanh nặng
	};
	
	public char[][] vnVowelWithSignMatrix_LowerCase = new char[][] {
		 { 'á', 'ắ', 'ấ', 'é', 'ế','ó','ố', 'ớ', 'í','ú','ứ', 'ý'}, // thanh sắc
         { 'à', 'ằ', 'ầ', 'è', 'ề','ò','ồ', 'ờ', 'ì','ù','ừ', 'ỳ' }, // thanh huyền
         { 'ả', 'ẳ', 'ẩ', 'ẻ', 'ể', 'ỏ','ổ', 'ở', 'ỉ', 'ủ','ử', 'ỷ' }, // thanh hỏi
         { 'ã', 'ẵ', 'ẫ', 'ẽ', 'ễ', 'õ','ỗ', 'ỡ', 'ĩ','ũ','ữ', 'ỹ' }, // thanh ngã
         { 'ạ', 'ặ', 'ậ', 'ẹ', 'ệ',  'ọ','ộ', 'ợ','ị', 'ụ','ự', 'ỵ' } // thanh nặng
	};
	
	public char[][] vnVowelWithSignMatrix_UpperCase = new char[][] {
		 { 'Á', 'Ắ', 'Ấ', 'É', 'Ế','Ó','Ố', 'Ớ', 'Í','Ú','Ứ', 'Ý'}, // thanh sắc
         { 'À', 'Ằ', 'Ầ', 'È', 'Ề','Ò','Ồ', 'Ờ', 'Ì','Ù','Ừ', 'Ỳ' }, // thanh huyền
         { 'Ả', 'Ẳ', 'Ẩ', 'Ẻ', 'Ể','Ỏ','Ổ', 'Ở', 'Ỉ','Ủ','Ử', 'Ỷ' }, // thanh hỏi
         { 'Ã', 'Ẵ', 'Ẫ', 'Ẽ', 'Ễ','Õ','Ỗ', 'Ỡ', 'Ĩ','Ũ','Ữ', 'Ỹ' }, // thanh ngã
         { 'Ạ', 'Ặ', 'Ậ', 'Ẹ', 'Ệ', 'Ọ','Ộ', 'Ợ','Ị','Ụ','Ự', 'Ỵ' } // thanh nặng
	};
	
	// nguyên âm mang dấu phụ
	public char[] subVnVowelArr_LowerCase = new char[] {
			'ă', 'â', 'ê','ô', 'ơ','ư'
	};
	
	public char[] subVnVowelArr_UpperCase = new char[] {
			'Ă', 'Â', 'Ê','Ô', 'Ơ','Ư'
	};
	
	public char[][] vnCharacterMatrix = new char[][] {
		 {'d','đ', ' '},
         {'e','ê', ' '},
         {'a','ă','â'},
         {'o','ô', 'ơ'},
         {'u','ư', ' '},
         {'ư', 'â', ' ' }
	};
	
	public String vnCharacter = "ăâêôơưđ";
	public String[] vnCharacter_Telex = new String[] {
			"aw", "aa", "ee", "oo", "ow", "uw", "dd"
	};
	
	// phụ âm
	public String[] vnConsonantArr_LowerCase = new String[] {
			 "ngh","ng", "nh","ph", "th", "tr", "kh","b", "ch" ,"c","d", "đ","gi", "gh", "g",  "h", "k",
	            "l", "m", "n" ,  "qu", "r", "s"
	           , "t", "v", "x"
	};
	
	public String[] vnConsonantArr_UpperCase = new String[] {
			"NGH","NG", "NH","PH", "TH", "TR", "KH","B", "CH" ,"C","D", "Đ","GI", "GH", "G", "H", "K",
            "L", "M", "N" ,  "QU", "R", "S"
           , "T", "V", "X"
	};
	
	public String[][] vnRegion_Confused_Matrix_LowerCase = new String[][] {
		{"i", "y", "",""},
        {"s", "x", "",""},
        {"l", "n", "",""},
        {"n", "ng", "nh",""},
        {"d", "gi" ,"v","r"},
        {"ch", "tr", "",""},
        {"t", "c", "", "" },
        {"u", "uô", "", "" },
        {"g", "gh", "", "" },
        {"â", "uâ", "", "" },
        {"ao", "au", "âu", "" },
        {"v", "qu", "", "" },
        {"i", "iê", "", "" },
        {"i", "uy", "", "" },
        {"o", "oa", "", "" }
	};
	
	public String[][] vnRegion_Confused_Matrix_UpderCase =new String[][] {
		{"S", "X", "",""},
        {"L", "N", "",""},
        {"V","R", "D", "GI" },
        {"CH", "TR", "",""}
	};
	
	public String patternMiddleSymbol = "[-|/|\\|>|<|\\[|\\]|,|\"|(|)|“|”]";
	public String patternEndMiddleSymbol = "[”|,|)]";
    public String patternEndSentenceCharacter = "[.!?;:…]";
    
    public String patternCheckSpecialChar = ".*[-|\\/|\\|>|<|\\[|\\]|\"|(|)|“|”|@|#|$|%|^|&|\\*|\\d|\\W].*";
    public String patternHasWord = "[\\w]";
    
    public String[] vnAcronym = new String[] {
    		 "XHCN", "CNXH", "ĐCS", "CHXHCNVN", "MTDTGPMNVN", "QDND","QLVNCH","VNQDĐ","VNQDD","VNCH","VNDCCH",
             "ĐH","TS", "PGS", "CLB"
    };
    
    public String patternSignSentence = "[-|/|\\|>|<|\\[|\\]|,|\"|(|)|“|”|.!?;:…]";
    public String patternCheckWord = "[-|\\/|\\|>|<|\\[|\\]|,|\"|(|)|“|”|@|#|$|%|^|&|\\*|\\d]";

    public String patternSPEC = "[-|\\/|\\|\\[|\\]||@|#|$|%|^|&|\\*|\\d|]";
    public String patternOPEN = "[(|“]";
    public String patternCLOSE = "[,|)|”|.!?;:…]";
    
}
