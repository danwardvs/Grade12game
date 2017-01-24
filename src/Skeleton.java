import java.util.List;
import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Skeleton {
	
	World gameWorld;
	WorldController gameController;
	
	float r;
	float g;
	float b;
	
	Colour Black = new Colour(0.2f,0.2f,0.2f,false);
	Colour Skin = new Colour(255,201,180,true);
	Colour Blue = new Colour(50,50,255,true);
	Colour Red = new Colour(255,50,50,true);
	

	List<Box> boneList = new ArrayList<Box>();
	List<Joint> jointList = new ArrayList<Joint>();


	
	Box Core;
	
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
	
	
	
	
	public Skeleton(World newWorld,WorldController newWorldController, float newX, float newY,float newR,float newG,float newB){
		
		gameWorld = newWorld;
		gameController = newWorldController;
		
		r = newR;
		g = newG;
		b = newB;
		
		Core = new Box(newWorld,BodyType.DYNAMIC,false,newX,newY,0.5f,0.7f,0,Red.r,Red.g,Red.b,1);
		
			
		LeftThigh = createBodyPart(LeftHip,Core.getBody(),newX-0.25f,newY-1f,0.2f,0.5f,newX-0.25f,newY-0.5f,true,-0.7f,-0.3f,-0.5f,Blue);
		LeftShin = createBodyPart(LeftKnee,LeftThigh.getBody(),newX-0.25f,newY-2f,0.2f,0.5f,newX-0.25f,newY-1.5f,true,0.4f,0.6f,0.5f,Skin);
		LeftFoot = createBodyPart(LeftAnkle,LeftShin.getBody(),newX-0.5f,newY-2.5f,0.5f,0.2f,newX-0.25f,newY-2.5f,true,-0.2f,0,0,Black);

		RightThigh = createBodyPart(RightHip,Core.getBody(),newX+0.25f,newY-1f,0.2f,0.5f,newX+0.25f,newY-0.5f,true,0.3f,0.7f,0.5f,Blue);
		RightShin = createBodyPart(RightKnee,RightThigh.getBody(),newX+0.25f,newY-2f,0.2f,0.5f,newX+0.25f,newY-1.5f,true,-0.6f,-0.4f,-0.5f,Skin);
		RightFoot = createBodyPart(RightAnkle,RightShin.getBody(),newX+0.5f,newY-2.5f,0.5f,0.2f,newX+0.25f,newY-2.5f,true,-0.2f,0.2f,0,Black);
			
		RightUpperArm = createBodyPart(RightShoulder,Core.getBody(),newX-0.75f,newY,0.2f,0.5f,newX-0.75f,newY+0.25f,false,-3.0f,2.0f,0,Red);
		RightForearm = createBodyPart(RightElbow,RightUpperArm.getBody(),newX-0.75f,newY-0.5f,0.2f,0.5f,newX-0.75f,newY-0.25f,false,0,0,0,Skin);
		RightHand = createBodyPart(RightWrist,RightForearm.getBody(),newX-0.75f,newY-1f,0.3f,0.3f,newX-0.75f,newY-0.75f,false,0,0,0,Skin);
			
		LeftUpperArm = createBodyPart(LeftShoulder,Core.getBody(),newX+0.75f,newY,0.2f,0.5f,newX+0.75f,newY+0.25f,false,0,0,0,Red);
		LeftForearm = createBodyPart(LeftElbow,LeftUpperArm.getBody(),newX+0.75f,newY-0.5f,0.2f,0.5f,newX+0.75f,newY-0.25f,false,0,0,0,Skin);
		LeftHand = createBodyPart(LeftWrist,LeftForearm.getBody(),newX+0.75f,newY-1f,0.3f,0.3f,newX+0.75f,newY-0.75f,false,0,0,0,Skin);
			
		Neck = createBodyPart(LowerNeck,Core.getBody(),newX,newY+0.75f,0.3f,0.2f,newX,newY+0.75f,true,0,0,0,Skin);
			
		Head = createBodyPart(UpperNeck,Neck.body,newX,newY+1.25f,0.4f,0.4f,newX,newY+1.25f,true,0,0,0,Skin);
		
		boneList.add(LeftThigh);
		boneList.add(LeftShin);
		boneList.add(LeftFoot);
		boneList.add(LeftUpperArm);
		boneList.add(LeftForearm);
		boneList.add(LeftHand);
		
		boneList.add(RightThigh);
		boneList.add(RightShin);
		boneList.add(RightFoot);
		boneList.add(RightUpperArm);
		boneList.add(RightForearm);
		boneList.add(RightHand);
		
		boneList.add(Neck);
		boneList.add(Head);
		boneList.add(Core);
		
		jointList.add(LeftHip);
		jointList.add(LeftKnee);
		jointList.add(LeftAnkle);
		
		jointList.add(LeftShoulder);
		jointList.add(LeftElbow);
		jointList.add(LeftWrist);
		
		jointList.add(RightHip);
		jointList.add(RightKnee);
		jointList.add(RightAnkle);
		
		jointList.add(RightShoulder);
		jointList.add(RightElbow);
		jointList.add(RightWrist);
		
		jointList.add(UpperNeck);
		jointList.add(LowerNeck);
		
	
		
		
		
	}
	
	//This function creates a body part based on a revolutejoint that is passed
	public Box createBodyPart(Joint newJoint, Body newBody,float newX, float newY,float newWidth, float newHeight, float newAnchorX, float newAnchorY,boolean newLimits, float newLowerLimit, float newUpperLimit,
			
			float newReferenceAngle, Colour newColour){
		
		Box newBox = new Box(gameWorld,BodyType.DYNAMIC,false,newX,newY,newWidth,newHeight,0,newColour.r,newColour.g,newColour.b,1);
		//newBox.body.setAngularDamping(1000000000);
		//newBox.body.setLinearDamping(10);
		gameController.createBox(newBox);
		
		Vec2 Anchor = new Vec2(newAnchorX,newAnchorY);
		
		RevoluteJointDef jd = new RevoluteJointDef();
		
		jd.initialize(newBody, newBox.body, Anchor);
		jd.enableMotor=true;
		jd.maxMotorTorque=100;
		jd.motorSpeed=0;
        jd.collideConnected = false;
        jd.enableLimit=newLimits;
        jd.upperAngle=newUpperLimit;
        jd.lowerAngle=newLowerLimit;
        jd.referenceAngle=newReferenceAngle;
        jd.enableMotor=true;
        
   
        newJoint.setJoint((RevoluteJoint) gameWorld.createJoint(jd));
        
    
		return newBox;	
		
	}
	public void removeJoints(){
		 for(Joint newJoint: jointList){
	        	gameWorld.destroyJoint(newJoint.getJoint());
	        }
	}
	
	public void removeBodys(){
		 for(Box newBody: boneList){
	        	gameWorld.destroyBody(newBody.getBody());
	        }
	}
	
	
	
	public void applyTorque(float newTorque){
		 Core.getBody().applyTorque(newTorque);
	}
	public void applyLinearImpulse(float newX,float newY){
		//body.ApplyLinearImpulse( new Vec2(0,50), body.getWorldCenter());
		Core.getBody().applyLinearImpulse(new Vec2(newX,newY), Core.getBody().getPosition(), true);
	}
	public void applyImpulse(float newX, float newY){
		Core.getBody().applyForceToCenter(new Vec2(newX,newY));
	}
	public float getX(){
		return Core.getBody().getPosition().x;
	}
	public float getY(){
		return Core.getBody().getPosition().y;
	}
	
	public void update(){
		
	
	}
	public void drawRect(float angle,float x, float y, float width, float height, Colour newColour){
		
		// draw quad
		GL11.glLoadIdentity();
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glTranslatef(400, 300, 0);
		GL11.glRotatef((float)Math.toDegrees(angle), 0, 0, 1);
		GL11.glTranslatef(-x, -y, 0);
		GL11.glTranslatef(-400, -300, 0);
		GL11.glTranslatef(400, 300, 0);

		GL11.glBegin(GL11.GL_QUADS);
					
			GL11.glColor4f(0,0,0,1);
			GL11.glVertex2f(x-(width/2),y-(height/2));
			GL11.glVertex2f(x-(width/2)+width,y-(height/2));
			GL11.glVertex2f(x-(width/2)+width,y+height-(height/2));
			GL11.glVertex2f(x-(width/2),y+height-(height/2));
				
			GL11.glColor4f(newColour.r,newColour.g,newColour.b,1);
			GL11.glVertex2f(x-(width/2)+1,y-(height/2)+1);
			GL11.glVertex2f(x-(width/2)+width-1,y-(height/2)+1);
			GL11.glVertex2f(x-(width/2)+width-1,y+height-(height/2)-1);
			GL11.glVertex2f(x-(width/2)+1,y+height-(height/2)-1);
	            	
		GL11.glEnd();
	            
	            
	    GL11.glPopMatrix();
		}
	
	public void draw(){
		 
		
		for(Box newBones: boneList){
	       	newBones.draw();
	    }
	


		drawRect(Head.getBody().getAngle(),(Head.getX() - 0.2f)*20,(Head.getY() + 0.1f)*20,4f,4f,Blue);
		drawRect(Head.getBody().getAngle(),(Head.getX() + 0.2f)*20,(Head.getY() + 0.1f)*20,4f,4f,Blue);
		drawRect(Head.getBody().getAngle(),(Head.getX())*20,(Head.getY() - 0.2f)*20,8f,4f,Red);


		 
		
	}
	

}
