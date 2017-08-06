package code.graphics.message_box;

import java.util.ArrayList;

import javafx.scene.Group;

public class Menu extends MessageBox{

	ArrayList<Group> buttons;
	
	public Menu(double xPos, double yPos, double width, double height) {
		super(xPos, yPos, width, height);
		// TODO Auto-generated constructor stub
		buttons = new ArrayList<Group>();
	}
	
	public void addButton(Group button) {
		this.addGraphic(button);
		buttons.add(button);	
	}

}
