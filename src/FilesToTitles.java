import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTextArea;

public class FilesToTitles {

	public static void main(String[] args) {
		new DisplayManager();
	}

}

class DisplayManager implements ActionListener {
	private WindowBuilder w = new WindowBuilder();
	private ListHelper l = new ListHelper();
	private TextDataHelper txt = new TextDataHelper();
	private List<String[]> data;

	DisplayManager() {
		w.initializeDisplay("Get Files To List of Names.", 1400, 800);
		w.getMainPanel().setBackground(new Color(100, 100, 100));
//
//		// panels
		for (int i = 0; i < 2; i++) {
			w.setInputOutputArea();
		}
//
//		// text inputs
		w.setInputTextArea("Directory", 0);
//		w.setInputTextArea("To", 0);
//
//		w.setInputTextArea("directory title", 1);
//		w.setInputTextArea("FromFile", 1);
//
//		w.setInputTextArea("Dir",2);
//		w.setInputTextArea("File", 2);
//
//		// buttons
		w.setButtonArea("Start", 0);
//		w.setButtonArea("Shuffle", 0);
//		w.setButtonArea("Load", 0);
//		
		int len = w.getTextAreas().size();
		for (int i = 5; i < len; i += 2) {
			w.getTextAreas().get(i).setPreferredSize(new Dimension(300, 20));
		}
//
//		// outputs
		w.setOutputTextAreaHeights(200);
		w.setOutputTextArea("Data", 1);

//
		setListeners();
		w.setVisible();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == w.getButtons().get(0)) {
				
				String dir = w.getTextAreaByName("Directory").getText().toString();
				FilesHelper f=new FilesHelper();
				
				List<String> list= f.dir(dir);
				String all="";
				for (int i = 0; i < list.size(); i++) {
					String str=list.get(i);
					str=str.replace(".mp3", "");
					str=str.replace("'", "");
					str=str.replace("‘", "");
					all+=str+"\n";
				}
				w.getTextAreaByName("Data").setText(all);
			}
//
//		    if (e.getSource() == w.getButtons().get(1)) {
//			JTextArea dt = w.getTextAreaByName("Dir");
//			JTextArea ft = w.getTextAreaByName("File");
//			List<String> d = new ArrayList<String>();
//			for (int i = 0; i < data.size(); i++) {
//			    d.add(txt.join(data.get(i), ","));
//			}
//			txt.toFile(d, dt.getText() + "\\" + ft.getText());
//		    }
//
//		    if (e.getSource() == w.getButtons().get(2)) {
//			String[] head=data.get(0);
//			data.remove(0);
//			Collections.shuffle(data);
//			data.add(0, head);
//			w.getTextAreaByName("Data").setText("Shuffled: ");
//		    }
//
//		    if (e.getSource() == w.getButtons().get(3)) {
//			String fromDir = w.getTextAreaByName("FromDir").getText();
//			String fromFile = w.getTextAreaByName("FromFile").getText();
//			data = l.listToListOfArrays(txt.fromFile(fromDir + "\\" + fromFile));
//			w.getTextAreaByName("Data").setText("Loaded: ");
//		    }
		} catch (Exception e2) {
			e2.printStackTrace();
			w.getTextAreaByName("Data").setText(e2.toString());
		}
	}

	private void setListeners() {
		for (int i = 0; i < w.getButtons().size(); i++) {
			w.getButtons().get(i).addActionListener(this);
		}
	}
}
