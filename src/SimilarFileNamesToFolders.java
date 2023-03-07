import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTextArea;

public class SimilarFileNamesToFolders implements ActionListener {
	private static final String DIR_FROM_DEFAULT = "C:\\Users\\";
	String DIR_FROM = "Directory From";
	String DIR_TO = "Directory To";
	String CONTAINS_STR = " - ";
	String OUTPUT = "Result";

	private WindowBuilder w = new WindowBuilder();
	private ListHelper l = new ListHelper();
	private TextDataHelper txt = new TextDataHelper();
	private StringHelper sh = new StringHelper();
	private List<String[]> data;
	private FilesHelper fs = new FilesHelper();
	private FileHelp f = new FileHelp();

	public static void main(String[] args) {
		new SimilarFileNamesToFolders();
	}

	public SimilarFileNamesToFolders() {
		similarFileFixer();
//		fileNameFixer();
//		fileMover();
	}

	private void similarFileFixer() {
		DIR_FROM = "Directory to check.";

		w.initializeDisplay("Similar Files Organizer.", 1400, 800);
		w.getMainPanel().setBackground(new Color(100, 100, 100));

		// panels
		for (int i = 0; i < 4; i++) {
			w.setInputOutputArea();
		}

		// text inputs
		w.setInputTextArea(DIR_FROM, 1);
		w.setInputTextArea(CONTAINS_STR, 1);
//
		// buttons
		w.setButtonArea("Show File Names", 0);
		w.setButtonArea("Show How Organized", 0);
		w.setButtonArea("Organize into folders", 0);

		w.getTextAreas().get(1).setPreferredSize(new Dimension(300, 20));

		// outputs
		w.setOutputTextAreaHeights(600);
		w.setOutputTextArea(OUTPUT, 3);

		setListeners();
		w.getTextAreaByName(DIR_FROM).setText("C:\\Users\\");
		w.getTextAreaByName(CONTAINS_STR).setText(" - ");
		w.setVisible();
	}

	private void showFileNames1() {
		String fromDir = w.getTextAreaByName(DIR_FROM).getText();

		File folder = new File(fromDir);
		List<File> listToFill = new ArrayList<File>();
		fs.findFiles(listToFill, folder);
		String result = "", n = "\n";

		for (int i = 0; i < listToFill.size(); i++) {
			File file = listToFill.get(i);
			String name = f.getSimpleFileName(file);
			result += name + n;
		}
		w.getTextAreaByName(OUTPUT).setText(result);
	}

	private void showOrganizedNames() {
		String fromDir = w.getTextAreaByName(DIR_FROM).getText();
		String similaritySplitter = w.getTextAreaByName(CONTAINS_STR).getText();

		List<File> files = getFiles(fromDir);
		List<String> names = getFileNames(files);
		String organized = "", n = "\n";

		Set<String> uniqueGroup = new LinkedHashSet<String>();

		for (int i = 0; i < names.size(); i++) {
			String name = names.get(i);
			String[] splitName = name.split(similaritySplitter);
			if (splitName.length > 1) {
				String firstNamePart = splitName[0].trim();
				if (firstNamePart.length() > 3) {
					List<String> duplicates = getDuplicates(firstNamePart, names);
					if (duplicates != null) {
						boolean isFirstTimeAdded = uniqueGroup.add(firstNamePart);
						if (isFirstTimeAdded) {
							for (int j = 0; j < duplicates.size(); j++) {
								String current = duplicates.get(j);
								organized += firstNamePart + "/" + current + n;
							}
						}
					}
				}
			}
		}
		output(organized);
	}

