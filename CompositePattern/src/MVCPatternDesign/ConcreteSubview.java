package MVCPatternDesign;

public class ConcreteSubview extends View {

	View[] v; //Composite
	
	//obs
	int state;
	public void update()
	{
		//strategy
	}
	
	//all obs
	public void addSubView()
	{
		
	}
	public void removeSubView()
	{
		
	}
	public View getSubView()
	{
		View subview =new View();
		return subview;
	}
}
