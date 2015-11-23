package newMud;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CommandListener implements ActionListener {

	private JTextArea out;
	private GameCharacter mainGuy;
	private JLabel imLabel;
	private ImageIcon roomPic;
	private JTextArea statsList;
	private Pattern commandRegex = Pattern.compile("(\\S+)(\\s+)(.+)");
	private String commandType = null;
	private String commandValue = null;
	
	public CommandListener(JTextArea out, GameCharacter pc, JLabel label, JTextArea sList){
		this.out = out;
		mainGuy = pc;
		imLabel = label;
		statsList = sList;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JTextField source = (JTextField) e.getSource();
		String s = source.getText().toLowerCase();
		out.append(s + "\n");
		source.setText("");
		Matcher commandMatcher = commandRegex.matcher(s);
		if(commandMatcher.matches()){
			commandType = commandMatcher.group(1);
			commandValue = commandMatcher.group(3);
		}
		else commandType = s;
		
		switch(commandType){
		case("start"):
			out.append("Enjoy your stay.");
			break;
		case("exit"):
			out.append("Goodbye \n");
			System.exit(1);		//close the all java stuff
			break;
		case("go"):
			switch(commandValue){
			case("north"):
				if(mainGuy.getLocation().getExits()[0] != null) mainGuy.goNorth();
				else out.append("You can not go in that direction." + "\n");
				break;
			case("south"):
				if(mainGuy.getLocation().getExits()[1] != null) mainGuy.goSouth();
				else out.append("You can not go in that direction." + "\n");
				break;
			case("east"):
				if(mainGuy.getLocation().getExits()[2] != null) mainGuy.goEast();
				else out.append("You can not go in that direction." + "\n");
				break;
			case("west"):
				if(mainGuy.getLocation().getExits()[3] != null) mainGuy.goWest();
				else out.append("You can not go in that direction." + "\n");
				break;
			case("up"):
				if(mainGuy.getLocation().getExits()[4] != null) mainGuy.goUp();
				else out.append("You can not go in that direction." + "\n");
				break;
			case("down"):
				if(mainGuy.getLocation().getExits()[5] != null) mainGuy.goDown();
				else out.append("You can not go in that direction." + "\n");
				break;
			default:
				out.append("That is not a valid direction." + "\n");
			}
			break;
		case("get"):
			if((mainGuy.getLocation()).checkItem(commandValue)){
				mainGuy.pickUp((mainGuy.getLocation()).returnItem(commandValue));
				out.append("Got it!" + "\n");
			}
			else out.append("That item is not in the room." + "\n");
			break;
		case("drop"):
			if (mainGuy.checkItem(commandValue)){						
				mainGuy.drop(mainGuy.returnItem(commandValue));
				out.append("Tossed " + commandValue + ", hope you dont need it later." + "\n");
			}
			else out.append("You can't drop what you dont have." + "\n");
			break;
		default:
			out.append("That is not a valid command." + "\n");
			break;
		}
		
		//NewCOde
		String list = mainGuy.getName() + " Stats \n";
		list = list +  "Score: " + mainGuy.getScore() + " \n";
		String inventory = "Inventory: \n";
		for(int i = 0; i<mainGuy.getInventory().size();i++){
			inventory = inventory + mainGuy.getInventory().get(i).getName() +"  ";
			}
		list = list + inventory;
		statsList.setText(list);	//sets the stats list (inventory,score, ect.) at the top of the GUI
		
		
		out.selectAll();//makes console auto scroll to the bottom when entering a new command
		//EndNewCode
	

		roomPic = mainGuy.getLocation().getImage();
		Image img = roomPic.getImage();
		Image newimg = img.getScaledInstance(230, 310, java.awt.Image.SCALE_SMOOTH);
		roomPic = new ImageIcon(newimg);
		imLabel.setIcon(roomPic);
		
		
		out.append((mainGuy.getLocation()).getDescription() +"\n");
		out.append("The room contains the following items: ");
		out.append(mainGuy.getLocation().getInventory().toString() + "\n");
		out.append(mainGuy.getLocation().listExits() + "\n");
		out.append("You are holding the following items: ");	  		  	 
		out.append(mainGuy.getInventory().toString() + "\n");
		
	}
}
