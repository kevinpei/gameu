package code.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import code.constants.Constants;
import code.constants.Terms;
import code.file_management.FileManager;
import code.game_mechanics.abilities.Ability;
import code.game_mechanics.abilities.DamageAbility;
import code.game_mechanics.abilities.FixedDamageAbility;
import code.game_mechanics.abilities.StatDamageAbility;
import code.game_mechanics.status_effects.DamageStatusEffect;
import code.game_mechanics.status_effects.StatStatusEffect;
import code.game_mechanics.status_effects.StatusEffect;
import code.game_mechanics.status_effects.VampireStatusEffect;
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
    static XPathFactory xFactory = XPathFactory.newInstance();
    static XPath xpath;
	
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
    public static Element getElementByID(Document doc, String target, int id) {
    	xpath = xFactory.newXPath();
    	try {
			XPathExpression expr = xpath.compile("//" + target + "[@id='" + id + "']");
			Element result = (Element)expr.evaluate(doc, XPathConstants.NODE);
			System.out.println(result.getTagName());
			return result;
		} catch (XPathExpressionException e) {
			return null;
		}
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
    		String[] abilities = XMLReader.getByTagName(root, "abilities").split(",");
    		for (String id : abilities) {
    			((PlayerCharacter)character).abilities.add(Integer.parseInt(id));
    		}
    	}
    	character.name = XMLReader.getByTagName(root, "name");
    	character.description = XMLReader.getByTagName(root, "description");
    	character.img = XMLReader.getByTagName(root, "img");
    	Element statistics = (Element) root.getElementsByTagName("statistics").item(0);
    	String[] stats = XMLReader.getByTags(Terms.stats, statistics);
    	for (int i = 0; i < stats.length; i++) {
    		character.stats.get(Terms.stats[i])[0] = Integer.parseInt(stats[i]);
    		character.stats.get(Terms.stats[i])[1] = character.stats.get(Terms.stats[i])[0];
    	}
    	String[] points = XMLReader.getByTags(Terms.points, statistics);
    	for (int i = 0; i < points.length; i++) {
    		character.points.get(Terms.points[i])[0] = Integer.parseInt(points[i]);
    		character.points.get(Terms.points[i])[1] = character.points.get(Terms.points[i])[0];
    		character.points.get(Terms.points[i])[2] = character.points.get(Terms.points[i])[0];
    	}
    	String[] finesse = XMLReader.getByTags(Terms.finesse, statistics);
    	for (int i = 0; i < finesse.length; i++) {
    		character.finesse.get(Terms.finesse[i])[0] = Integer.parseInt(finesse[i]);
    		character.finesse.get(Terms.finesse[i])[1] = character.finesse.get(Terms.finesse[i])[0];
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
     * in the given element. If the given tag does not exist, return null.
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
    	} else if (XMLReader.getByTagName(root, "type").compareToIgnoreCase("Fixed") == 0){
    		ability = XMLReader.initFixedDamageAbility(root);
    	} else {
    		return null;
    	}
    	if (ability instanceof DamageAbility) {
    		try {
    			((DamageAbility)ability).hits = Integer.parseInt(XMLReader.getByTagName(root, "hits"));
    		} catch (NullPointerException e) {
    			((DamageAbility)ability).hits = 1;
    		}
    		try {
    			((DamageAbility)ability).variance = Double.parseDouble(XMLReader.getByTagName(root, "variance"));
    		} catch (NullPointerException e) {
    			((DamageAbility)ability).variance = 0;
    		}
    		try {
    			((DamageAbility)ability).accuracy = Integer.parseInt(XMLReader.getByTagName(root, "accuracy"));
    		} catch (NullPointerException e) {
    			((DamageAbility)ability).accuracy = 100;
    		}
    		try {
    			((DamageAbility)ability).critChance = Integer.parseInt(XMLReader.getByTagName(root, "critChance"));
    		} catch (NullPointerException e) {
    			((DamageAbility)ability).critChance = 0;
    		}
    		try {
    			((DamageAbility)ability).element = XMLReader.getByTagName(root, "element");
    		} catch (NullPointerException e) {
    			((DamageAbility)ability).element = "None";
    		}
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
    
    /*
     * A function to initialize a fixed damage ability.
     */
    public static FixedDamageAbility initFixedDamageAbility(Element root) {
    	int power, currentPercent, maxPercent;
    	try {
    		power = Integer.parseInt(XMLReader.getByTagName(root, "power"));
    	} catch(NullPointerException e) {
    		power = 0;
    	}
    	try {
    		currentPercent = Integer.parseInt(XMLReader.getByTagName(root, "currentPercent"));
    	} catch(NullPointerException e) {
    		currentPercent = 0;
    	}
    	try {
    		maxPercent = Integer.parseInt(XMLReader.getByTagName(root, "maxPercent"));
    	} catch(NullPointerException e) {
    		maxPercent = 0;
    	}
    	return new FixedDamageAbility(power, currentPercent, maxPercent);
    }
    
    /*
     * A function to initialize a status effect. It determines what the specific status effect
     * type is before setting general fields.
     */
    public static StatusEffect initStatusEffect(Element root) {
    	StatusEffect newStatus;
    	if (XMLReader.getByTagName(root, "type").compareToIgnoreCase(Terms.damageStatusEffect) == 0) {
    		newStatus = initDamageStatusEffect(root);
    	} else if (XMLReader.getByTagName(root, "type").compareToIgnoreCase(Terms.statStatusEffect) == 0) {
    		newStatus = initStatStatusEffect(root);
    	} else if (XMLReader.getByTagName(root, "type").compareToIgnoreCase(Terms.vampireStatusEffect) == 0) {
    		newStatus = initVampireStatusEffect(root);
    	} else {
    		return null;
    	}
    	newStatus.name = XMLReader.getByTagName(root, "name");
    	newStatus.description = XMLReader.getByTagName(root, "description");
    	newStatus.maxMagnitude = Integer.parseInt(XMLReader.getByTagName(root, "maxMagnitude"));
    	newStatus.id = Integer.parseInt(root.getAttribute("id"));
    	return newStatus;
    }
    
    /*
     * A function to initialize a new damage status effect.
     */
    public static DamageStatusEffect initDamageStatusEffect(Element root) {
    	double baseDamage = Double.parseDouble(XMLReader.getByTagName(root, "baseDamage"));
    	double multiplier = Double.parseDouble(XMLReader.getByTagName(root, "exponentialMultiplier"));
    	double variance = Double.parseDouble(XMLReader.getByTagName(root, "damageVariance"));
    	return new DamageStatusEffect(baseDamage, multiplier, variance);
    }
    
    /*
     * A function to initialize a new stat-affecting status effect.
     */
    public static StatStatusEffect initStatStatusEffect(Element root) {
    	String[] stats = XMLReader.getByTagName(root, "stats").split(",");
    	String[] stringMultipliers = XMLReader.getByTagName(root, "multipliers").split(",");
    	double[] multipliers = new double[stringMultipliers.length];
    	for (int i = 0; i < stringMultipliers.length; i++) {
    		multipliers[i] = Double.parseDouble(stringMultipliers[i]);
    	}
    	return new StatStatusEffect(stats, multipliers);
    }
    
    /*
     * A function to initialize a new vampire-like status effect.
     */
    public static VampireStatusEffect initVampireStatusEffect(Element root) {
    	double damageMultiplier = Double.parseDouble(XMLReader.getByTagName(root, "damageMultiplier"));
    	double exponentialMultiplier = Double.parseDouble(XMLReader.getByTagName(root, "exponentialMultiplier"));
    	return new VampireStatusEffect(damageMultiplier, exponentialMultiplier);
    	
    }
}
