
public class Colour {
	
	public float r;
	public float b;
	public float g;
	
	public String name = "";
	
	static Colour Black = new Colour(0.2f,0.2f,0.2f,false,"Black");
	static Colour Skin = new Colour(255,201,180,true,"Skin");
	static Colour Blue = new Colour(50,50,255,true,"Blue");
	static Colour Red = new Colour(255,50,50,true,"Red");
	static Colour Green = new Colour(0,1,0,false,"Green");
	
	
	public static Colour ColourFromString(String newString){
		
		if(Black.name.equals(newString))
			return Black;
		if(Skin.name.equals(newString))
			return Skin;
		if(Blue.name.equals(newString))
			return Blue;
		if(Red.name.equals(newString))
			return Red;
		if(Green.name.equals(newString))
			return Green;
		
		return Black;
	}
	
	
	public Colour(float newR, float newG, float newB,boolean RGB,String newName){
		
		name = newName;
		
		if(!RGB){
			r = newR;
			b = newB;
			g = newG;
		}
		if(RGB){
			r = newR/255;
			b = newB/255;
			g = newG/255;
		}
	}
	
	public Colour(float newR, float newG, float newB,boolean RGB){
		
		
		
		if(!RGB){
			r = newR;
			b = newB;
			g = newG;
		}
		if(RGB){
			r = newR/255;
			b = newB/255;
			g = newG/255;
		}
	}

}
