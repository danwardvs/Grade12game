import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Main {
	
	World gameWorld;
	
    long lastFrame;
    int fps;
    long lastFPS;
    
	boolean exit=false;
	
	public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
      
        return delta;
    }
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
     
    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: 60");

            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }
	
	
	private void start(){
		
	    try {
	        Display.setDisplayMode(new DisplayMode(800,600));
	        Display.create();
	    } catch (LWJGLException e) {
	        e.printStackTrace();
	        System.exit(0);
	    }
	  
	    // init OpenGL
	        //GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);        
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);    
	        
	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    GL11.glOrtho(0,800, 0, 600, 1, -1);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	    
	    
	    Vec2  gravity = new Vec2(0,-10);
	    World gameWorld = new World(gravity);
	    BodyDef groundBodyDef = new BodyDef();
	    groundBodyDef.position.set(0, -15);
	    Body groundBody = gameWorld.createBody(groundBodyDef);
	    PolygonShape groundBox = new PolygonShape();
	    groundBox.setAsBox(800, 0);
	    groundBody.createFixture(groundBox, 0);
	    gameWorld.setSleepingAllowed(true);

	
		
		WorldController gameController = new WorldController(gameWorld);
	    gameController.start();
	       
	    boolean exit=false;
		    
		while (!Display.isCloseRequested() && !exit) {
		    
			float delta = getDelta();
			updateFPS();
			
		    if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		    	exit=true;
		    
		    gameController.update((int)delta);
		       
		    //Haxx for stepping world but idek
		    gameWorld.step(delta/1000, 2, 6);

		    gameController.draw();
		        
		   
		    Display.update();
		        
		    Display.sync(60);
		}
		  
		Display.destroy();
		    
	}
    
	public static void main(String[] args) {
		
		Main gameMain = new Main();
		gameMain.start();

	    
	}

}
