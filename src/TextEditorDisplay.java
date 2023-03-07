import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTextArea;

public class TextEditorDisplay implements ActionListener {
//	private static final String DIR_FROM_DEFAULT = "C:\\Users\\Ubaby\\Google Drive\\1-UBABY\\1. foto\\";
	private static final String DIR_FROM_DEFAULT = "C:\\Users\\";
	final String DIR_FROM = "Directory From";
	final String DIR_TO = "Directory To";
	final String CONTAINS_STR = "Contains String";
	final String OUTPUT = "Result";

	private WindowBuilder w = new WindowBuilder();
	private ListHelper l = new ListHelper();
	private TextDataHelper txt = new TextDataHelper();
	private StringHelper sh = new StringHelper();
	private List<String[]> data;
	private FilesHelper fs = new FilesHelper();
	private FileHelp f = new FileHelp();

	public static void main(String[] args) {
		new TextEditorDisplay();
	}

	public TextEditorDisplay() {
		fileNameFixer();
//		fileMover();
	}

	private void showFileNames() {
		String fromDir = w.getTextAreaByName(DIR_FROM).getText();
//		String toDir = w.getTextAreaByName(DIR_TO).getText();
//		String type = w.getTextAreaByName(CONTAINS_STR).getText();

		File folder = new File(fromDir);
		List<File> listToFill = new ArrayList<File>();
		fs.findFiles(listToFill, folder);
//		fs.findFilesLike(listToFill, folder, type);
		String result = "", n = "\n";

		for (int i = 0; i < listToFill.size(); i++) {
			File file = listToFill.get(i);
			String name = f.getSimpleFileName(file);
			result += name + n;
		}
		w.getTextAreaByName(OUTPUT).setText(result);

	}
	// TODO: OLD FILE NAME REPLACER
//	private void showFileNames() {
//		String fromDir = w.getTextAreaByName("Directory to process").getText();
//		File folder = new File(fromDir);
//		List<File> listToFill = new ArrayList<File>();
//		fs.findFiles(listToFill, folder);
//		String result = "", n = "\n";
//		
//		for (int i = 0; i < listToFill.size(); i++) {
//			File file = listToFill.get(i);
//			String name=FileHelp.getSimpleFileName(file);
//			result += name + n;
//		}
//		w.getTextAreaByName(OUTPUT).setText(result);
//	}

	private void moveFiles() {
		String fromDir = w.getTextAreaByName(DIR_FROM).getText();
		String toDir = w.getTextAreaByName(DIR_TO).getText();
		String type = w.getTextAreaByName(CONTAINS_STR).getText();
		String output = w.getTextAreaByName(OUTPUT).getText();

		String[] filesToProcess = output.trim().split("\n");
		for (int i = 0; i < filesToProcess.length; i++) {
			String dir = filesToProcess[i];
			File f = new File(dir);

			boolean ok = fs.moveFileTo(dir, toDir + "\\" + f.getName());
			System.out.println(dir);
			System.out.println(ok);
		}

	}

	private void showSimpleFileNames() {
//		String fromDir = w.getTextAreaByName("Directory to process").getText();
//		File folder = new File(fromDir);
//		List<File> listToFill = new ArrayList<File>();
//		fs.findFiles(listToFill, folder);

		String[] newFileNames = w.getTextAreaByName(OUTPUT).getText().trim().split("\n");
		if (newFileNames.length < 5) {
			return;
		}

		String result = "", n = "\n";
		for (int i = 0; i < newFileNames.length; i++) {
//			File file = listToFill.get(i);
			String name = newFileNames[i];
			name = getOnlySimple(name).trim();
			name = StringHelper.getComplexWordsCapitalized(name);
			name = StringHelper.getWithoutBracketsInFront(name).trim();
			name = StringHelper.getWithoutNumbersInFront(name).trim();

			result += name + n;
		}
		w.getTextAreaByName(OUTPUT).setText(result);
	}

