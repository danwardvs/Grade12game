
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.ContactEdge;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.JointDef;
import org.jbox2d.dynamics.joints.JointEdge;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.dynamics.joints.WeldJointDef;
import java.util.Random;



public class Character extends Box {
	
	float movement_speed=50;
	World gameWorld;
	WorldController gameController;
	MouseHandler gameMouse;
	int bullet_time;
	int bullet_time_delay=300;
	float x;
	float y;
	boolean hasDied = false;
	boolean direction;

	boolean alive=true;
	int deathDirection;
	
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
	
	Joint LeftHip = new Joint();
	Joint LeftKnee = new Joint();
	Joint LeftAnkle = new Joint();
	
	Joint RightHip = new Joint();
	Joint RightKnee = new Joint();
	Joint RightAnkle = new Joint();
	
	Joint RightShoulder = new Joint();
	Joint RightElbow = new Joint();
	Joint RightWrist = new Joint();
	
	Joint LeftShoulder = new Joint();
	Joint LeftElbow = new Joint();
	Joint LeftWrist = new Joint();
	
	Joint LowerNeck = new Joint();
	Joint UpperNeck = new Joint();
	
	
	
	Random rn = new Random();

	public Character(WorldController newWorldController, World newWorld, MouseHandler newMouse, boolean newIsSensor, float newX, float newY, float newWidth, float newHeight,
		
		float newAngle, float newR, float newG, float newB, float newA) {
		super(newWorld, BodyType.DYNAMIC, newIsSensor, newX, newY, newWidth, newHeight, newAngle, newR, newG, newB, newA);
		gameWorld = newWorld;
		gameMouse = newMouse;
		gameController = newWorldController;
		//body.setFixedRotation(true);
		
		LeftThigh = createBodyPart(LeftHip,body,newX-0.25f,newY-1f,0.2f,0.5f,newX-0.25f,newY-0.5f,true,-0.2f,0);
		LeftShin = createBodyPart(LeftKnee,LeftThigh.getBody(),newX-0.25f,newY-2f,0.2f,0.5f,newX-0.25f,newY-1.5f,true,-0.2f,0);
		LeftFoot = createBodyPart(LeftAnkle,LeftShin.getBody(),newX-0.5f,newY-2.5f,0.75f,0.2f,newX-0.25f,newY-2.5f,true,-0.2f,0);

		RightThigh = createBodyPart(RightHip,body,newX+0.25f,newY-1f,0.2f,0.5f,newX+0.25f,newY-0.5f,true,0,0.2f);
		RightShin = createBodyPart(RightKnee,RightThigh.getBody(),newX+0.25f,newY-2f,0.2f,0.5f,newX+0.25f,newY-1.5f,true,0,0.2f);
		RightFoot = createBodyPart(RightAnkle,RightShin.getBody(),newX+0.5f,newY-2.5f,0.75f,0.2f,newX+0.25f,newY-2.5f,true,0,0.2f);
		
		RightUpperArm = createBodyPart(RightShoulder,body,newX-0.75f,newY,0.2f,0.5f,newX-0.75f,newY+0.25f,false,-3.0f,2.0f);
		RightForearm = createBodyPart(RightElbow,RightUpperArm.getBody(),newX-0.75f,newY-0.5f,0.2f,0.5f,newX-0.75f,newY-0.25f,false,0,0);
		RightHand = createBodyPart(RightWrist,RightForearm.getBody(),newX-0.75f,newY-1f,0.3f,0.3f,newX-0.75f,newY-0.75f,false,0,0);
		
		LeftUpperArm = createBodyPart(LeftShoulder,body,newX+0.75f,newY,0.2f,0.5f,newX+0.75f,newY+0.25f,false,0,0);
		LeftForearm = createBodyPart(LeftElbow,LeftUpperArm.getBody(),newX+0.75f,newY-1f,0.2f,0.5f,newX+0.75f,newY-0.25f,false,0,0);
		LeftHand = createBodyPart(LeftWrist,LeftForearm.getBody(),newX+0.75f,newY-1.5f,0.3f,0.3f,newX+0.75f,newY-1.25f,false,0,0);
		
		Neck = createBodyPart(LowerNeck,body,newX,newY+0.75f,0.3f,0.2f,newX,newY+0.75f,true,0,0);
		
		Head = createBodyPart(UpperNeck,Neck.body,newX,newY+1.25f,0.4f,0.4f,newX,newY+1.25f,true,0,0);
       
	}
	
