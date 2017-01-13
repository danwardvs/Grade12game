import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.ContactEdge;

public class NPC extends Box {
	
	ContactEdge itemContactList;
	Character gameCharacter;
	World gameWorld;
	String type;
	WorldController gameController;
	int shootTimer;
	boolean alive=true;
	boolean direction=false;

	
	public NPC(World newWorld, Character newCharacter, WorldController newWorldController, String newType, Boolean newDirection, BodyType newBodyType, boolean newIsSensor, float newX, float newY, float newWidth,
			float newHeight, float newAngle, float newR, float newG, float newB, float newA) {
		super(newWorld, newBodyType, newIsSensor, newX, newY, newWidth, newHeight, newAngle, newR, newG, newB, newA);
		// TODO Auto-generated constructor stub
		gameCharacter = newCharacter;
		gameWorld = newWorld;
		type = newType;
		gameController = newWorldController;
		direction=newDirection;
	}
	
	public void createProjectile(float newSpeed, float newAngle, float newX, float newY){
		Projectile newProjectile = new Projectile(gameWorld,BodyType.DYNAMIC,gameCharacter,"Enemy",false,getX()+newX,getY()+newY,0.2f,0.2f,0,1,0f,0f,1,960);
		newProjectile.applyLinearImpulse(newSpeed, newAngle);
		gameController.createProjectile(newProjectile);
	}
	public String getType(){
		return type;
	}
	
	public int update(){
		itemContactList = body.getContactList();
		
		
			for (ContactEdge ce = body.getContactList(); ce != null; ce = ce.next){
			     if (ce.other == gameCharacter.body && ce.contact.isTouching()){
			    	 
	
			    
			     }
			}
		
		
	
			
		
		return 0;
		
	}

}
