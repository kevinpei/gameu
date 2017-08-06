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

import code.constants.Constants;
import code.file_management.FileManager;
import code.game_mechanics.Ability;
import code.game_mechanics.DamageAbility;
import code.game_mechanics.StatDamageAbility;
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
    
    /*
     * A function to initialize a GameCharacter object using the XML element
     * containing its information.
     */
    public static GameCharacter initCharacter(Element root) {
    	GameCharacter character;
    	if (root.getTagName().compareTo("enemy") == 0) {
    		character = new Enemy();
    		((Enemy)character).width = Integer.parseInt(XMLReader.getByTagName(root, "width"));
    	} else {
    		character = new PlayerCharacter();
    	}
    	character.name = XMLReader.getByTagName(root, "name");
    	character.description = XMLReader.getByTagName(root, "description");
    	character.img = XMLReader.getByTagName(root, "img");
    	Element statistics = (Element) root.getElementsByTagName("statistics").item(0);
    	String[] stats = XMLReader.getByTags(Constants.stats, statistics);
    	for (int i = 0; i < stats.length; i++) {
    		character.stats.get(Constants.stats[i])[0] = Integer.parseInt(stats[i]);
    		character.stats.get(Constants.stats[i])[1] = character.stats.get(Constants.stats[i])[0];
    	}
    	String[] points = XMLReader.getByTags(Constants.points, statistics);
    	for (int i = 0; i < points.length; i++) {
    		character.points.get(Constants.points[i])[0] = Integer.parseInt(points[i]);
    		character.points.get(Constants.points[i])[1] = character.points.get(Constants.points[i])[0];
    		character.points.get(Constants.points[i])[2] = character.points.get(Constants.points[i])[0];
    	}
    	String[] finesse = XMLReader.getByTags(Constants.finesse, statistics);
    	for (int i = 0; i < finesse.length; i++) {
    		character.finesse.get(Constants.finesse[i])[0] = Integer.parseInt(finesse[i]);
    		character.finesse.get(Constants.finesse[i])[1] = character.finesse.get(Constants.finesse[i])[0];
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
    		content[i] = XMLReader.getByTagName(root, tags[i]);
    	}
    	return content;
    }
    
    /*
     * A function to return the string content of the first appearance of the given tag
     * in the given element.
     */
    public static String getByTagName(Element root, String tag) {
    	return root.getElementsByTagName(tag).item(0).getTextContent();
    }
    
    /*
     * A function to initialize an ability based on its type.
     */
    public static Ability initAbility(Element root) {
    	Ability ability;
    	if (XMLReader.getByTagName(root, "type").compareToIgnoreCase("Stat-Based") == 0) {
    		ability = XMLReader.initStatDamageAbility(root);
    	} else {
    		return null;
    	}
    	if (ability instanceof DamageAbility) {
    		((DamageAbility)ability).hits = Integer.parseInt(XMLReader.getByTagName(root, "hits"));
    		((DamageAbility)ability).variance = Double.parseDouble(XMLReader.getByTagName(root, "variance"));
    		((DamageAbility)ability).accuracy = Integer.parseInt(XMLReader.getByTagName(root, "accuracy"));
    		((DamageAbility)ability).critChance = Integer.parseInt(XMLReader.getByTagName(root, "critChance"));
    		((DamageAbility)ability).element = XMLReader.getByTagName(root, "element");
    	}
    	ability.name = XMLReader.getByTagName(root, "name");
    	ability.description = XMLReader.getByTagName(root, "description");
    	ability.HP = Integer.parseInt(XMLReader.getByTagName(root, "HP"));
    	ability.MP = Integer.parseInt(XMLReader.getByTagName(root, "MP"));
    	ability.AP = Integer.parseInt(XMLReader.getByTagName(root, "AP"));
    	return ability;
    }
    
    /*
     * A function to initialize a stat-based damage ability.
     */
    public static StatDamageAbility initStatDamageAbility(Element root) {
    	String[] attackStats = XMLReader.getByTagName(root, "attackStats").split(",");
		String[] attackMultipliersString = XMLReader.getByTagName(root, "attackMultipliers").split(",");
		double[] attackMultipliers = new double[attackMultipliersString.length];
		for (int i = 0; i < attackMultipliersString.length; i++) {
			attackMultipliers[i] = Double.parseDouble(attackMultipliersString[i]);
		}
		String[] defenseStats = XMLReader.getByTagName(root, "defenseStats").split(",");
		String[] defenseMultipliersString = XMLReader.getByTagName(root, "defenseMultipliers").split(",");
		double[] defenseMultipliers = new double[defenseMultipliersString.length];
		for (int i = 0; i < defenseMultipliersString.length; i++) {
			defenseMultipliers[i] = Double.parseDouble(defenseMultipliersString[i]);
		}
		return new StatDamageAbility(attackStats, attackMultipliers, defenseStats, defenseMultipliers);
    }
    
}
