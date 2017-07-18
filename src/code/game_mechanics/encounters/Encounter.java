package code.game_mechanics.encounters;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import code.game_mechanics.characters.Enemy;
import code.music_player.MusicPlayer;
import code.xml.XMLReader;

public class Encounter {
	//An ArrayList to store all the enemies in this encounter.
	ArrayList<Enemy> enemies;
	//A string to store the name of the music for this encounter.
	public String bgm;
	//A string to store the name of the background for this encounter.
	public String bg;
	
	/*
	 * If no bgm or bg are given, then a default bgm and bg are chosen.
	 */
	public Encounter(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
		this.bgm = "Midnight Waltz.mp3";
		this.bg = "forest.jpg";
	}
	
	/*
	 * To instantiate an encounter, you need the enemies, the bgm, and the bg.
	 */
	public Encounter(ArrayList<Enemy> enemies, String bgm, String bg) {
		this(enemies);
		this.bgm = bgm;
		this.bg = bg;
	}
	
	/*
     * A function to create a new encounter given its id. To do so, it reads the .XML
     * file and creates a new enemy object for each enemy in the encounter. Then it
     * sets the encounter arraylist to contain all the enemies. The appropriate
     * bgm is also set.
     */
    public static Encounter InitEncounter(int id) {
    	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    	Document doc = XMLReader.createDocument("encounters");
    	Element encounterInfo = (Element) doc.getElementsByTagName("encounter").item(id);
    	NodeList enemyList = ((Element) encounterInfo.getElementsByTagName("enemies").item(0))
    			.getElementsByTagName("characterid");
    	for (int i = 0; i < enemyList.getLength(); i++) {
    		enemies.add(Encounter.InitEnemy(Integer.parseInt(enemyList.item(i).getTextContent())));
    	}
    	String bgm = encounterInfo.getElementsByTagName("bgm").item(0).getTextContent();
    	String bg = encounterInfo.getElementsByTagName("bg").item(0).getTextContent();
    	MusicPlayer.playBGM(bgm);
  
    	return new Encounter(enemies, bgm, bg);
    }
	
    /*
     * A function to create a new Enemy object given its id. To do so, it reads the .XML
     * file containing enemy information and creates an appropriate enemy object based
     * on the information in the .XML file.
     */
    public static Enemy InitEnemy(int id) {
      	Document doc = XMLReader.createDocument("enemies");
   		Element enemyInfo = (Element)doc.getElementsByTagName("enemy").item(id);
   		Enemy enemy = (Enemy) XMLReader.initCharacter(enemyInfo);
   		return enemy;
	}
    
	//A getter function to return the enemies in this encounter.
	public ArrayList<Enemy> getEnemies() {return enemies;}
	
	//A getter function to return the background in this encounter.
		public String getBG() {return bg;}
}
