import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

public class Skeleton {
	
	World gameWorld;
	WorldController gameController;
	
	float r;
	float g;
	float b;
	
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
		
		Core = new Box(newWorld,BodyType.DYNAMIC,false,newX,newY,0.5f,0.7f,0,newR,newG,newB,1);
	
			
		LeftThigh = createBodyPart(LeftHip,Core.getBody(),newX-0.25f,newY-1f,0.2f,0.5f,newX-0.25f,newY-0.5f,true,-0.2f,0);
		LeftShin = createBodyPart(LeftKnee,LeftThigh.getBody(),newX-0.25f,newY-2f,0.2f,0.5f,newX-0.25f,newY-1.5f,true,-0.2f,0);
		LeftFoot = createBodyPart(LeftAnkle,LeftShin.getBody(),newX-0.5f,newY-2.5f,0.75f,0.2f,newX-0.25f,newY-2.5f,true,-0.2f,0);

		RightThigh = createBodyPart(RightHip,Core.getBody(),newX+0.25f,newY-1f,0.2f,0.5f,newX+0.25f,newY-0.5f,true,0,0.2f);
		RightShin = createBodyPart(RightKnee,RightThigh.getBody(),newX+0.25f,newY-2f,0.2f,0.5f,newX+0.25f,newY-1.5f,true,0,0.2f);
		RightFoot = createBodyPart(RightAnkle,RightShin.getBody(),newX+0.5f,newY-2.5f,0.75f,0.2f,newX+0.25f,newY-2.5f,true,0,0.2f);
			
		RightUpperArm = createBodyPart(RightShoulder,Core.getBody(),newX-0.75f,newY,0.2f,0.5f,newX-0.75f,newY+0.25f,false,-3.0f,2.0f);
		RightForearm = createBodyPart(RightElbow,RightUpperArm.getBody(),newX-0.75f,newY-0.5f,0.2f,0.5f,newX-0.75f,newY-0.25f,false,0,0);
		RightHand = createBodyPart(RightWrist,RightForearm.getBody(),newX-0.75f,newY-1f,0.3f,0.3f,newX-0.75f,newY-0.75f,false,0,0);
			
		LeftUpperArm = createBodyPart(LeftShoulder,Core.getBody(),newX+0.75f,newY,0.2f,0.5f,newX+0.75f,newY+0.25f,false,0,0);
		LeftForearm = createBodyPart(LeftElbow,LeftUpperArm.getBody(),newX+0.75f,newY-1f,0.2f,0.5f,newX+0.75f,newY-0.25f,false,0,0);
		LeftHand = createBodyPart(LeftWrist,LeftForearm.getBody(),newX+0.75f,newY-1.5f,0.3f,0.3f,newX+0.75f,newY-1.25f,false,0,0);
			
		Neck = createBodyPart(LowerNeck,Core.getBody(),newX,newY+0.75f,0.3f,0.2f,newX,newY+0.75f,true,0,0);
			
		Head = createBodyPart(UpperNeck,Neck.body,newX,newY+1.25f,0.4f,0.4f,newX,newY+1.25f,true,0,0);
		
		
		
	}
	
	//This function creates a body part based on a revolutejoint that is passed
	public Box createBodyPart(Joint newJoint, Body newBody,float newX, float newY,float newWidth, float newHeight, float newAnchorX, float newAnchorY,boolean newLimits, float newLowerLimit, float newUpperLimit){
		
		Box newBox = new Box(gameWorld,BodyType.DYNAMIC,false,newX,newY,newWidth,newHeight,0,r,b,g,1);
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
	
	public void draw(){
		Core.draw();
		
		LeftThigh.draw();
		LeftShin.draw();
		LeftFoot.draw();
		
		RightThigh.draw();
		RightShin.draw();
		RightFoot.draw();
		
		RightUpperArm.draw();
		RightForearm.draw();
		RightHand.draw();
		
		LeftUpperArm.draw();
		LeftForearm.draw();
		LeftHand.draw();
		
		Neck.draw();
		Head.draw();
		
	}
	

}
