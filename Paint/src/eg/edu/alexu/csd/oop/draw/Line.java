package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Line extends Shapes {

	double x, y;

	@Override
	public void setProperties(java.util.Map<String, Double> properties) {
		x = properties.get("x");
		y = properties.get("y");
	}

	@Override
	public Map<String, Double> getProperties() {
		// TODO Auto-generated method stub
		Map<String, Double> p = new HashMap<String, Double>();
		p.put("x", x);
		p.put("y", y);
		return p;
	}

	@Override
	public void draw(Object canvas) {
		// TODO Auto-generated method stub
		Graphics g = (Graphics) canvas;
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g.setColor(getColor());
		g.drawLine(this.getPosition().x, this.getPosition().y, (int) x, (int) y);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Color c = this.getColor();
		Point p = this.getPosition();
		Map<String, Double> properties = this.getProperties();
		Shape copy = new Line();
		copy.setPosition(p);
		copy.setColor(c);
		copy.setProperties(properties);
		return copy;
		
	}
}
