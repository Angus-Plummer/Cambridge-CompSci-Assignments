package uk.ac.cam.ap801.tick6;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PatternPanel extends JPanel {
	
	private JList guiList;
	 
	public PatternPanel() {
		super();
		setLayout(new BorderLayout());
		guiList = new JList();
		add(new JScrollPane(guiList));
	 }
	 
	public void setPatterns(List<Pattern> list) {
		ArrayList<String> names = new ArrayList<String>();
		for (Pattern p : list){
			String patAuthor = p.getAuthor();
			String patName = p.getName();
			String patDescription = patName + " (" + patAuthor + ")";
			names.add(patDescription);
		}
		guiList.setListData(names.toArray());
	 } 

}
