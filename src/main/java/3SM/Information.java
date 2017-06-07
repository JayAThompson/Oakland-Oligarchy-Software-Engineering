import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Information extends JPanel{
	
	ArrayList<JLabel> labels = new ArrayList<>();
	
	Information(ArrayList<Player> players){
		for(Player player : players){
			JLabel name = new JLabel("<html><u>"+player.getName()+"</u></html>", SwingConstants.CENTER);
			name.setFont(new Font("Calibri", Font.BOLD, 15));
			labels.add(name);
			JLabel money = new JLabel("MONEY: "+ player.getMoney(), SwingConstants.CENTER);
			labels.add(money);
			JLabel owned = new JLabel("PROPERTIES: ", SwingConstants.CENTER);
			labels.add(owned);
		}
		
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setPreferredSize(new Dimension(285, 1000));
		this.setLayout(new GridLayout(30, 1));
		
		for(JLabel label : labels){
			this.add(label);
		}
	}
}