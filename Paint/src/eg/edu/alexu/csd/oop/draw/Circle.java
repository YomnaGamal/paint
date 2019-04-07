package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Circle extends Shapes {
	double r;

	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		r = properties.get("Radius");
	}

	@Override
	public Map<String, Double> getProperties() {
		// TODO Auto-generated method stub
		Map<String, Double> p = new HashMap<String, Double>();
		p.put("Radius", r);
		return p;
	}

	@Override
	public void draw(Object canvas) {
		// TODO Auto-generated method stub
		Graphics g = (Graphics) canvas;
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		if (this.getFillColor() != null) {
			g.setColor(getFillColor());
			g.fillOval(p.x - (int) r, p.y - (int) r, 2 * (int) r, 2 * (int) r);
		}
		g.setColor(getColor());
		g.drawOval(p.x - (int) r, p.y - (int) r, 2 * (int) r, 2 * (int) r);

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Point p = this.getPosition();
		Color c = this.getColor();
		Color fillColor = this.getFillColor();
		Map<String, Double> properties = this.getProperties();
		Shape copy = new Circle();
		copy.setColor(c);
		copy.setFillColor(fillColor);
		copy.setPosition(p);
		copy.setProperties(properties);
		return copy;

	}
}
