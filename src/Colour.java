
public class Colour {
	
	public float r;
	public float b;
	public float g;
	
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
