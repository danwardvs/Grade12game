import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Level {
	
	World gameWorld;
	WorldController gameController;
	Character gameCharacter;
	MouseHandler gameMouse;
	String object_type;
	float x;
	float y;
	float width;
	float height;
	float angle;
	float r;
	float g;
	float b;
	String body_type;
	String itemtype;
	String directionString;
	Boolean direction=false;
	
	
	
	
	public Level(WorldController newWorldController, World newWorld, MouseHandler newMouse, Character newCharacter){
		gameWorld = newWorld;
		gameMouse = newMouse;
		gameController = newWorldController;
		gameCharacter = newCharacter;
	}
	
	public void load_level(String newLevelPath){
		try {

			File fXmlFile = new File(newLevelPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
					
			doc.getDocumentElement().normalize();
					
			NodeList nList = doc.getElementsByTagName("object");
					
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
						
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					object_type = (eElement.getAttribute("type"));
					x = Float.valueOf(eElement.getElementsByTagName("x").item(0).getTextContent());
					y = Float.valueOf(eElement.getElementsByTagName("y").item(0).getTextContent());
					width = Float.valueOf(eElement.getElementsByTagName("width").item(0).getTextContent());
					height = Float.valueOf(eElement.getElementsByTagName("height").item(0).getTextContent());
					angle = Float.valueOf(eElement.getElementsByTagName("angle").item(0).getTextContent());
					r = Float.valueOf(eElement.getElementsByTagName("r").item(0).getTextContent());
					g = Float.valueOf(eElement.getElementsByTagName("g").item(0).getTextContent());
					b = Float.valueOf(eElement.getElementsByTagName("b").item(0).getTextContent());
					body_type = (eElement.getElementsByTagName("bodytype").item(0).getTextContent());
					if(object_type.equals("Item")){
						itemtype = (eElement.getElementsByTagName("itemtype").item(0).getTextContent());
						
						if(itemtype.equals("Enemy")){
							directionString = (eElement.getElementsByTagName("direction").item(0).getTextContent());
							if(directionString.equals("Right"))
								direction=true;
							else
								direction=false;
						}
					}


					
					if(object_type.equals("Box")){
						if(body_type.equals("KINEMATIC")){
							gameController.createBox(new Box(gameWorld,BodyType.KINEMATIC,false,x,y,width,height,angle,r,g,b,1));
						}
						if(body_type.equals("DYNAMIC")){
							gameController.createBox(new Box(gameWorld,BodyType.DYNAMIC,false,x,y,width,height,angle,r,g,b,1));
						}
					}
					if(object_type.equals("Character")){
			
						if(body_type.equals("DYNAMIC")){
							gameCharacter = new Character(gameController,gameWorld,gameMouse,false,x,y,0.5f,0.7f,angle,r,g,b,1);
							gameController.createCharacter(gameCharacter);
						}
					}
					if(object_type.equals("NPC")){
					
					
						gameController.createNPC(new NPC(gameWorld,gameCharacter,gameController,itemtype,direction,BodyType.DYNAMIC,false,x,y,0.5f,0.7f,angle,r,g,b,1));
						
					}

				}
			}
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	}

}
