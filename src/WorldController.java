import java.util.List;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;


public class WorldController {
	

	int level = 1;
	    
	List<Box> gameBoxes = new ArrayList<Box>();
	List<Skeleton> gameSkeletons = new ArrayList<Skeleton>();
	Character[] gameCharacters =  new Character[4];

	static World gameWorld;
	Level gameLevel;

    // Setup world
    //float timeStep = 1.0f/60.0f;
    int velocityIterations = 6;
    int positionIterations = 2;
    
    public WorldController(World newWorld){
    	gameWorld = newWorld;
  
    }
    
    public void setGravity(float newX, float newY){
    	gameWorld.setGravity(new Vec2(newX,newY));
    }
	
    public void start() {
    	
        gameLevel  = new Level(this,gameWorld);
	    gameLevel.load_level("gamedata/Level_"+level+".xml");
	    gameCharacters[0].setControls(Keyboard.KEY_A,Keyboard.KEY_D , Keyboard.KEY_E, Keyboard.KEY_Q,Keyboard.KEY_S,Keyboard.KEY_W);
	    gameCharacters[1].setControls(Keyboard.KEY_J,Keyboard.KEY_L , Keyboard.KEY_O, Keyboard.KEY_U,Keyboard.KEY_K,Keyboard.KEY_I);


    }
    
    public void loadLevel(int newLevel){
    	level = newLevel;
    	clearWorld();
    }
    
	public void update(int delta){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_1)){
			loadLevel(1);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_2)){
			loadLevel(2);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_3)){
			loadLevel(3);
		}
		
		
		for(int i = 0; i<4; i++){
        	if(gameCharacters[i]!=null)
        		gameCharacters[i].update(delta);
        };
		
		
	}
	
			 
	public void draw(){
		
		// Clear the screen and depth buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  
         
        GL11.glClearColor(1, 1, 1, 1);
        // set the color of the quad (R,G,B,A)
       
             
        for(Box box: gameBoxes){
        	box.draw();
	    } 
        
        for(Skeleton skeleton: gameSkeletons){
        	skeleton.draw();
        }
        
        for(int i = 0; i<4; i++){
        	if(gameCharacters[i]!=null)
        		gameCharacters[i].draw();
        }

	}
	 public void createBox(Box newBox){
			
		gameBoxes.add(newBox);
	
	} 
	 
	 public void createSkeleton(Skeleton newSkeleton){
			
		gameSkeletons.add(newSkeleton);
	} 

	
	public void createCharacter(Character newCharacter, int newIndex){

		gameCharacters[newIndex] = newCharacter;		
	} 
	
	public void clearWorld(){
	
		
		for(Box box: gameBoxes){
			box.deleteBody();
		}
		  
		gameBoxes.clear();
		
		for(int i = 0; i<4; i++){
        	if(gameCharacters[i]!=null)
        		gameCharacters[i].delete();
        };
		
		  
		start();
		  
		
	}
}