	//This function creates a body part based on a revolutejoint that is passed
	public Box createBodyPart(Joint newJoint, Body newBody,float newX, float newY,float newWidth, float newHeight, float newAnchorX, float newAnchorY,boolean newLimits, float newLowerLimit, float newUpperLimit){
		
		Box newBox = new Box(gameWorld,BodyType.DYNAMIC,false,newX,newY,newWidth,newHeight,0,0,1,0,1);
		//newBox.body.setAngularDamping(1000000000);
		//newBox.body.setLinearDamping(10);
		gameController.createBox(newBox);
		
		Vec2 Anchor = new Vec2(newAnchorX,newAnchorY);
		
		RevoluteJointDef jd = new RevoluteJointDef();
		jd.initialize(newBody, newBox.body, Anchor);
		jd.enableMotor=true;
		jd.maxMotorTorque=0;
		jd.motorSpeed=0;
        jd.collideConnected = false;
        jd.enableLimit=newLimits;
        jd.upperAngle=newUpperLimit;
        jd.lowerAngle=newLowerLimit;
        jd.referenceAngle=0;
   

        newJoint.setJoint((RevoluteJoint) gameWorld.createJoint(jd));
    

        

		return newBox;	
		
	}

	
	
	
	
	public void createProjectile(float newSpeed, float newAngle, float newX, float newY){
		Projectile newProjectile = new Projectile(gameWorld,BodyType.DYNAMIC,this,"Player",false,getX()+newX,getY()+newY,0.2f,0.2f,0,0.7f,0.7f,0.7f,1,960);
		newProjectile.applyLinearImpulse(newSpeed, newAngle);
		gameController.createProjectile(newProjectile);
	}
	
	public void setState(boolean newAlive){
		alive = newAlive;
	}
	public void draw(){
        Vec2 position = body.getPosition();
        float angle = body.getAngle();
        drawRect(angle,position.x*20,position.y*20,width*40,height*40,r,g,b,a);
       
	}
	
	public void update(int delta){
		
		//System.out.println(RightShoulder.bodyA);
		
		float x = body.getPosition().x;
		float y = body.getPosition().y;
		
		if(x<-22){
			alive=false;
			deathDirection=1;
		}
		if(x>22){
			alive=false;
			deathDirection=2;
		}
		
		if(!alive){
			r=1;
			g=0;
			b=0;
		}
		
		if(body.getLinearVelocity().y<-15){
			
			
			//body.setFixedRotation(false);
			//body.applyTorque(50);
			//alive=false;
			
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_R)){
			 gameController.clearWorld();
		 }
		
		 if(alive){
			 
			 bullet_time+=delta;
			 
			 if(gameMouse.getLeftMouseDown())
				 gameController.createSkeleton(new Skeleton(gameWorld,gameController,gameMouse.getWorldX(),gameMouse.getWorldY(),0.5f,0.5f,0.5f));

			 
			 if (Keyboard.isKeyDown(Keyboard.KEY_W)){
				 
				 
				 gameWorld.setGravity(new Vec2(gameWorld.getGravity().x,gameWorld.getGravity().y+0.5f));

			 }
			 
			 if (Keyboard.isKeyDown(Keyboard.KEY_A)){
				 gameWorld.setGravity(new Vec2(gameWorld.getGravity().x-0.5f,gameWorld.getGravity().y));

			 }
			 
			 if (Keyboard.isKeyDown(Keyboard.KEY_D)){
				 gameWorld.setGravity(new Vec2(gameWorld.getGravity().x+0.5f,gameWorld.getGravity().y));

			 }
			 
			 if (Keyboard.isKeyDown(Keyboard.KEY_S)){
				 gameWorld.setGravity(new Vec2(gameWorld.getGravity().x,gameWorld.getGravity().y-0.5f));

			 }
			 if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
				System.out.println(RightShoulder);
				RightShoulder.getJoint().enableMotor(true);
				 RightShoulder.getJoint().setMotorSpeed(-10);
					
					
				
			 }
			 if(Keyboard.isKeyDown(Keyboard.KEY_E)){
					System.out.println(RightShoulder);
					 RightShoulder.getJoint().enableMotor(false);
					 //gameWorld.destroyJoint(RightShoulder);
						
						
					
				 }
			 
