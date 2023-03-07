import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTextArea;

public class DuplicateFileSorter implements ActionListener {
//	private static final String DIR_FROM_DEFAULT = "C:\\Users\\Ubaby\\Google Drive\\1-UBABY\\1. foto\\";
	private static final String DIR_FROM_DEFAULT = "C:\\Users\\";
	 String DIR_FROM = "Directory From";
	 String DIR_FROM2 = "Directory To Check";
	 String DIR_TO = "Directory To";
	 String CONTAINS_STR = "Contains String";
	 String OUTPUT = "Result";

	private WindowBuilder w = new WindowBuilder();
	private ListHelper l = new ListHelper();
	private TextDataHelper txt = new TextDataHelper();
	private StringHelper sh = new StringHelper();
	private List<String[]> data;
	private FilesHelper fs = new FilesHelper();
	private FileHelp f = new FileHelp();

	public static void main(String[] args) {
		new DuplicateFileSorter();
	}

	public DuplicateFileSorter() {
		duplicateFileFixer();
//		fileNameFixer();
//		fileMover();
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
	
	private void showFileNames2() {
		String fromDir = w.getTextAreaByName(DIR_FROM2).getText();
		
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
	
	private void showFileNamesUnique() {
		String fromDir1 = w.getTextAreaByName(DIR_FROM).getText();
		String fromDir2 = w.getTextAreaByName(DIR_FROM2).getText();
		
		File folder1 = new File(fromDir1);
		File folder2 = new File(fromDir2);
		List<File> listToFill1 = new ArrayList<File>();
		List<File> listToFill2 = new ArrayList<File>();
		fs.findFiles(listToFill1, folder1);
		fs.findFiles(listToFill2, folder2);
		String result = "", n = "\n";
		
		for (int i = 0; i < listToFill1.size(); i++) {
			File file1 = listToFill1.get(i);
			String name1 = f.getSimpleFileName(file1);
			int count=0;
			for (int j = 0; j < listToFill2.size(); j++) {
				File file2 = listToFill2.get(j);
				String name2 = f.getSimpleFileName(file2);
				if(name1.equals(name2)) {
					count++;
				}
			}
			if(count==0) {
				result += name1+n;
			}
			count=0;
		}
		w.getTextAreaByName(OUTPUT).setText(result);
	}
	
	private void showFileNamesCopies() {
		String fromDir1 = w.getTextAreaByName(DIR_FROM).getText();
		String fromDir2 = w.getTextAreaByName(DIR_FROM2).getText();
		
		File folder1 = new File(fromDir1);
		File folder2 = new File(fromDir2);
		List<File> listToFill1 = new ArrayList<File>();
		List<File> listToFill2 = new ArrayList<File>();
		fs.findFiles(listToFill1, folder1);
		fs.findFiles(listToFill2, folder2);
		String result = "", n = "\n";
		
		for (int i = 0; i < listToFill1.size(); i++) {
			File file1 = listToFill1.get(i);
			String name1 = f.getSimpleFileName(file1);
			int count=0;
			for (int j = 0; j < listToFill2.size(); j++) {
				File file2 = listToFill2.get(j);
				String name2 = f.getSimpleFileName(file2);
				if(name1.equals(name2)) {
					count++;
				}
			}
			if(count>0) {
				result += name1+n;
			}
			count=0;
		}
		w.getTextAreaByName(OUTPUT).setText(result);
	}
	

	private void moveCopies() {
		
		String fromDir1 = w.getTextAreaByName(DIR_FROM).getText();
		String fromDir2 = w.getTextAreaByName(DIR_FROM2).getText();
		String newFolder=fromDir1+"/moved";
		f.createDirectory(newFolder);
		
		File folder1 = new File(fromDir1);
		File folder2 = new File(fromDir2);
		List<File> listToFill1 = new ArrayList<File>();
		List<File> listToFill2 = new ArrayList<File>();
		List<File> fileCopies = new ArrayList<File>();
		fs.findFiles(listToFill1, folder1);
		fs.findFiles(listToFill2, folder2);
		String result = "", n = "\n";
		
		for (int i = 0; i < listToFill1.size(); i++) {
			File file1 = listToFill1.get(i);
			String name1 = f.getSimpleFileName(file1);
			int count=0;
			for (int j = 0; j < listToFill2.size(); j++) {
				File file2 = listToFill2.get(j);
				String name2 = f.getSimpleFileName(file2);
				if(name1.equals(name2)) {
					count++;
				}
			}
			if(count>0) {
				result += name1+n;
				fileCopies.add(file1);
			}
			count=0;
		}
		
		for (int i = 0; i < fileCopies.size(); i++) {
			File f =fileCopies.get(i);

			boolean ok = fs.moveFileTo(f.getAbsolutePath(), newFolder + "\\" + f.getName());
		}
		
		
		w.getTextAreaByName(OUTPUT).setText(result);

	}

	private void duplicateFileFixer() {
		DIR_FROM = "Which of these. Directory.";
		DIR_FROM2 = "Are here. Directory";
		
		w.initializeDisplay("Duplicate File Fixer.", 1400, 800);
		w.getMainPanel().setBackground(new Color(100, 100, 100));

		// panels
		for (int i = 0; i < 4; i++) {
			w.setInputOutputArea();
		}

		// text inputs
		w.setInputTextArea(DIR_FROM, 1);
		w.setInputTextArea(DIR_FROM2, 1);
//
		// buttons
		w.setButtonArea("Load File Names 1", 0);
		w.setButtonArea("Load File Names 2", 0);
		w.setButtonArea("Show Copies", 0);
		w.setButtonArea("Show Unique Files", 0);
		w.setButtonArea("Move Copies", 0);

		w.getTextAreas().get(1).setPreferredSize(new Dimension(300, 20));

		// outputs
		w.setOutputTextAreaHeights(600);
		w.setOutputTextArea(OUTPUT, 3);

		setListeners();
		w.getTextAreaByName(DIR_FROM).setText("C:\\Users\\");
		w.getTextAreaByName(DIR_FROM2).setText("C:\\Users\\");
		w.setVisible();
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
				showFileNames2();
			}
			
			if (e.getSource() == w.getButtons().get(2)) {
				showFileNamesCopies();
			}
			if (e.getSource() == w.getButtons().get(3)) {
				showFileNamesUnique();
			}
			if (e.getSource() == w.getButtons().get(4)) {
				moveCopies();
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}
