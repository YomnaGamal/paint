package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.util.Map;

public class Shapes implements Shape {
	
	Point p;
	Color c , fullColor;

	@Override
	public void setPosition(Object position) {
		// TODO Auto-generated method stub
		p = (Point) position;
	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return p;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Double> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColor(Object color) {
		// TODO Auto-generated method stub
		c = (Color) color;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return c;
	}

	@Override
	public void setFillColor(Object color) {
		// TODO Auto-generated method stub
		fullColor = (Color) color;
	}

	@Override
	public Color getFillColor() {
		// TODO Auto-generated method stub
		return fullColor;
	}

	@Override
	public void draw(Object canvas) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return null;
		
	}

}
