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
	    cursor = loadTexture("Cursor.png");
	    
	}
	
	
	Texture loadTexture(String newPath){
		Texture newTexture = null;
		try{
			
			newTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(newPath),GL11.GL_NEAREST);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return newTexture;
	}
	
	public void setVisibility(boolean newVisibility){
		is_visible = newVisibility;
	}
	
	public void update(){
		double mouse_delta_x = Mouse.getDX();
		double mouse_delta_y = Mouse.getDY();
		
		left_mouse_down = Mouse.isButtonDown(0);
		right_mouse_down = Mouse.isButtonDown(1);

		
		mouse_x += mouse_delta_x*sensitivity;
		mouse_y += -mouse_delta_y*sensitivity;
		
		if(has_bounds){
			if(mouse_x<0)
				mouse_x=0;
			if(mouse_x>SCREEN_W-1)
				mouse_x=SCREEN_W-1;
			
			if(mouse_y>SCREEN_H-1)
				mouse_y=SCREEN_H-1;
			if(mouse_y<0)
				mouse_y=0;
		}
		
		

		
	}
	public void draw(){
		
	
		if(is_visible){
			cursor.bind();
				
			GL11.glBegin(GL11.GL_QUADS);
			   	GL11.glTexCoord2f(0,0);
			   	GL11.glVertex2f(mouse_x,mouse_y);
				GL11.glTexCoord2f(1,0);
				GL11.glVertex2f(mouse_x+cursor.getTextureWidth(),mouse_y);
				GL11.glTexCoord2f(1,1);
				GL11.glVertex2f(mouse_x+cursor.getTextureWidth(),mouse_y+cursor.getTextureHeight());
				GL11.glTexCoord2f(0,1);
				GL11.glVertex2f(mouse_x,mouse_y+cursor.getTextureHeight());	
			GL11.glEnd();
		}
	}
	
	public int getX(){
		return mouse_x;
	}
	
	public int getY(){
		return mouse_y;
	}
	
	public boolean getLeftMouseDown(){
		return left_mouse_down;
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
	
	public void setCursor(String newCursorPath){
		
		cursor = loadTexture(newCursorPath);
	}
	
}