	private void processFileNames() {
		String fromDir = w.getTextAreaByName(DIR_FROM).getText();
		File folder = new File(fromDir);
		List<File> listToFill = new ArrayList<File>();
		fs.findFiles(listToFill, folder);
		String result = "", n = "\n";

		String[] newFileNames = w.getTextAreaByName(OUTPUT).getText().trim().split(n);
		if (newFileNames.length != listToFill.size()) {
			return;
		}

		for (int i = 0; i < listToFill.size(); i++) {
			File file = listToFill.get(i);
			String newName = newFileNames[i].trim();
			result += newName + n;
			String newPath = FileHelp.getRenamedPathName(file, newName);

			if (f.getSimpleFileName(file).toLowerCase().equals(newName.toLowerCase())) {
				if (!f.getSimpleFileName(file).equals(newName)) {
					f.renameFile(file.getAbsolutePath(), newPath + ".todo");
					f.renameFile(newPath + ".todo", newPath);
				}
			} else {
				f.renameFile(file.getAbsolutePath(), newPath);
			}
		}
		w.getTextAreaByName(OUTPUT).setText(result);

	}

//	private String getRenamedPathName(File file, String newName) {
//		String path = file.getAbsolutePath();
//		String extension = getFileExtension(file);
//		String oldName = getSimpleFileName(file);
//		String folderName = file.getParent();
//		return folderName + "\\" + newName + extension;
//	}
//	
//	private String getSimpleFileName(File file) {
//		String extension = getFileExtension(file);
//		String name = file.getName().replaceAll(extension, "");
//		return name;
//	}
//	
//	private String getFileExtension(File file) {
//		String extension = "";
//		String fileName=file.getName();
//
//		int i = fileName.lastIndexOf('.');
//		if (i > 0) {
//		    extension = fileName.substring(i+1);
//		}
//		return extension;
//	}

	private void showReplacedFileNames() {
		String fromDir = w.getTextAreaByName(DIR_FROM).getText();
		String regex = w.getTextAreaByName("Replace What Regex").getText();
		String with = w.getTextAreaByName("Replace With").getText();

		String[] newFileNames = w.getTextAreaByName(OUTPUT).getText().trim().split("\n");

		if (newFileNames.length < 5) {
			return;
		}
//		File folder = new File(fromDir);
//		List<File> listToFill = new ArrayList<File>();
//		fs.findFiles(listToFill, folder);
		String result = "", n = "\n";
		for (int i = 0; i < newFileNames.length; i++) {
			String name = newFileNames[i];
			result += name.replaceAll(regex, with) + n;
		}
		w.getTextAreaByName(OUTPUT).setText(result);
	}

	private String getOnlySimple(String s) {
		return txt.getSomewhatOnlySimpleSymbols(s);
	}

	private void fileNameFixer() {
		w.initializeDisplay("File Name Fixer.", 1400, 800);
		w.getMainPanel().setBackground(new Color(100, 100, 100));

		// panels
		for (int i = 0; i < 4; i++) {
			w.setInputOutputArea();
		}

		// text inputs
		w.setInputTextArea(DIR_FROM, 1);
		w.setInputTextArea("Replace What Regex", 1);
		w.setInputTextArea("Replace With", 1);
//
		// buttons
		w.setButtonArea("Load File Names", 0);
		w.setButtonArea("Simplify Names", 0);
		w.setButtonArea("Replace Regex In Names", 0);
		w.setButtonArea("Show New Directories Names", 0);
		w.setButtonArea("Rename Into Actual Files", 0);

//		int len = w.getTextAreas().size();
//		for (int i = 0; i < len; i += 2) {
		w.getTextAreas().get(1).setPreferredSize(new Dimension(300, 20));
//		}

		// outputs
		w.setOutputTextAreaHeights(600);
		w.setOutputTextArea(OUTPUT, 3);

		setListeners();
		w.getTextAreaByName(DIR_FROM)
//		.setText("C:\\Users\\Ubaby\\Desktop\\progressive-positive");
				.setText("C:\\Users\\");
		w.setVisible();
	}

	private void setListeners() {
		for (int i = 0; i < w.getButtons().size(); i++) {
			w.getButtons().get(i).addActionListener(this);
		}
	}

	private void fileMover() {
		w.initializeDisplay("File Mover.", 1400, 800);
		w.getMainPanel().setBackground(new Color(100, 100, 100));

		// panels
		for (int i = 0; i < 4; i++) {
			w.setInputOutputArea();
		}

		// text inputs
		w.setInputTextArea(DIR_FROM, 1);
		w.setInputTextArea(DIR_TO, 1);
		w.setInputTextArea(CONTAINS_STR, 1);
//
		// buttons
		w.setButtonArea("Show File Names", 0);
		w.setButtonArea("Move Files", 0);
//		w.setButtonArea("Show Replaced Names", 0);
//		w.setButtonArea("Show New File Names", 0);
//		w.setButtonArea("Rename Into Actual Files", 0);

//		int len = w.getTextAreas().size();
//		for (int i = 0; i < len; i += 2) {
		w.getTextAreas().get(1).setPreferredSize(new Dimension(300, 20));
//		}

		// outputs
		w.setOutputTextAreaHeights(600);
		w.setOutputTextArea(OUTPUT, 3);

		setListeners();
		w.getTextAreaByName(DIR_FROM).setText(DIR_FROM_DEFAULT);
		// FileHelp.createDirectoryOnDesktop("RECENTLY_MOVED_FILES");
		w.getTextAreaByName(DIR_TO).setText(FileHelp.getCurrentDirectory() + "\\RECENTLY_MOVED_FILES");
		FileHelp.createDirectory(FileHelp.getCurrentDirectory() + "/RECENTLY_MOVED_FILES");
		w.getTextAreaByName(CONTAINS_STR).setText(".mp3");
		w.setVisible();
	}

//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		try {
//			if (e.getSource() == w.getButtons().get(0)) {
//				showFileNames();
//			}
//
//			if (e.getSource() == w.getButtons().get(1)) {
//				moveFiles();
//			}
////
////			if (e.getSource() == w.getButtons().get(2)) {
////				showReplacedFileNames();
////			}
//////
////			if (e.getSource() == w.getButtons().get(3)) {
////				showNewFileNames();
////			}
////			if (e.getSource() == w.getButtons().get(4)) {
////				processFileNames();
////			}
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
//	}

