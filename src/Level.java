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
	String object_type;
	float x;
	float y;
	float width;
	float height;
	float angle;
	float r;
	float g;
	float b;
	int character_id;
	String body_type;
	String itemtype;
	
	public Level(WorldController newWorldController, World newWorld){
		gameWorld = newWorld;
		gameController = newWorldController;
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
					r = Float.valueOf(eElement.getElementsByTagName("r").item(0).getTextContent());
					g = Float.valueOf(eElement.getElementsByTagName("g").item(0).getTextContent());
					b = Float.valueOf(eElement.getElementsByTagName("b").item(0).getTextContent());
					
					if(object_type.equals("Box")){
						
						width = Float.valueOf(eElement.getElementsByTagName("width").item(0).getTextContent());
						height = Float.valueOf(eElement.getElementsByTagName("height").item(0).getTextContent());
						angle = Float.valueOf(eElement.getElementsByTagName("angle").item(0).getTextContent());
						body_type = eElement.getElementsByTagName("bodytype").item(0).getTextContent();

						
						if(body_type.equals("KINEMATIC")){
							gameController.createBox(new Box(gameWorld,BodyType.KINEMATIC,false,x,y,width,height,angle,r,g,b,1));
						}
						if(body_type.equals("DYNAMIC")){
							gameController.createBox(new Box(gameWorld,BodyType.DYNAMIC,false,x,y,width,height,angle,r,g,b,1));
						}
					}
					if(object_type.equals("Character")){
						
						character_id = Integer.valueOf(eElement.getElementsByTagName("character_id").item(0).getTextContent());;
				
					
						Character gameCharacter = new Character(gameWorld,gameController,character_id,x,y,r,g,b);
						gameController.createCharacter(gameCharacter,character_id);
					}

				}
			}
		} catch (Exception e) {
    	e.printStackTrace();
    	}
	}

}
