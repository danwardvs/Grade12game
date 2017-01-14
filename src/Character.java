
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



public class Character extends Skeleton {
	
	float movement_speed=50;
	//World gameWorld;
	//WorldController gameController;
	MouseHandler gameMouse;
	int bullet_time;
	int bullet_time_delay=300;
	float x;
	float y;
	int key_delay;
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

	public Character(World newWorld,WorldController newWorldController, MouseHandler newMouse, float newX, float newY,float newR,float newG,float newB){
		
		super(newWorld,newWorldController,newX, newY,newR,newG,newB);
		//gameWorld = newWorld;
		gameMouse = newMouse;
		//gameController = newWorldController;
		//Core.getBody().setFixedRotation(true);
		
		
       
	}
	
	
	
	public void createProjectile(float newSpeed, float newAngle, float newX, float newY){
		Projectile newProjectile = new Projectile(gameWorld,BodyType.DYNAMIC,this,"Player",false,getX()+newX,getY()+newY,0.2f,0.2f,0,0.7f,0.7f,0.7f,1,960);
		newProjectile.applyLinearImpulse(newSpeed, newAngle);
		gameController.createProjectile(newProjectile);
	}
	
	public void setState(boolean newAlive){
		alive = newAlive;
	}

	
	public void update(int delta){
		
		//System.out.println(RightShoulder.bodyA);
		
		float x = Core.getBody().getPosition().x;
		float y = Core.getBody().getPosition().y;
		
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
		
		if(Core.getBody().getLinearVelocity().y<-15){
			
			
			//Core.getBody().setFixedRotation(false);
			//Core.getBody().applyTorque(50);
			//alive=false;
			
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_R)){
			 gameController.clearWorld();
		 }
		
		 if(alive){
			 
			 key_delay+=delta;
			 if(key_delay<0)
				 key_delay=0;
			 
			 if(gameMouse.getLeftMouseDown() && key_delay>100){
				 gameController.createSkeleton(new Skeleton(gameWorld,gameController,gameMouse.getWorldX(),gameMouse.getWorldY(),0.5f,0.5f,0.5f));
				 key_delay=0;
			 }
			 
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
			
			 if(Keyboard.isKeyDown(Keyboard.KEY_E)){
					System.out.println(RightShoulder);
					 RightShoulder.getJoint().enableMotor(false);
					 //gameWorld.destroyJoint(RightShoulder);
						
						
					
				 }
			 
			 if(Keyboard.isKeyDown(Keyboard.KEY_G)){
					Core.getBody().applyAngularImpulse(1);
						
						
					
				 }
				 if(Keyboard.isKeyDown(Keyboard.KEY_F)){
					 Core.getBody().applyAngularImpulse(-1f);
							
							
						
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
				 if(Core.getBody().getLinearVelocity().y<=0.1f && Core.getBody().getLinearVelocity().y>=-0.1f)
					applyImpulse(0,6000);
			 }
			 
			 
			 if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && bullet_time>=bullet_time_delay){
				bullet_time=0;
				if(direction){
					createProjectile(300,20,2,0.5f);
					createProjectile(300,0f,2,0);
					createProjectile(300,-20f,2,-0.5f);
					Core.getBody().applyForceToCenter(new Vec2(-200,0));
				}
				else{
					createProjectile(-300,20,-2,0.5f);
					createProjectile(-300,0f,-2,0);
					createProjectile(-300,-20f,-2,-0.5f);
					Core.getBody().applyForceToCenter(new Vec2(200,0));
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
						splatterVelocity.x = Core.getBody().getLinearVelocity().x + newX;
						splatterVelocity.y = Core.getBody().getLinearVelocity().x + newY;
						newBox.getBody().setLinearVelocity(splatterVelocity);
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
						newBox.getBody().setLinearVelocity(splatterVelocity);
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
						newBox.getBody().setLinearVelocity(splatterVelocity);
						gameController.createBox(newBox);
					}
					
					hasDied=true;
				}
		 }
		 
	}

}
