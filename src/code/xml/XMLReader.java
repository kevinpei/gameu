package code.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import code.file_management.FileManager;
import code.game_mechanics.characters.Enemy;
import code.game_mechanics.characters.GameCharacter;
import code.game_mechanics.characters.PlayerCharacter;

public class XMLReader {
	
	/*
	 * A document builder factory and document builder to create a new Document object given
	 * a file.
	 */
	static DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	static DocumentBuilder docBuilder;
    
    /*
     * A function to return a Document object containing the parsed XML file. The argument
     * should be just the name without the .xml extension. If the specified file does not
     * exist, then return null.
     */
    public static Document createDocument(String name) {
       	Document doc;
   		try {
   			docBuilder = docBuilderFactory.newDocumentBuilder();
   			doc = docBuilder.parse(new File(FileManager.getResource("xml") + name + ".xml"));
   		} catch (SAXException | IOException | ParserConfigurationException e) {
   			doc = null;
   		}
   		return doc;
    }
    
    /*
     * A function to return an Element object of the given id in a given doc.
     */
    public static Element getElementByID(Document doc, int id) {
    	return (Element)doc.getElementById(id + "");
    }
    
    public static NodeList getByTagName(Element root, String tag) {
    	return root.getElementsByTagName(tag);
    }
    
    /*
     * A function to initialize a GameCharacter object using the XML element
     * containing its information.
     */
    public static GameCharacter initCharacter(Element root) {
    	GameCharacter character;
    	if (root.getTagName().compareTo("enemy") == 0) {
    		character = new Enemy();
    		((Enemy)character).width = Integer.parseInt(
    				root.getElementsByTagName("width").item(0).getTextContent());
    	} else {
    		character = new PlayerCharacter();
    	}
    	character.name = root.getElementsByTagName("name").item(0).getTextContent();
    	character.description = root.getElementsByTagName("description").item(0).getTextContent();
    	character.img = root.getElementsByTagName("img").item(0).getTextContent();
    	character.icon = root.getElementsByTagName("icon").item(0).getTextContent();
    	Element statistics = (Element) root.getElementsByTagName("statistics").item(0);
    	String[] stats = {"attack", "intelligence", "speed", "finesse", "defense", "mdefense",
    			"maxHP", "maxMP", "maxAP", "accuracy", "evasion", "critChance"};
    	stats = XMLReader.getByTags(stats, statistics);
    	for (int i = 0; i < 6; i++) {
    		character.baseStats[i] = Integer.parseInt(stats[i]);
    		character.currStats[i] = character.baseStats[i];
    	}
    	for (int i = 0; i < 3; i++) {
    		character.basePoints[i] = Integer.parseInt(stats[i+6]);
    		character.currPoints[i] = character.basePoints[i];
    		character.finesseStats[i] = Integer.parseInt(stats[i+9]);
    	}
    	return character;
    }
    
    /*
     * Takes an array of tags and the root to check and returns a String array containing
     * the text content of all of the tags in the array in the xml file.
     */
    public static String[] getByTags(String[] tags, Element root) {
    	String[] content = new String[tags.length];
    	for (int i = 0; i < tags.length; i++) {
    		content[i] = root.getElementsByTagName(tags[i]).item(0).getTextContent();
    	}
    	return content;
    }
    
}