			 if(Keyboard.isKeyDown(Keyboard.KEY_G)){
					body.applyAngularImpulse(1);
						
						
					
				 }
				 if(Keyboard.isKeyDown(Keyboard.KEY_F)){
					 body.applyAngularImpulse(-1f);
							
							
						
					 }
				 
			 
			 
			 
			 if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D) ){
				 applyImpulse(movement_speed,0);
				 direction=true;
			 }
			 if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A) ){
					applyImpulse(-movement_speed,0);
					direction=false;
			 }
			 //System.out.println(Feet);
			 if (Keyboard.isKeyDown(Keyboard.KEY_UP)){
				 if(body.getLinearVelocity().y<=0.1f && body.getLinearVelocity().y>=-0.1f)
					applyImpulse(0,6000);
			 }
			 
			 
			 if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && bullet_time>=bullet_time_delay){
				bullet_time=0;
				if(direction){
					createProjectile(300,20,2,0.5f);
					createProjectile(300,0f,2,0);
					createProjectile(300,-20f,2,-0.5f);
					body.applyForceToCenter(new Vec2(-200,0));
				}
				else{
					createProjectile(-300,20,-2,0.5f);
					createProjectile(-300,0f,-2,0);
					createProjectile(-300,-20f,-2,-0.5f);
					body.applyForceToCenter(new Vec2(200,0));
				}
			 }
		 }
		 if(alive==false && hasDied==false){
				for(int i = 0; i<25; i++){
					Vec2 splatterVelocity = new Vec2(10,0);
					if(deathDirection==0){
						Box newBox = new Box(gameWorld,BodyType.DYNAMIC,false,x,y,0.2f,0.2f,1f,1,0,0,1);
						splatterVelocity = new Vec2(0,0);
						int newX;
						int newY;
						int n = -10 - 10 + 1;
						int randX = rn.nextInt() % n;
						int randY = rn.nextInt() % n;
						newX = -10 + randX;
						newY = -10 + randY;
						splatterVelocity.x = body.getLinearVelocity().x + newX;
						splatterVelocity.y = body.getLinearVelocity().x + newY;
						newBox.body.setLinearVelocity(splatterVelocity);
						gameController.createBox(newBox);
					}
					if(deathDirection==1){
						splatterVelocity = new Vec2(10,0);
						
						int newY = 0;
						int n = -10 - 10 + 1;
						int randY = rn.nextInt() % n;
						newY = -10 + randY;
						splatterVelocity.y = newY;
						
						Box newBox = new Box(gameWorld,BodyType.DYNAMIC,false,x,y,0.2f,0.2f,1f,1,0,0,1);
						newBox.body.setLinearVelocity(splatterVelocity);
						gameController.createBox(newBox);
					}
					if(deathDirection==2){
						splatterVelocity = new Vec2(-10,0);
						int newY = 0;
						int n = -10 - 10 + 1;
						int randY = rn.nextInt() % n;
						newY = -10 + randY;
						splatterVelocity.y = newY;
						Box newBox = new Box(gameWorld,BodyType.DYNAMIC,false,x-0.4f,y,0.2f,0.2f,1f,1,0,0,1);
						newBox.body.setLinearVelocity(splatterVelocity);
						gameController.createBox(newBox);
					}
					
					hasDied=true;
				}
		 }
		 
	}

}
