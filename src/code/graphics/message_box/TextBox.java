package code.graphics.message_box;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TextBox extends MessageBox{

	public Label text;
	
	public TextBox(double xPos, double yPos, double width, double height) {
		super(xPos, yPos, width, height);
		text = new Label();
		text.setMinSize(width - 16, height - 16);
		text.setLayoutX(8);
		text.setLayoutY(8);
		text.setTextFill(Color.WHITE);
		text.setFont(new Font(24));
		text.setAlignment(Pos.TOP_CENTER);
		messageBox.getChildren().add(text);
	}
	
	public void changeText(String text) {
		this.text.setText(text);
	}
	
}
