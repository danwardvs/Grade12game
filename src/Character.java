import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.lwjgl.input.Keyboard;
import org.jbox2d.common.Vec2;
import java.util.Random;


public class Character extends Skeleton {
	
	boolean hasDied = false;
	
	int id;
	
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
	
	Random rn = new Random();

	public Character(World newWorld,WorldController newWorldController, int newId, float newX, float newY,float newR,float newG,float newB){
		
		super(newWorld,newWorldController,newX, newY,newR,newG,newB);
		id = newId;
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
		
		int x=0;
		
		if(id==0)
			x=-250;
		if(id==1)
			x=250;
		
		
		if(ult_timer==255)
			drawRect(0,x,250,220,50,new Colour((float)255-ult_timer,(float)ult_timer,0f,true));
		else
			drawRect(0,x,250,200,40,new Colour((float)255-ult_timer,(float)ult_timer,0f,true));

		
		if(jump_timer==255)
			drawRect(0,x,180,220,50,new Colour((float)255-jump_timer,(float)jump_timer,0f,true));
		else
			drawRect(0,x,180,200,40,new Colour((float)255-jump_timer,(float)jump_timer,0f,true));


		
	}
	
	public void delete(){
	 	removeJoints();
		removeBodys();
	}
	
	public void update(int delta){
		if(delta<0)
			delta=0;
		
		ult_frame+=delta;
	

		if(ult_frame>50){
			ult_timer++;
			ult_frame=0;
		}
		
		if(ult_timer>255)
			ult_timer=255;
		
		
		jump_frame+=delta;
		

		if(jump_frame>25){
			jump_timer++;
			jump_frame=0;
		}
		
		if(jump_timer>255)
			jump_timer=255;
	
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			 gameController.clearWorld();
		 }
		
		 if(alive){

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
				 	//RightKnee.getJoint().enableLimit(false);

				 	
				 	RightAnkle.getJoint().setMotorSpeed(-100);
				 	RightAnkle.getJoint().enableMotor(true);
				 	//RightAnkle.getJoint().enableLimit(false);

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
				 	//LeftKnee.getJoint().enableLimit(false);

				 	
				 	LeftAnkle.getJoint().setMotorSpeed(100);
				 	LeftAnkle.getJoint().enableMotor(true);
				 	//LeftAnkle.getJoint().enableLimit(false);

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
			 
			 
			 if(getY()<-9)
				 alive=false;
			 
			 if (Keyboard.isKeyDown(Keyboard.KEY_8)){
				 
				 
				 gameWorld.setGravity(new Vec2(gameWorld.getGravity().x,gameWorld.getGravity().y-0.5f));

			 }
			 
			 if (Keyboard.isKeyDown(Keyboard.KEY_9)){
				 gameWorld.setGravity(new Vec2(gameWorld.getGravity().x,gameWorld.getGravity().y+0.5f));

			 }
			
			 if (Keyboard.isKeyDown(control_jump) && jump_timer==255){
					applyImpulse(0,5000);
					jump_timer=0;
			 }
			 
			 
		 }
		 if(alive==false && hasDied==false){
			 
			 removeJoints();
			 	
			for(int i = 0; i<50; i++){
					
				Vec2 splatterVelocity = new Vec2((rn.nextFloat()*100)-50,(rn.nextFloat()*100)-50);
				Box newBox = new Box(gameWorld,BodyType.DYNAMIC,false,getX(),getY(),rn.nextFloat()/3,rn.nextFloat()/3,1f,1,0,0,1);
				newBox.getBody().setLinearVelocity(splatterVelocity);
				gameController.createBox(newBox);
					
				hasDied=true;
			}
		}
	}	
}