	private void showOrganizedNames2() {
		String fromDir = w.getTextAreaByName(DIR_FROM).getText();
		String similaritySplitter = w.getTextAreaByName(CONTAINS_STR).getText();

		List<File> files = getFiles(fromDir);
		List<String> names = getFileNames(files);
		String organized = "", n = "\n";

		Set<String> uniqueGroup = new LinkedHashSet<String>();

		for (int i = 0; i < names.size(); i++) {
			String name = names.get(i);
			String[] splitName = name.split(similaritySplitter);
			if (splitName.length > 1) {
				String firstNamePart = splitName[0].trim();
				if (firstNamePart.length() > 3) {
					List<List<String>> sortedFiles = getDuplicatesAndNonDuplicates(firstNamePart, names);
					if (sortedFiles != null) {

						List<String> duplicates = sortedFiles.get(0);
						names = sortedFiles.get(1);

						if (duplicates.size() > 2) {

							boolean isFirstTimeAdded = uniqueGroup.add(firstNamePart);
							if (isFirstTimeAdded) {
								for (int j = 0; j < duplicates.size(); j++) {
									String current = duplicates.get(j);
									organized += firstNamePart + "/" + current + n;
								}
							}
						}
					}
				}
			}
		}
		output(organized);
	}

	private void organizedNames2() {
		String fromDir = w.getTextAreaByName(DIR_FROM).getText();
		String similaritySplitter = w.getTextAreaByName(CONTAINS_STR).getText();

		List<File> files = getFiles(fromDir);
		List<String> names = getFileNames(files);
		String organized = "", n = "\n";

		Set<String> uniqueGroup = new LinkedHashSet<String>();
		List<List<File>> groups = new ArrayList<List<File>>();

		for (int i = 0; i < files.size(); i++) {
			File file = files.get(i);
			String name = f.getSimpleFileName(file);
			String[] splitName = name.split(similaritySplitter);
			if (splitName.length > 1) {
				String firstNamePart = splitName[0].trim();
				if (firstNamePart.length() > 3) {
					List<List<File>> sortedFiles = getDuplicateAndNonDuplicateFiles(firstNamePart, files);
					if (sortedFiles != null) {
						List<File> duplicates = sortedFiles.get(0);
						files = sortedFiles.get(1);
						if (duplicates.size() > 2) {
							boolean isFirstTimeAdded = uniqueGroup.add(firstNamePart);

							if (isFirstTimeAdded) {
								List<File> group = new ArrayList<>();
								for (int j = 0; j < duplicates.size(); j++) {
									group.add(duplicates.get(j));
								}
								groups.add(group);
							}
						}
					}
				}
			}
		}

		List<String> folders = new ArrayList<String>();
		folders.addAll(uniqueGroup);

		for (int i = 0; i < groups.size(); i++) {
			String folder = folders.get(i);
			String toWhere = fromDir + "\\" + folder;
			f.createDirectory(toWhere);
		}

		for (int i = 0; i < groups.size(); i++) {
			List<File> group = groups.get(i);
			String folder = folders.get(i).trim();
			String toWhere = fromDir + "\\" + folder;
			moveFiles(group, toWhere);
		}
	}

	private void organizedNames() {
		String fromDir = w.getTextAreaByName(DIR_FROM).getText();
		String similaritySplitter = w.getTextAreaByName(CONTAINS_STR).getText();

		List<File> files = getFiles(fromDir);
		List<String> names = getFileNames(files);
		String organized = "", n = "\n";

		Set<String> uniqueGroup = new LinkedHashSet<String>();
		List<List<File>> groups = new ArrayList<List<File>>();

		for (int i = 0; i < names.size(); i++) {
			String name = names.get(i);
			File file = files.get(i);
			String[] splitName = name.split(similaritySplitter);
			if (splitName.length > 1) {
				String firstNamePart = splitName[0].trim();
				if (firstNamePart.length() > 3) {
					List<File> duplicates = getDuplicateFiles(firstNamePart, files);
					if (duplicates != null) {
						boolean isFirstTimeAdded = uniqueGroup.add(firstNamePart);
						if (isFirstTimeAdded) {
							List<File> group = new ArrayList<>();
							for (int j = 0; j < duplicates.size(); j++) {
								group.add(duplicates.get(j));
							}
							groups.add(group);
						}
					}
				}
			}
		}

		List<String> folders = new ArrayList<String>();
		folders.addAll(uniqueGroup);

		for (int i = 0; i < groups.size(); i++) {
			String folder = folders.get(i);
			String toWhere = fromDir + "\\" + folder;
			f.createDirectory(toWhere);
		}

		for (int i = 0; i < groups.size(); i++) {
			List<File> group = groups.get(i);
			String folder = folders.get(i).trim();
			String toWhere = fromDir + "\\" + folder;
			moveFiles(group, toWhere);
		}
	}

