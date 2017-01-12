
import org.lwjgl.input.Keyboard;

public class KeyboardHandler {
	
	
	public boolean isKeyPressed(){
		
		boolean is_pressed=false;
		
		for(int i=0; i<122;i++){
			if(Keyboard.isKeyDown(i)){
				is_pressed=true;
			}
		}
		return is_pressed;
	}
	public String lastKeyPressed(){
		for(int i=0; i<122;i++){
			if(Keyboard.isKeyDown(i)){

				return Keyboard.getKeyName(i);
			}
		}
		return "";
	}

}
