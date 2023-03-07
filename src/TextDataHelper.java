import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextDataHelper {
    private List<String[]> data;
    private String[] labels;
    private String[] headers;
    private String file;
    private ListHelper d = new ListHelper();


    public TextDataHelper() {
    }
    
    public String getOnlyASCII(String raw) {
        Pattern asciiPattern = Pattern.compile("\\p{ASCII}*$");
        Matcher matcher = asciiPattern.matcher(raw);
        String asciiString = null;
        if (matcher.find()) {
            asciiString = matcher.group();
        }
        return asciiString;
    }
    
    public String getSomewhatOnlySimpleSymbols(String raw) {
    	String regex="[^a-zA-Z0-9\\<\\>\\[\\]\\(\\)\\.\\_\\-\\s]";
    	return raw.replaceAll(regex, "");
    }
    
    
    public String getRemovedCurlyBrackets(String raw) {
    	String regex="\\{.*?\\}";
    	return raw.replaceAll(regex, "");
    }
    
    public String getRemovedBrackets(String raw) {
    	raw=getRemovedCurlyBrackets(raw);
    	raw=getRemovedSquareBrackets(raw);
    	raw=getRemovedBowBrackets(raw);
    	raw=getRemovedAngleBrackets(raw);
    	return raw;
    }
    
    public String getRemovedSquareBrackets(String raw) {
    	String regex="\\[.*?\\]";
    	return raw.replaceAll(regex, "");
    }
    public String getRemovedBowBrackets(String raw) {
    	String regex="\\(.*?\\)";
    	return raw.replaceAll(regex, "");
    }
    public String getRemovedAngleBrackets(String raw) {
    	String regex="\\<.*?\\>";
    	return raw.replaceAll(regex, "");
    }

    private void loadList() {
	List<String> list = fromFile(file);
	headers = list.get(0).split(",");
	list.remove(0);

	for (int i = 0; i < list.size(); i++) {
	    String cur = list.get(i);
	    String[] instance = cur.split(",");
	    data.add(instance);
	}
    }

    public double parse(String in) {
	return Double.parseDouble(in);
    }

    public double[] parseArray(String[] toParse) {
	double[] parsed = new double[toParse.length];

	for (int i = 0; i < parsed.length; i++) {
	    parsed[i] = parse(toParse[i]);
	}
	return parsed;
    }

    /**
     * @param List<String>
     * @param String
     *            path
     */
    public void toFile(List<String> list, String path) {
	try {
	    FileWriter x = new FileWriter(path);
	    PrintWriter y = new PrintWriter(x);
	    for (int i = 0; i < list.size(); i++) {
		y.print(list.get(i));
		// this prevents printing last line as empty
		if (i < list.size() - 1) {
		    y.print("\r");
		}
	    }
	    x.close();
	    y.close();

	} catch (FileNotFoundException e) {
	    new Err("toFile FileNotFoundException : " + e.toString());
	} catch (IOException e) {
	    new Err("toFile IOEException : " + e.toString());
	}
    }

    /**
     * @return List<String>
     */
    public List<String> fromFile(String path) {
	List<String> lines = new ArrayList<String>();
	try {
	    FileReader x = new FileReader(path);
	    BufferedReader y = new BufferedReader(x);
	    lines.add(y.readLine());
	    String str;
	    while ((str = y.readLine()) != null) {
		lines.add(str);
	    }
	    x.close();
	    y.close();

	} catch (EOFException e) {
	    new Err("\n" + "fromFile EOFE : " + e.toString());
	} catch (FileNotFoundException e) {
	    new Err("\n" + "fromFile FileNotFoundException : " + e.toString());
	} catch (IOException e) {
	    new Err("\n" + "fromFile IOException : " + e.toString());
	}
	return lines;
    }

    /**
     * @return String manipulation
     */
    public String join(String[] str, String sym) {
	String data = "";
	for (int i = 0; i < str.length; i++) {
	    data += str[i];
	    if (i < str.length - 1) {
		data += sym;
	    }
	}
	return data;
    }

    public String[] getLabels() {
	ListHelper l = new ListHelper();
	labels = l.uniques(labels);
	return labels;
    }

    public void setLabels(String[] labels) {
	this.labels = labels;
    }

    public String[] getHeaders() {
	return headers;
    }

    public void setHeaders(String[] headers) {
	this.headers = headers;
    }

    public String getFile() {
	return file;
    }

    public void setFile(String file) {
	this.file = file;
    }

    public void setData(List<String[]> data) {
	this.data = data;
    }
}
