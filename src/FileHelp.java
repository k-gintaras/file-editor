import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

/**
 * The MIT License (MIT) Copyright (c)
 * 
 * <2016><Gintaras Koncevicius>(@author Ubaby)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class FileHelp {
	private List<Vector> vectors;
	private List<String[]> data;
	private String[] labels;
	private String[] headers;
	private String file;
	private ListHelper d = new ListHelper();

	public FileHelp() {

	}

	public FileHelp(String file) {
		this.file = file;
		vectors = new ArrayList<Vector>();
		data = new ArrayList<String[]>();
		loadList();
		loadVectors();
	}

	public static String getRenamedPathName(File file, String newName) {
		String path = file.getAbsolutePath();
		String extension = getFileExtension(file);
		String oldName = getSimpleFileName(file);
		String folderName = file.getParent();
		return folderName + "\\" + newName + "." + extension;
	}

	public static String getSimpleFileName(File file) {
		String extension = "." + getFileExtension(file);
		String name = file.getName().replaceAll(extension, "");
		return name;
	}

	public static String getFileExtension(File file) {
		String extension = "";
		String fileName = file.getName();

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(i + 1);
		}
		return extension;
	}

	public void renameFile(String from, String to) {
		// File (or directory) with old name
		File file = new File(from);

		// File (or directory) with new name
		File file2 = new File(to);

//    	try {
//			if (file2.exists())
////			   throw new java.io.IOException("file exists");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		// Rename file (or directory)
		boolean success = false;
		if (!file2.exists()) {
			success = file.renameTo(file2);
		}
//		else {
////			// still a different type of rename
//			if(!from.equals(to)) {
//				// but it is just caps that differ
//				if(from.toLowerCase().equals(to.toLowerCase())) {
//					File normal=new File(to);
//					file2 = new File(to+".todo");
//					success = file.renameTo(file2);
//					success = file2.renameTo(normal);
//				}
//			}
//		}
//		try {
//			if (!success)
//				throw new java.io.IOException("file was not renamed");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	private void loadVectors() {
		// save the header column and then remove it
		int len = data.size();
		labels = d.getColumn(data, data.get(0).length - 1);
		List<String[]> noLabel = d.removeColumn(data, data.get(0).length - 1, data.get(0).length - 1);
		List<double[]> numbers = d.parseList(noLabel);

		for (int i = 0; i < len; i++) {
			double[] cur = numbers.get(i);
			Vector v = new Vector(cur, labels[i]);
			vectors.add(v);
		}
	}

	public void printData() {
		System.out.println(join(headers, ", "));
		for (int i = 0; i < vectors.size(); i++) {
			Vector cur = vectors.get(i);
			double[] vector = cur.values;
			for (int j = 0; j < vector.length; j++) {
				System.out.print(vector[j] + ",");
			}
			System.out.println("Label: " + cur.getNominalLabel());
		}
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
	 * @param String       path
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
			new Error("toFile FileNotFoundException : " + e.toString());
		} catch (IOException e) {
			new Error("toFile IOEException : " + e.toString());
		}
	}

	/**
	 * @param String
	 * @param String path
	 */
	public void toFile(String list, String path) {
		try {
			FileWriter x = new FileWriter(path);
			PrintWriter y = new PrintWriter(x);
			y.print(list);
			x.close();
			y.close();

		} catch (FileNotFoundException e) {
			new Error("toFile FileNotFoundException : " + e.toString());
		} catch (IOException e) {
			
			new Error("toFile IOEException : " + e.toString());
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
			e.printStackTrace();
			new Error("\n" + "fromFile EOFE : " + e.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			new Error("\n" + "fromFile FileNotFoundException : " + e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			new Error("\n" + "fromFile IOException : " + e.toString());
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

	public List<Vector> getData() {
		return vectors;
	}

	public List<Vector> getVectors() {
		return vectors;
	}

	public void setVectors(List<Vector> vectors) {
		this.vectors = vectors;
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

	public static String getCurrentDirectory() {
		try {
			return new File(FileHelp.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
		} catch (URISyntaxException e) {
			return "";
		}
	}

	public static String getDesktopDirectory() {
		FileSystemView filesys = FileSystemView.getFileSystemView();
		// File[] roots = filesys.getRoots();
		return filesys.getHomeDirectory().getAbsolutePath();
	}

	public static void createDirectoryOnDesktop(String name) {
		String desktop = getDesktopDirectory();
		createDirectory(desktop + "/" + name);
	}

	public static void createDirectory(String name) {
		File theDir = new File(name);
		if (!theDir.exists()) {
			theDir.mkdirs();
		}
	}

	public void setFile(String file) {
		this.file = file;
	}

	public ListHelper getD() {
		return d;
	}

	public void setD(ListHelper d) {
		this.d = d;
	}

	public void setData(List<String[]> data) {
		this.data = data;
	}

}
