package uk.ac.cam.ap801.tick6;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.List;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import uk.ac.cam.acr31.life.World;


public class GuiLife extends JFrame {
	
	public PatternPanel patternPanel;
	public ControlPanel controlPanel;
	public GamePanel gamePanel;
	
	public GuiLife() {
		 super("GuiLife");
		 setSize(640, 480);
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 setLayout(new BorderLayout());
		 JComponent optionsPanel = createOptionsPanel();
		 add(optionsPanel, BorderLayout.WEST);
		 JComponent gamePanel = createGamePanel();
		 add(gamePanel, BorderLayout.CENTER);
	}
		 
	private JComponent createOptionsPanel() {
		 Box result = Box.createVerticalBox();
		 result.add(createSourcePanel());
		 result.add(createPatternPanel());
		 result.add(createControlPanel());
		 return result;
	}
		 
	private void addBorder(JComponent component, String title) {
		 Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		 Border tb = BorderFactory.createTitledBorder(etch,title);
		 component.setBorder(tb);
	}
		 
	private JComponent createGamePanel() {
		 JPanel holder = new JPanel();
		 addBorder(holder,Strings.PANEL_GAMEVIEW);
		 GamePanel result = new GamePanel();
		 holder.add(result);
		 gamePanel = result;
		 return new JScrollPane(holder);
	}
		 
	private JComponent createSourcePanel() {
		 SourcePanel result = new SourcePanel();
		 addBorder(result,Strings.PANEL_SOURCE);
		 return result; 
	}
		
	private JComponent createPatternPanel() {
		PatternPanel result = new PatternPanel();
		addBorder(result,Strings.PANEL_PATTERN);
		patternPanel = result;
		return patternPanel;
	}
	
	private JComponent createControlPanel() {
		ControlPanel result = new ControlPanel();
		addBorder(result,Strings.PANEL_CONTROL);
		controlPanel = result;
		return result; 
	}
	
	public static void main(String[] args2) {
		String[] args = {"-long","http://www.cl.cam.ac.uk/teaching/current/ProgJava/life.txt", "0"};
		GuiLife gui = new GuiLife();
		try{
			CommandLineOptions c = new CommandLineOptions(args);
			List<Pattern> list;
			if (c.getSource().startsWith("http://")){
				list = PatternLoader.loadFromURL(c.getSource());
			} else{
				list = PatternLoader.loadFromDisk(c.getSource());
			}
			if (c.getIndex() == null) {
				int i = 0;
				for (Pattern p : list)
						System.out.println((i++)+" "+p.getName()+" "+p.getAuthor());
			} else { 
				if (c.getIndex() < 0 || c.getIndex() > list.size()){
					throw new CommandLineException("Index out of bounds");
			}
			gui.patternPanel.setPatterns(list);
			World w = gui.controlPanel.initialiseWorld(list.get(c.getIndex()));
			gui.gamePanel.display(w);
			}
			gui.setVisible(true);
		} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
		} catch (PatternFormatException pe){
				System.out.println(pe.getDescription());
		} catch (CommandLineException cle) {
				System.out.println(cle.getMessage());
		} catch (NumberFormatException nfe){
				System.out.println("Error: number required " + nfe.getMessage());
		}
	}

}
