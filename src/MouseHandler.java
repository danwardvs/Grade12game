import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class MouseHandler {
	private static final int SCREEN_W=320;
	private static final int SCREEN_H=240;
	private int mouse_x=180;
	private int mouse_y=120;
	private boolean is_visible=true;
	private boolean left_mouse_down;
	private boolean right_mouse_down;
	private float sensitivity = 0.5f;
	private boolean has_bounds=true;
	private Texture cursor;
	
	public MouseHandler(){
		
		try{
			Mouse.create();
		} catch (LWJGLException e) {
	        e.printStackTrace();
	        System.exit(0);
	    }  
		
	    Mouse.setGrabbed(true);
	
	    
	}
	
	
	
	public void setVisibility(boolean newVisibility){
		is_visible = newVisibility;
	}
	public int getWorldX(){
		return (Mouse.getX()/20)-20;
	}
	
	public int getWorldY(){
		return (Mouse.getY()/20)-15;
	}
	public int getScreenX(){
		return Mouse.getX()-400;
	}
	public int getScreenY(){
		return Mouse.getY()-300;
	}
	
	
	
	
	public void draw(){
		
	
		if(is_visible){
			drawRect(0f,getScreenX(),getScreenY(),10f,10f,1,0,0,1);
		}
	}
	

	public void drawRect(float angle,float x, float y, float width, float height, float newR,float newG, float newB, float newA){
		
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
					
			GL11.glColor4f(0,0,0,0);
			GL11.glVertex2f(x-(width/2),y-(height/2));
			GL11.glVertex2f(x-(width/2)+width,y-(height/2));
			GL11.glVertex2f(x-(width/2)+width,y+height-(height/2));
			GL11.glVertex2f(x-(width/2),y+height-(height/2));
				
			GL11.glColor4f(newR,newG,newB,newA);
			GL11.glVertex2f(x-(width/2)+1,y-(height/2)+1);
			GL11.glVertex2f(x-(width/2)+width-1,y-(height/2)+1);
			GL11.glVertex2f(x-(width/2)+width-1,y+height-(height/2)-1);
			GL11.glVertex2f(x-(width/2)+1,y+height-(height/2)-1);
	            	
		GL11.glEnd();
	            
	            
	    GL11.glPopMatrix();
		}
	

	//public int get
	
	public boolean getLeftMouseDown(){
		return Mouse.isButtonDown(0);
	}
	
	public void toggleBounds(){
		
		if(has_bounds)
			has_bounds=false;
		else
			has_bounds=true;
		
	}
	
	public void setBounds(boolean newBounds){
		
		has_bounds=newBounds;
		
	}

	public boolean getRightMouseDown(){
		return right_mouse_down;
	}
	

}
