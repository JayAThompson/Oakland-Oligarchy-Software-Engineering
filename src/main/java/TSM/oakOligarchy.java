package TSM;

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;

public class oakOligarchy{
	
	private final int height = 640;
	private final int width = 1200;
	
	JFrame frame = new JFrame("Oakland Oligarchy");
	JPanel board;
	public oakOligarchy() {
		
		board = new JPanel(new GridBagLayout());
		
		frame.setSize(width, height);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new oakOligarchy();
	}
}