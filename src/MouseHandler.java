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
	boolean USING_CURSOR=false;
	
	public MouseHandler(){
		
		try{
			Mouse.create();
		} catch (LWJGLException e) {
	        e.printStackTrace();
	        System.exit(0);
	    }  
		
	    Mouse.setGrabbed(true);
	    if(USING_CURSOR)
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
	
	public int getWorldX(){
		return (Mouse.getX()/20)-20;
	}
	
	public int getWorldY(){
		return (Mouse.getY()/20)-15;
	}
	
	//public int get
	
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