	private List<File> getRemovedArrayFromArray(List<File> removeThis, List<File> FromThis) {

		return null;
	}

	private List<String> getDuplicates(String isThisStr, List<String> here) {
		String isThis = isThisStr.toLowerCase().trim();
		List<String> names = new ArrayList<String>();
		int counter = 0;

		for (int i = 0; i < here.size(); i++) {
			String currentString = here.get(i).toLowerCase();
			if (currentString.indexOf(isThis) != -1) {
				names.add(here.get(i));
				counter++;
			}
		}
		if (counter < 2) {
			return null;
		}
		return names;
	}

	private List<List<String>> getDuplicatesAndNonDuplicates(String isThisStr, List<String> here) {
		String isThis = isThisStr.toLowerCase().trim();
		List<String> names = new ArrayList<String>();
		List<String> nonDuplicates = new ArrayList<String>();
		List<List<String>> duAndNonDu = new ArrayList<List<String>>();

		int copyCounter = 0;
		for (int i = 0; i < here.size(); i++) {
			String currentString = here.get(i).toLowerCase();
			if (currentString.indexOf(isThis) != -1) {
				names.add(here.get(i));
				copyCounter++;
			} else {
				nonDuplicates.add(here.get(i));
			}
		}

		if (copyCounter == 0) {
			return null;
		}

		duAndNonDu.add(names);
		duAndNonDu.add(nonDuplicates);

		return duAndNonDu;
	}

	private List<File> getDuplicateFiles(String isThisStr, List<File> here) {
		String isThis = isThisStr.toLowerCase().trim();
		List<File> names = new ArrayList<File>();
		int counter = 0;

		for (int i = 0; i < here.size(); i++) {
			String currentString = f.getSimpleFileName(here.get(i)).toLowerCase();
			if (currentString.indexOf(isThis) != -1) {
				names.add(here.get(i));
				counter++;
			}
		}
		if (counter < 2) {
			return null;
		}
		return names;
	}

	private List<List<File>> getDuplicateAndNonDuplicateFiles(String isThisStr, List<File> here) {
		String isThis = isThisStr.toLowerCase().trim();
		List<File> names = new ArrayList<File>();
		List<File> nonDuplicates = new ArrayList<>();
		List<List<File>> duAndNonDu = new ArrayList<List<File>>();
		int copyCounter = 0;

		for (int i = 0; i < here.size(); i++) {
			String currentString = f.getSimpleFileName(here.get(i)).toLowerCase();
			if (currentString.indexOf(isThis) != -1) {
				names.add(here.get(i));
				copyCounter++;
			} else {
				nonDuplicates.add(here.get(i));
			}
		}

		if (copyCounter == 0) {
			return null;
		}

		duAndNonDu.add(names);
		duAndNonDu.add(nonDuplicates);

		return duAndNonDu;
	}

	private void output(String str) {
		w.getTextAreaByName(OUTPUT).setText(str);
	}

	private void moveFiles(List<File> files, String toWhere) {
		for (int i = 0; i < files.size(); i++) {
			File f = files.get(i);
			boolean ok = fs.moveFileTo(f.getAbsolutePath(), toWhere + "\\" + f.getName());
		}
	}

	private List<File> getFiles(String fromWhere) {
		File folder = new File(fromWhere);
		List<File> listToFill = new ArrayList<File>();
		fs.findFiles(listToFill, folder);
		return listToFill;
	}

	private List<String> getFileNames(String fromWhere) {
		List<File> files = getFiles(fromWhere);
		return getFileNames(files);
	}

	private List<String> getFileNames(List<File> l) {
		List<String> result = new ArrayList<String>();

		for (int i = 0; i < l.size(); i++) {
			File file = l.get(i);
			String name = f.getSimpleFileName(file);
			result.add(name);
		}
		return result;
	}

	private void setListeners() {
		for (int i = 0; i < w.getButtons().size(); i++) {
			w.getButtons().get(i).addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == w.getButtons().get(0)) {
				showFileNames1();
			}
			if (e.getSource() == w.getButtons().get(1)) {
				showOrganizedNames2();
			}

			if (e.getSource() == w.getButtons().get(2)) {
				organizedNames2();
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}
