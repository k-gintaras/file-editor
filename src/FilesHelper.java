import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class FilesHelper {

	public FilesHelper() {
	}

	public List<String> dir(String start) {
		File f = new File(start);
		List<String> names = new ArrayList<>();
		names = Arrays.asList(f.list());
		return names;
	}

	// http://stackoverflow.com/questions/7768071/how-to-delete-directory-content-in-java
	public void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}

	public List<File> findFiles(List<File> listToFill, File folder, String type) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					findFiles(listToFill, f, type);
				} else {
					String path = f.getAbsolutePath();
					if (path.endsWith(type)) {
						listToFill.add(f);
					}
				}
			}
		}
		return listToFill;
	}

	public List<File> findFiles(List<File> listToFill, File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					findFiles(listToFill, f);
				} else {
//					String path = f.getAbsolutePath();
					listToFill.add(f);
				}
			}
		}
		return listToFill;
	}

	public List<File> findFilesLike(List<File> listToFill, File folder, String similarTo) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					findFilesLike(listToFill, f, similarTo);
				} else {
					String path = f.getAbsolutePath();
					if (path.indexOf(similarTo) != -1) {
						listToFill.add(f);
					}
				}
			}
		}
		return listToFill;
	}

	public void deleteFolderNamed(String where, String named) {
		List<String> names = dir(where);
		String dir1 = where + "\\" + named;
		for (int i = 0; i < names.size(); i++) {
			String dir2 = where + "\\" + names.get(i);
			File f = new File(where + "\\" + names.get(i));
			if (f.isDirectory()) {
				if (dir1.equals(dir2)) {
					deleteFolder(f);
				}
			}
		}
	}

	public void deleteFoldersNamed(String where, List<String> named) {
		for (int i = 0; i < named.size(); i++) {
			deleteFolderNamed(where, named.get(i));
		}
	}

	public void deleteFoldersNamed(String where, String commaSeparatedNames) {
		String[] arr = commaSeparatedNames.split(",");
		for (int i = 0; i < arr.length; i++) {
			deleteFolderNamed(where, arr[i]);
		}
	}

	public void deleteFilesEnding(String where, String commaSeparatedNames) {
		String[] arr = commaSeparatedNames.split(",");
		for (int i = 0; i < arr.length; i++) {
			String dir = where + "\\" + arr[i];
			File f = new File(dir);
			if (f.exists()) {
				f.delete();
			}
		}
	}

	public void deleteFilesLike(String whereToSearch, String similarTo) {
		List<File> list = new ArrayList<File>();
		list = findFilesLike(list, new File(whereToSearch), similarTo);

		for (int i = 0; i < list.size(); i++) {
			File file = list.get(i);
			file.delete();
		}
	}

	public void findAndMoveFilesTo(String whereToMove, String whereToSearch, String type) {
		int sameCounter = 0;
		List<File> list = new ArrayList<File>();
		list = findFiles(list, new File(whereToSearch), type);
		for (int i = 0; i < list.size(); i++) {
			File file = list.get(i);
			boolean good = file.renameTo(new File(whereToMove + "\\" + file.getName()));
			if (!good) {
				file.renameTo(new File(whereToMove + "\\" + "(" + sameCounter + ")" + file.getName()));
				sameCounter++;
			}
		}
	}

	public boolean moveFileTo(String from, String to) {
		try {
			Files.move(Paths.get(from), Paths.get(to), StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException e) {
			System.out.println(e.toString());
			return false;
		}
	}
}
