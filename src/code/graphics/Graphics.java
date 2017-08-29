package code.graphics;

import java.io.File;

import code.file_management.FileManager;
import code.game_mechanics.characters.Enemy;
import code.game_mechanics.characters.GameCharacter;
import code.game_mechanics.characters.PlayerCharacter;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Graphics {
	
    /*
     * A function to add a given node to a given group at the given x and y position. Used
     * for graphical purposes.
     */
    public static void addToGroup(Group group, Node node, double xpos, double ypos) {
    	group.getChildren().add(node);
    	node.setLayoutX(xpos);
    	node.setLayoutY(ypos);
    }
    
    /*
     * A function to draw a background. If it isn't the exact size of the window, it is
     * scaled up and centered appropriately so that it fits.
     */
    public static void drawBackground(String bg, AnchorPane graphics) {
    	Image bgIMG = FileManager.getBackground(bg);
    	Canvas background = new Canvas(graphics.getWidth(), graphics.getHeight());
    	GraphicsContext gc = background.getGraphicsContext2D();
    	if (graphics.getWidth() / bgIMG.getWidth() > graphics.getHeight() / bgIMG.getHeight()) {
    		double scale = graphics.getWidth() / bgIMG.getWidth();
    		gc.drawImage(bgIMG, 0, (graphics.getHeight() - bgIMG.getHeight() * scale) / 2,
    				bgIMG.getWidth() * scale, bgIMG.getHeight() * scale);
    	} else {
    		double scale = graphics.getHeight() / bgIMG.getHeight();
    		gc.drawImage(bgIMG, (graphics.getWidth() - bgIMG.getWidth() * scale) / 2, 0,
    				bgIMG.getWidth() * scale, bgIMG.getHeight() * scale);
    	}
    	graphics.getChildren().add(background);
    	background.toBack();
    }
    
    /*
     * Function to draw a canvas given an Image and its scale. Creates a new canvas object
     * at the appropriate size, draw the image, and return it.
     */
    public static Canvas drawCanvas(Image img, double scale) {
    	Canvas canvas = new Canvas(img.getWidth() * scale, img.getHeight() * scale);
    	GraphicsContext gc = canvas.getGraphicsContext2D();
    	gc.drawImage(img, 0, 0, img.getWidth() * scale, img.getHeight() * scale);
    	return canvas;
    }
    
    /*
     * A function to draw a generic bar. Can be used to show HP, MP, or AP, and resized as
     * necessary. Useful for showing healthbars for enemies and other bars for players.
     * Takes a canvas as input and redraws the bar based on the new current status.
     */
    public static void drawBar(Canvas bar, Paint color, double currPoints, double maxPoints) {
    	GraphicsContext gc = bar.getGraphicsContext2D();
    	gc.clearRect(0, 0, bar.getWidth(), bar.getHeight());
    	gc.setStroke(Color.BLACK);
    	gc.setLineWidth(2);
    	gc.strokeRect(1, 1, bar.getWidth() - 2, bar.getHeight() - 2);
    	gc.setFill(Color.WHITE);
    	gc.fillRect(2, 2, bar.getWidth() - 4, bar.getHeight() - 4);
    	gc.setFill(color);
    	gc.fillRect(4, 4, (bar.getWidth() - 8) * (currPoints / maxPoints), bar.getHeight() - 8);
    }
    
    /*
     * A function to make a label at the given position with the given text.
     */
    public static Label drawLabel(double xPos, double yPos, Color color, int fontSize, String text) {
    	Label label = new Label(text);
    	label.setFont(new Font(fontSize));
    	label.setTextFill(color);
    	label.setAlignment(Pos.CENTER_LEFT);
    	label.setLayoutX(xPos);
    	label.setLayoutY(yPos);
    	label.toFront();
    	return label;
    }
    
    /*
     * Function to make the given Node visible
     */
    public static void makeVisible(Object element) {
    	((Node) element).setVisible(true);
    	((Node) element).toFront();
    }
   
    /*
     * Function to make the given Node invisible
     */
    public static void makeInvisible(Object element) {
    	((Node) element).setVisible(false);
    }
    
    public static void hitCharacter(GameCharacter character, int amount) {
    	Graphics.drawBar(character.pointBars[0], Color.RED, character.points.get("HP")[2], character.points.get("HP")[1]);
    	//Creates a new animation timeline
    	//Sets the enemy graphics to blink for 0.8 seconds.
    	Text damage = Animations.dealDamage(amount);
    	character.graphics.getChildren().add(damage);
    	damage.setLayoutX(character.portrait.getLayoutX() + 0.5 * character.portrait.getWidth());
    	damage.setLayoutY(character.portrait.getLayoutY() + 0.5 * character.portrait.getHeight() - 20);
    	Animations.flashObject(character.portrait, 4);
    	Animations.hideObject(damage, 1200);
    	Animations.playAnimations();
    }
}
