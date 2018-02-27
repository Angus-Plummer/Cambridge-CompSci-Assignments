package uk.ac.cam.ap801.tick7;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public abstract class SourcePanel extends JPanel {
	
	private JRadioButton current;
	
	protected abstract boolean setSourceNone();
	protected abstract boolean setSourceLibrary();
	protected abstract boolean setSourceThreeStar();
	protected abstract boolean setSourceFile();
	
	public SourcePanel() {
		super();
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		final JRadioButton none = new JRadioButton(Strings.BUTTON_SOURCE_NONE, true);
		final JRadioButton file = new JRadioButton(Strings.BUTTON_SOURCE_FILE, true);
		final JRadioButton library = new JRadioButton(Strings.BUTTON_SOURCE_LIBRARY, true);
		final JRadioButton fourStar = new JRadioButton(Strings.BUTTON_SOURCE_FOURSTAR, true); 
		 
		 //add ActionListeners to each button
		none.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setSourceNone())
					//successful: file found and patterns loaded
					current = none; 
				else
					 //unsuccessful: re-enable previous source choice
					current.setSelected(true); 
			}
		});
		file.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setSourceFile())
					//successful: file found and patterns loaded
					current = file; 
				else
					 //unsuccessful: re-enable previous source choice
					current.setSelected(true); 
			}
		});
		library.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setSourceLibrary())
					//successful: file found and patterns loaded
					current = library; 
				else
					 //unsuccessful: re-enable previous source choice
					current.setSelected(true); 
			}
		});
		fourStar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setSourceThreeStar())
					//successful: file found and patterns loaded
					current = fourStar; 
				else
					 //unsuccessful: re-enable previous source choice
					current.setSelected(true); 
			}
		});
		 
		 //add RadioButtons to this JPanel
		add(none);
		add(file);
		add(library);
		add(fourStar);
		 //create a ButtonGroup containing all four buttons
		 //Only one Button in a ButtonGroup can be selected at once
		ButtonGroup group = new ButtonGroup();
		group.add(none);
		group.add(file);
		group.add(library);
		group.add(fourStar);
		current = none;
	}
}
