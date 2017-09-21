/**
 * 
 */
package dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * TON DUC THANG UNIVERSITY
 * @author PHAN VAN HUNG
 * @Instructors Assoc. Prof. LE ANH CUONG 
 */
public class Provider {
	 public static List<String> ReadFile(String fileName)
    {
        List<String> list = new LinkedList<>();
        try
        {
       	FileInputStream fis = new FileInputStream(fileName);
       	InputStreamReader isr = new InputStreamReader(fis,"utf-8");
       	BufferedReader br = new BufferedReader(isr);
       	String line ="";
       	while((line=br.readLine())!=null){
       		list.add(line);
       		}
       	br.close();
       	isr.close();
       	fis.close();
       	return list;
        }
        catch (Exception e)
        {
            return list;
        }

    }
	 public static HashSet<String> ReadFileHS(String fileName)
	    {
	        HashSet<String> list = new HashSet();
	        try
	        {
	       	FileInputStream fis = new FileInputStream(fileName);
	       	InputStreamReader isr = new InputStreamReader(fis,"utf-8");
	       	BufferedReader br = new BufferedReader(isr);
	       	String line ="";
	       	while((line=br.readLine())!=null){
	       		list.add(line);
	       		}
	       	br.close();
	       	isr.close();
	       	fis.close();
	       	return list;
	        }
	        catch (Exception e)
	        {
	            return list;
	        }

	    }
    public static boolean WriteFile(List<String> data, String filePath)
    {
        try
        {      	
       	 FileOutputStream fos = new FileOutputStream(filePath);
       	 OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
       	 for(String line :data){
       		 osw.write(line+"\n");
       	 }
           osw.flush();
           fos.close();
            return true;
        }
        catch (Exception ex)
        {
            return false;

        }
    }
    public static boolean WriteFileHS(HashSet<String> data, String filePath)
    {
        try
        {      	
       	 FileOutputStream fos = new FileOutputStream(filePath);
       	 OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
       	 for(String line :data){
       		 osw.write(line+"\n");
       	 }
           osw.flush();
           fos.close();
            return true;
        }
        catch (Exception ex)
        {
            return false;

        }
    }
    public static boolean WriteFileDL(List<Double> data, String filePath)
    {
        try
        {      	
       	 FileOutputStream fos = new FileOutputStream(filePath);
       	 OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
       	 for(double line :data){
       		 osw.write(line+"\n");
       	 }
           osw.flush();
           fos.close();
            return true;
        }
        catch (Exception ex)
        {
            return false;

        }
    }
    public static boolean WriteFileSI(HashMap<String, Integer> data, String filePath)
    {
   	 try
        {        	
       	 FileOutputStream fos = new FileOutputStream(filePath);
       	 OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
       	 Set set = data.keySet();
       	 Iterator i =set.iterator();
       	 String key="";
       	 while(i.hasNext()){
       		 key=(String)i.next();
       		 osw.write(key+" "+data.get(key)+"\n");
       	 }
           osw.flush();
           fos.close();
            return true;
        }
        catch (Exception ex)
        {
            return false;

        }
    }
    public static boolean WriteFileSD(HashMap<String, Double> data, String filePath)
    {
   	 try
        {        	
       	 FileOutputStream fos = new FileOutputStream(filePath);
       	 OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
       	 Set set = data.keySet();
       	 Iterator i =set.iterator();
       	 String key="";
       	 while(i.hasNext()){
       		 key=(String)i.next();
       		 osw.write(key+" "+data.get(key)+"\n");
       	 }
           osw.flush();
           fos.close();
            return true;
        }
        catch (Exception ex)
        {
            return false;

        }
    }
    public static boolean WriteFileSS(HashMap<String, String> data, String filePath)
    {
   	 try
        {        	
       	 FileOutputStream fos = new FileOutputStream(filePath);
       	 OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
       	 Set set = data.keySet();
       	 Iterator i =set.iterator();
       	 String key="";
       	 while(i.hasNext()){
       		 key=(String)i.next();
       		 osw.write(key+" "+data.get(key)+"\n");
       	 }
           osw.flush();
           fos.close();
            return true;
        }
        catch (Exception ex)
        {
            return false;

        }
    }
    public static boolean WriteFileSLS(HashMap<String,List<String>> data, String filePath)
    {
   	 try
        {        	
       	 FileOutputStream fos = new FileOutputStream(filePath);
       	 OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
       	 for(Entry<String, List<String>> entry:data.entrySet()){      	
       		 osw.write(entry.getKey());
       		 for(String w :data.get(entry.getKey())){
       			 osw.write("||"+w);
       		 }
       		 osw.write("\n");
       	 }       	 
           osw.flush();
           fos.close();
            return true;
        }
        catch (Exception ex)
        {
            return false;

        }
    }
    public static boolean WriteFileSHS(HashMap<String,HashSet<String>> data, String filePath)
    {
   	 try
        {        	
       	 FileOutputStream fos = new FileOutputStream(filePath);
       	 OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
       	 for(Entry<String, HashSet<String>> entry:data.entrySet()){      	
       		 osw.write(entry.getKey());
       		 for(String w :data.get(entry.getKey())){
       			 osw.write("||"+w);
       		 }
       		 osw.write("\n");
       	 }       	 
           osw.flush();
           fos.close();
            return true;
        }
        catch (Exception ex)
        {
            return false;

        }
    }
    public static void WriteFile(String filePath, HashMap<String,Integer> hsmap) throws IOException {
		List listItem = new LinkedList(hsmap.entrySet());
		extracted(listItem);
		FileOutputStream fi = new FileOutputStream(filePath);
		OutputStreamWriter osw = new OutputStreamWriter(fi, "utf-8");
		String key = "";
		Integer value = 0;
		for (Iterator i = listItem.iterator(); i.hasNext();) {
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) i.next();
			key = entry.getKey();
			value = entry.getValue();
			osw.write(key + "," + value.toString() + "\n");
		}
		osw.flush();
		fi.close();
	}

	private static void extracted(List listItem) {
		Collections.sort(listItem, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				int value1 = ((Map.Entry<String, Integer>) o1).getValue();
				int value2 = ((Map.Entry<String, Integer>) o2).getValue();
				if (value1 < value2)
					return 1;
				else if (value1 > value2)
					return -1;
				return 0;
			}
		});
	} 
	public static void SortEntry_String_Double(List listItem) {
		Collections.sort(listItem, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				double value1 = ((Map.Entry<String, Double>) o1).getValue();
				double value2 = ((Map.Entry<String, Double>) o2).getValue();
				if (value1 < value2)
					return 1;
				else if (value1 > value2)
					return -1;
				return 0;
			}
		});
	} 
}