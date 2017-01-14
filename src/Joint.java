import org.jbox2d.dynamics.joints.RevoluteJoint;

public class Joint {
	
	RevoluteJoint joint;
	
	public Joint(){}
	
	public RevoluteJoint getJoint(){
		return joint;
	}
	
	public void setJoint(RevoluteJoint newJoint){
		joint = newJoint;
	}

}
