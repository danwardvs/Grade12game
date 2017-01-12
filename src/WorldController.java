import java.util.List;
import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.Sys;


public class WorldController {
	
		
	
	MouseHandler gameMouse = new MouseHandler();
	KeyboardHandler gameKeyboard = new KeyboardHandler();
	
	int level = 1;
	boolean buttonPressed;
	    
	int score = 10;
	int BOX_AMOUNT = 25;
	List<Box> gameBoxes = new ArrayList<Box>();
	List<Projectile> gameProjectiles = new ArrayList<Projectile>();
	List<Item> gameItems = new ArrayList<Item>();
	Character gameCharacter;
	static World gameWorld;
	Level gameLevel;
	
	  /** time at last frame */

    // Setup world
    //float timeStep = 1.0f/60.0f;
    int velocityIterations = 6;
    int positionIterations = 2;
    
    public WorldController(World newWorld){
    	gameWorld = newWorld;
    }
	
    public void start() {
    	
        gameLevel  = new Level(this,gameWorld,gameCharacter);
	    gameLevel.load_level("gamedata/Level_"+level+".xml");
		

   }
    
	public void update(int delta){
		

    	//System.out.print(delta/1000);
    	//System.out.println(" " + timeStep);
    	//update((int)delta);
    	
    	
    	
    	
      
		

        Display.setTitle("Cubes Remaining: " + score);

	    if(gameMouse.getLeftMouseDown()){
	    	Display.setTitle("X: " + (gameMouse.getWorldX())+ "Y: " + (gameMouse.getWorldY()));
	    }
		 
		gameCharacter.update(delta);
		
		for(int j = 0; j < gameProjectiles.size(); j++)
		{
		    Projectile newProjectile = gameProjectiles.get(j);
		    
		    int result = newProjectile.update(delta);
		    
		    if(result==0){			    	
		    	
		        gameProjectiles.remove(j);
		        
		        break;
		    }
		    if(result==2){
		    	gameCharacter.setState(false);
		    }

		}
		
		for(int j = 0; j < gameItems.size(); j++)
		{
		    Item newItem = gameItems.get(j);
		    
		    int result = newItem.update();
		    if(buttonPressed){
		    	if(newItem.getType().equals("ButtonTrigger")){
		    		newItem.deleteBody();
		    		gameItems.remove(j);
		    	}
		    }
		    
		    if(result==1){			    	
		    	score--;
		        gameItems.remove(j);
		        
		        break;
		        
		    }else if(result==2){
		    	gameItems.remove(j);
		    	if(level!=3){
		    		level++;
			    	clearWorld();
		    	}
		    	else
		    		Sys.alert("Yippee!", "You win! Thanks for playing.");
		    	

		        break;

		    	
		    }else if(result==3){
		    	buttonPressed=true;
		    	
		    }
		    

		}
		//System.out.println(gameProjectiles.size());
		try{
		}catch(Exception e){
			System.out.println(e);
		}

	 }
			 
	public void draw(){
		  // Clear the screen and depth buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  
         
        GL11.glClearColor(1, 1, 1, 1);
        // set the color of the quad (R,G,B,A)
       
             
        for(Box box: gameBoxes){
        	box.draw();

	    } 
        
        for(Box projectile: gameProjectiles){
        	projectile.draw();
        }
        
        for(Box item: gameItems){
        	item.draw();
        }
        
        gameCharacter.draw();
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		
	}
	 public void createBox(Box newBox){
			
		gameBoxes.add(newBox);
	
	} 
	public int getRemainingBoxes(){
		return score;
	}
	 
	public void createProjectile(Projectile newProjectile){
		
		gameProjectiles.add(newProjectile);
		
	}
	
	public void createItem(Item newItem){
		
		gameItems.add(newItem);
		
	}
	
	
	public void createCharacter(Character newCharacter){

		gameCharacter = newCharacter;
			
	} 
	public void clearWorld(){
			buttonPressed=false;
		
		  for(Box box: gameBoxes){
	          box.deleteBody();

		  }
		  gameBoxes.clear();
		  
		  for(Projectile projectile: gameProjectiles){
	          projectile.deleteBody();

		  }
		  gameProjectiles.clear();
		  
		  for(Item item: gameItems){
	          item.deleteBody();

		  }
		  
		  gameItems.clear();
		  gameCharacter.deleteBody();
		  
		  gameLevel.load_level("gamedata/Level_"+level+".xml");
		  score=10;
		  gameCharacter.body.setLinearVelocity(new Vec2(0,0));
		  
		
	}
	

    /** 
     * Calculate how many milliseconds have passed 
     * since last frame.
     * 
     * @return milliseconds passed since last frame 
     */
	

		

	   
	  

}
