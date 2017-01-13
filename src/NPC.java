import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.ContactEdge;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.dynamics.joints.WeldJointDef;

public class NPC extends Box {
	
	ContactEdge itemContactList;
	Character gameCharacter;
	World gameWorld;
	String type;
	WorldController gameController;
	int shootTimer;
	boolean alive=true;
	boolean direction=false;
	Box LeftFoot;
	Box RightFoot;

	
	public NPC(World newWorld, Character newCharacter, WorldController newWorldController, String newType, Boolean newDirection, BodyType newBodyType, boolean newIsSensor, float newX, float newY, float newWidth,
			float newHeight, float newAngle, float newR, float newG, float newB, float newA) {
		super(newWorld, newBodyType, newIsSensor, newX, newY, newWidth, newHeight, newAngle, newR, newG, newB, newA);
		// TODO Auto-generated constructor stub
		gameCharacter = newCharacter;
		gameWorld = newWorld;
		type = newType;
		gameController = newWorldController;
		direction=newDirection;
		
		LeftFoot = new Box(gameWorld,BodyType.DYNAMIC,false,newX-0.25f,newY-newHeight,0.2f,0.5f,0,0,1,0,1);	
		
		Vec2 LeftFootAnchor = new Vec2(newX-0.25f,newY-0.5f);
		gameController.createBox(LeftFoot);
		
		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.initialize(body, LeftFoot.body, LeftFootAnchor);
        jointDef.collideConnected = false;
        gameWorld.createJoint(jointDef);
        
        RightFoot = new Box(gameWorld,BodyType.DYNAMIC,false,newX+0.25f,newY-newHeight,0.2f,0.5f,0,0,1,0,1);	
		
		Vec2 RightFootAnchor = new Vec2(newX+0.25f,newY-0.5f);
		gameController.createBox(RightFoot);
		
		jointDef.initialize(body, RightFoot.body, RightFootAnchor);
        jointDef.collideConnected = false;
        gameWorld.createJoint(jointDef);
		
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
