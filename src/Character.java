
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
	
	int control_right_leg;
	int control_left_leg;
	int control_right_arm;
	int control_left_arm;
	int control_ult;
	int control_jump;
	
	int ult_timer;
	int ult_frame;
	
	int jump_timer;
	int jump_frame;
	
	boolean alive=true;
	int deathDirection;
	
	
	
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
	
	public void setControls(int newLeftLeg, int newRightLeg, int newLeftArm, int newRightArm, int newUlt, int newJump){
		control_left_leg = newLeftLeg;
		control_right_leg = newRightLeg;
		control_left_arm = newLeftArm;
		control_right_arm = newRightArm;
		control_ult = newUlt;
		control_jump = newJump;
	}
	
	public void draw(){
		super.draw();
		if(gameController.gameCharacter1==this){
			drawRect(0,-250,250,200,40,new Colour((float)255-ult_timer,(float)ult_timer,0f,true));
			
			drawRect(0,-250,200,200,40,new Colour((float)255-jump_timer,(float)jump_timer,0f,true));


		}
		if(gameController.gameCharacter2==this){
			drawRect(0,250,250,200,40,new Colour((float)255-ult_timer,(float)ult_timer,0f,true));
			
			drawRect(0,250,200,200,40,new Colour((float)255-jump_timer,(float)jump_timer,0f,true));

		}
	}
	public void delete(){
	 	removeJoints();
		removeBodys();

	}
	
	
	public void update(int delta){
		if(delta<0)
			delta=0;
		
		ult_frame+=delta;
	

		if(ult_frame>100){
			ult_timer++;
			ult_frame=0;
		}
		
		if(ult_timer>255)
			ult_timer=255;
		
		
		jump_frame+=delta;
		

		if(jump_frame>50){
			jump_timer++;
			jump_frame=0;
		}
		
		if(jump_timer>255)
			jump_timer=255;

			
		
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
			 if(Keyboard.isKeyDown(Keyboard.KEY_P)){
				alive=false;
			 }
			
	

			 
			 
			 
			 if(Keyboard.isKeyDown(control_right_leg)){
				 
				 if(Keyboard.isKeyDown(control_ult) && ult_timer==255){
					 	applyImpulse(5000,0);
					 	ult_timer=0;

				 	}
				 	
				 	RightHip.getJoint().setMotorSpeed(100);
				 	RightHip.getJoint().enableMotor(true);
				 	RightHip.getJoint().enableLimit(false);
				 	
				 	
				 	RightKnee.getJoint().setMotorSpeed(-100);
				 	RightKnee.getJoint().enableMotor(true);
				 	RightHip.getJoint().enableLimit(false);

				 	
				 	RightAnkle.getJoint().setMotorSpeed(-100);
				 	RightAnkle.getJoint().enableMotor(true);
				 	RightHip.getJoint().enableLimit(false);

				 	Core.getBody().applyAngularImpulse(0.1f);

					 Core.getBody().applyForceToCenter(new Vec2(10f,0));
				 	

	
					
			}
			 if(!Keyboard.isKeyDown(control_right_leg)){
				 RightKnee.getJoint().enableMotor(false);
				 RightHip.getJoint().enableMotor(false);
				 RightAnkle.getJoint().enableMotor(false);
				 RightHip.getJoint().enableLimit(true);
				 RightKnee.getJoint().enableLimit(true);
				 RightAnkle.getJoint().enableLimit(true);





			 }
			 
			 if(Keyboard.isKeyDown(control_left_leg)){
				 
				 	if(Keyboard.isKeyDown(control_ult) && ult_timer==255){
					 	applyImpulse(-5000,0);
					 	ult_timer=0;

				 	}
				 		
				 	
				 	LeftHip.getJoint().setMotorSpeed(-100);
				 	LeftHip.getJoint().enableMotor(true);
				 	LeftHip.getJoint().enableLimit(false);

				 	
				 	LeftKnee.getJoint().setMotorSpeed(100);
				 	LeftKnee.getJoint().enableMotor(true);
				 	LeftHip.getJoint().enableLimit(false);

				 	
				 	LeftAnkle.getJoint().setMotorSpeed(100);
				 	LeftAnkle.getJoint().enableMotor(true);
				 	LeftHip.getJoint().enableLimit(false);

				 	Core.getBody().applyAngularImpulse(-0.1f);
				 	Core.getBody().applyForceToCenter(new Vec2(-10f,0));

	
					
			}
			 if(!Keyboard.isKeyDown(control_left_leg)){
				 LeftKnee.getJoint().enableMotor(false);
				 LeftHip.getJoint().enableMotor(false);
				 LeftAnkle.getJoint().enableMotor(false);
				 LeftHip.getJoint().enableLimit(true);
				 LeftKnee.getJoint().enableLimit(true);
				 LeftAnkle.getJoint().enableLimit(true);
				 
				




			 }
			 
			 
			 if(Keyboard.isKeyDown(control_right_arm)){
				 	
				 	RightShoulder.getJoint().setMotorSpeed(-100);
				 	RightShoulder.getJoint().enableMotor(true);
	
					
			}
			 if(!Keyboard.isKeyDown(control_right_arm)){
				 RightShoulder.getJoint().setMotorSpeed(10);
				 

			 }
			 if(Keyboard.isKeyDown(control_left_arm)){
				 	
				 	LeftShoulder.getJoint().setMotorSpeed(100);
				 	LeftShoulder.getJoint().enableMotor(true);
	
					
			}
			 if(!Keyboard.isKeyDown(control_left_arm)){
				 LeftShoulder.getJoint().setMotorSpeed(-10);

			 }
			 
			 
			/* if(Keyboard.isKeyDown(Keyboard.KEY_A)){
				 	
				 	RightHip.getJoint().setMotorSpeed(-100);
				 	RightHip.getJoint().enableMotor(true);
	
					
			}
			 if(!Keyboard.isKeyDown(Keyboard.KEY_A)){
				 	RightHip.getJoint().enableMotor(false);

			 }
			 if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				 	
				 	LeftHip.getJoint().setMotorSpeed(100);
				 	LeftHip.getJoint().enableMotor(true);
	
					
			}
			 if(!Keyboard.isKeyDown(Keyboard.KEY_D)){
				 	LeftHip.getJoint().enableMotor(false);

			 }*/
			 
			 
			 
			 key_delay+=delta;
			 if(key_delay<0)
				 key_delay=0;
			 
			 if(getY()<-6)
				 alive=false;
			 
			 if(gameMouse.getLeftMouseDown() && key_delay>100){
				 gameController.createSkeleton(new Skeleton(gameWorld,gameController,gameMouse.getWorldX(),gameMouse.getWorldY(),0.5f,0.5f,0.5f));
				 key_delay=0;
			 }
			 
			 if (Keyboard.isKeyDown(Keyboard.KEY_8)){
				 
				 
				 gameWorld.setGravity(new Vec2(gameWorld.getGravity().x,gameWorld.getGravity().y-0.5f));

			 }
			 
			 if (Keyboard.isKeyDown(Keyboard.KEY_9)){
				 gameWorld.setGravity(new Vec2(gameWorld.getGravity().x,gameWorld.getGravity().y+0.5f));

			 }
			
			

			 
	
			 
		
			 //System.out.println(Feet);
			 if (Keyboard.isKeyDown(control_jump) && jump_timer==255){
					applyImpulse(0,5000);
					jump_timer=0;
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
			 	removeJoints();
				for(int i = 0; i<50; i++){
					
					Vec2 splatterVelocity = new Vec2((rn.nextFloat()*100)-50,(rn.nextFloat()*100)-50);
					Box newBox = new Box(gameWorld,BodyType.DYNAMIC,false,x,y,rn.nextFloat()/3,rn.nextFloat()/3,1f,1,0,0,1);
					int newX;
					int newY;
					int n = -10 - 10 + 1;
					int randX = rn.nextInt() % n;
					int randY = rn.nextInt() % n;
					newX = -10 + randX;
					newY = -10 + randY;
					newBox.getBody().setLinearVelocity(splatterVelocity);
					gameController.createBox(newBox);
					
					
					hasDied=true;
				}
		 }
		 
	}
	
	

}
