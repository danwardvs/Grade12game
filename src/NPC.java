import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
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
	Box LeftThigh;
	Box LeftShin;
	Box LeftFoot;
	
	Box RightThigh;
	Box RightShin;
	Box RightFoot;
	
	Box RightUpperArm;
	Box RightForearm;
	Box RightHand;
	
	Box LeftUpperArm;
	Box LeftForearm;
	Box LeftHand;
	
	Box Neck;
	Box Head;
	
	
	
	public NPC(World newWorld, Character newCharacter, WorldController newWorldController, String newType, Boolean newDirection, BodyType newBodyType, boolean newIsSensor, float newX, float newY, float newWidth,
			float newHeight, float newAngle, float newR, float newG, float newB, float newA) {
		super(newWorld, newBodyType, newIsSensor, newX, newY, newWidth, newHeight, newAngle, newR, newG, newB, newA);
		// TODO Auto-generated constructor stub
		gameCharacter = newCharacter;
		gameWorld = newWorld;
		type = newType;
		gameController = newWorldController;
		direction=newDirection;
		
		
		LeftThigh = createBodyPart(body,newX-0.25f,newY-1f,0.2f,0.5f,newX-0.25f,newY-0.5f,-0.2f,0);
		LeftShin = createBodyPart(LeftThigh.getBody(),newX-0.25f,newY-2f,0.2f,0.5f,newX-0.25f,newY-1.5f,-0.2f,0);
		LeftFoot = createBodyPart(LeftShin.getBody(),newX-0.5f,newY-2.5f,0.75f,0.2f,newX-0.25f,newY-2.5f,-0.2f,0);

		RightThigh = createBodyPart(body,newX+0.25f,newY-1f,0.2f,0.5f,newX+0.25f,newY-0.5f,0,0.2f);
		RightShin = createBodyPart(RightThigh.getBody(),newX+0.25f,newY-2f,0.2f,0.5f,newX+0.25f,newY-1.5f,0,0.2f);
		RightFoot = createBodyPart(RightShin.getBody(),newX+0.5f,newY-2.5f,0.75f,0.2f,newX+0.25f,newY-2.5f,0,0.2f);
		
		RightUpperArm = createBodyPart(body,newX-0.75f,newY,0.2f,0.5f,newX-0.75f,newY+0.25f,false);
		RightForearm = createBodyPart(RightUpperArm.getBody(),newX-0.75f,newY-1f,0.2f,0.5f,newX-0.75f,newY-0.25f,false);
		RightHand = createBodyPart(RightForearm.getBody(),newX-0.75f,newY-1.5f,0.3f,0.3f,newX-0.75f,newY-1.25f,false);
		
		LeftUpperArm = createBodyPart(body,newX+0.75f,newY,0.2f,0.5f,newX+0.75f,newY+0.25f,false);
		LeftForearm = createBodyPart(LeftUpperArm.getBody(),newX+0.75f,newY-1f,0.2f,0.5f,newX+0.75f,newY-0.25f,false);
		LeftHand = createBodyPart(LeftForearm.getBody(),newX+0.75f,newY-1.5f,0.3f,0.3f,newX+0.75f,newY-1.25f,false);
		
		Neck = createBodyPart(body,newX,newY+0.75f,0.3f,0.2f,newX,newY+0.75f,true);
		
		Head = createBodyPart(Neck.body,newX,newY+1.25f,0.4f,0.4f,newX,newY+1.25f,true);
		
		
       
        
		
	}
	public Box createBodyPart(Body newBody,float newX, float newY,float newWidth, float newHeight, float newAnchorX, float newAnchorY, float newLowerLimit, float newUpperLimit){
		
		Box newBox = new Box(gameWorld,BodyType.DYNAMIC,false,newX,newY,newWidth,newHeight,0,0,1,0,1);
		//newBox.body.setAngularDamping(1000000000);
		//newBox.body.setLinearDamping(10);
		gameController.createBox(newBox);
		
		Vec2 Anchor = new Vec2(newAnchorX,newAnchorY);
		
		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.initialize(newBody, newBox.body, Anchor);
        jointDef.collideConnected = false;
        jointDef.enableLimit=true;
        jointDef.upperAngle=newUpperLimit;
        jointDef.lowerAngle=newLowerLimit;
        jointDef.referenceAngle=0;
    
        
        gameWorld.createJoint(jointDef);

        

		return newBox;	
		
	}
	
	public Box createBodyPart(Body newBody,float newX, float newY,float newWidth, float newHeight, float newAnchorX, float newAnchorY, boolean newLimits){
		
		Box newBox = new Box(gameWorld,BodyType.DYNAMIC,false,newX,newY,newWidth,newHeight,0,0,1,0,1);
		//newBox.body.setAngularDamping(1000000000);
		//newBox.body.setLinearDamping(10);
		gameController.createBox(newBox);
		
		Vec2 Anchor = new Vec2(newAnchorX,newAnchorY);
		
		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.initialize(newBody, newBox.body, Anchor);
        jointDef.collideConnected = false;
        jointDef.enableLimit=newLimits;
        jointDef.upperAngle=0;
        jointDef.lowerAngle=0;
        jointDef.referenceAngle=0;
    
        
        gameWorld.createJoint(jointDef);

        

		return newBox;	
		
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
			
			//itemContactList = body.getContactList();
			
			
			for (ContactEdge ce = body.getContactList(); ce != null; ce = ce.next){
			     if (ce.other == gameCharacter.body && ce.contact.isTouching()){
			    	 
	
			    
			     }
			}
		
		
	
			
		
		return 0;
		
	}

}
