package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Rectangle extends Shapes {

	double length, width;

	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		length = properties.get("length");
		width = properties.get("width");
	}

	@Override
	public Map<String, Double> getProperties() {
		// TODO Auto-generated method stub
		Map<String, Double> properties = new HashMap<String, Double>();
		properties.put("length", length);
		properties.put("width", width);
		return properties;
	}

	@Override
	public void draw(Object canvas) {
		// TODO Auto-generated method stub
		Graphics g = (Graphics) canvas;
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		int x = (int) (this.getPosition().getX() - width / 2);
		int y = (int) (this.getPosition().getY() - length / 2);
		if (this.getFillColor() != null) {
			g.setColor(getFillColor());
			g.fillRect(x, y, (int) width, (int) length);
		}
		g.setColor(getColor());
		g.drawRect(x, y, (int) width, (int) length);

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Point p = this.getPosition();
		Color c = this.getColor();
		Color fillColor = this.getFillColor();
		Map<String, Double> properties = this.getProperties();
		Shape copy = new Rectangle();
		copy.setColor(c);
		copy.setFillColor(fillColor);
		copy.setPosition(p);
		copy.setProperties(properties);
		return copy;

	}
}