	// TODO: OLD FILE NAME REPLACER
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == w.getButtons().get(0)) {
				showFileNames();
			}

			if (e.getSource() == w.getButtons().get(1)) {
				showSimpleFileNames();
			}

			if (e.getSource() == w.getButtons().get(2)) {
				showReplacedFileNames();
			}
//
			if (e.getSource() == w.getButtons().get(3)) {
				showNewFileNames();
			}
			if (e.getSource() == w.getButtons().get(4)) {
				processFileNames();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

//	private void excelThingActions(ActionEvent e) {
//		try {
//			if (e.getSource() == w.getButtons().get(0)) {
//				int from = (int) l.parse(w.getTextAreaByName("From").getText().toString());
//				int to = (int) l.parse(w.getTextAreaByName("To").getText().toString());
//				data = l.removeColumn(data, from, to);
//				w.getTextAreaByName("Data").setText("Removed: ");
//			}
//
//			if (e.getSource() == w.getButtons().get(1)) {
//				JTextArea dt = w.getTextAreaByName("Dir");
//				JTextArea ft = w.getTextAreaByName("File");
//				List<String> d = new ArrayList<String>();
//				for (int i = 0; i < data.size(); i++) {
//					d.add(txt.join(data.get(i), ","));
//				}
//				txt.toFile(d, dt.getText() + "\\" + ft.getText());
//			}
//
//			if (e.getSource() == w.getButtons().get(2)) {
//				String[] head = data.get(0);
//				data.remove(0);
//				Collections.shuffle(data);
//				data.add(0, head);
//				w.getTextAreaByName("Data").setText("Shuffled: ");
//			}
//
//			if (e.getSource() == w.getButtons().get(3)) {
//				String fromDir = w.getTextAreaByName("FromDir").getText();
//				String fromFile = w.getTextAreaByName("FromFile").getText();
//				data = l.listToListOfArrays(txt.fromFile(fromDir + "\\" + fromFile));
//				w.getTextAreaByName("Data").setText("Loaded: ");
//			}
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
//	}
//
//	private void excelThingie() {
//		w.initializeDisplay("Excel Read Only Solution.", 1400, 800);
//		w.getMainPanel().setBackground(new Color(100, 100, 100));
//
//		// panels
//		for (int i = 0; i < 4; i++) {
//			w.setInputOutputArea();
//		}
//
//		// text inputs
//		w.setInputTextArea("From", 0);
//		w.setInputTextArea("To", 0);
//
//		w.setInputTextArea("FromDir", 1);
//		w.setInputTextArea("FromFile", 1);
//
//		w.setInputTextArea("Dir", 2);
//		w.setInputTextArea("File", 2);
//
//		// buttons
//		w.setButtonArea("Delete", 0);
//		w.setButtonArea("Save", 1);
//		w.setButtonArea("Shuffle", 0);
//		w.setButtonArea("Load", 0);
//
//		int len = w.getTextAreas().size();
//		for (int i = 5; i < len; i += 2) {
//			w.getTextAreas().get(i).setPreferredSize(new Dimension(300, 20));
//		}
//
//		// outputs
//		w.setOutputTextAreaHeights(600);
//		w.setOutputTextArea("Data", 3);
//
//		setListeners();
//		w.setVisible();
//	}

	private void showNewFileNames() {
		String fromDir = w.getTextAreaByName(DIR_FROM).getText();
		File folder = new File(fromDir);
		List<File> listToFill = new ArrayList<File>();
		fs.findFiles(listToFill, folder);
		String result = "", n = "\n";

		String[] newFileNames = w.getTextAreaByName(OUTPUT).getText().trim().split(n);
		if (newFileNames.length != listToFill.size()) {
			return;
		}

		for (int i = 0; i < listToFill.size(); i++) {
			File file = listToFill.get(i);
			String newName = newFileNames[i].trim();
			String newPath = FileHelp.getRenamedPathName(file, newName);
			result += newPath + n;
//			f.renameFile(file.getAbsolutePath(), newPath);
		}
		w.getTextAreaByName(OUTPUT).setText(result);

	}

}
