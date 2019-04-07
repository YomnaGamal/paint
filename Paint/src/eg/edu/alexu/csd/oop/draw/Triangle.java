package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Triangle extends Shapes {

	double type, x1, y1, base, height;

	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		x1 = properties.get("x1");
		y1 = properties.get("y1");
		base = properties.get("base");
		height = properties.get("height");
		type = properties.get("type");
	}

	@Override
	public Map<String, Double> getProperties() {
		// TODO Auto-generated method stub
		Map<String, Double> properties = new HashMap<String, Double>();
		properties.put("x1", x1);
		properties.put("y1", y1);
		properties.put("height", height);
		properties.put("base", base);
		properties.put("type", type);
		return properties;
	}

	@Override
	public void draw(Object canvas) {
		// TODO Auto-generated method stub
		Graphics g = (Graphics) canvas;
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		int[] x = new int[3];
		int[] y = new int[3];
		if (type == (double) 1) {
			x[0] = (int) this.getPosition().getX();
			x[1] = (int) (this.getPosition().getX() - base / 2);
			x[2] = (int) (this.getPosition().getX() + base / 2);
			if (y1 < this.getPosition().getY()) {
				y[0] = (int) (this.getPosition().getY() - height / 2);
				y[1] = (int) (this.getPosition().getY() + height / 2);
				y[2] = (int) (this.getPosition().getY() + height / 2);
			} else {
				y[0] = (int) (this.getPosition().getY() + height / 2);
				y[1] = (int) (this.getPosition().getY() - height / 2);
				y[2] = (int) (this.getPosition().getY() - height / 2);
			}
		} else if (type == (double) 2) {
			x[0] = (int)x1;
			x[1] = (int)x1;
			y[0] = (int)y1;
			y[2] = (int) y1;
			if (y1 > this.getPosition().getY() && x1 > this.getPosition().getX()) {
				x[0] = (int) (this.getPosition().getX() + base / 2);
				y[0] = (int) (this.getPosition().getY() + height / 2);
				x[1] = (int) (this.getPosition().getX() + base / 2);
				y[1] = (int) ((this.getPosition().getY() + height / 2) - height);
				x[2] = (int) ((this.getPosition().getX() + base / 2) - base);
				y[2] = (int) (this.getPosition().getY() + height / 2);
			} else if (y1 < this.getPosition().getY() && x1 < this.getPosition().getX()) {
				x[0] = (int) (this.getPosition().getX() - base / 2);
				y[0] = (int) (this.getPosition().getY() - height / 2);
				x[1] = (int) (this.getPosition().getX() - base / 2);
				y[1] = (int) ((this.getPosition().getY() - height / 2) + height);
				x[2] = (int) ((this.getPosition().getX() - base / 2) + base);
				y[2] = (int) (this.getPosition().getY() - height / 2);
			} else if (y1 < this.getPosition().getY() && x1 > this.getPosition().getX()) {
				x[0] = (int) (this.getPosition().getX() + base / 2);
				y[0] = (int) (this.getPosition().getY() - height / 2);
				x[1] = (int) (this.getPosition().getX() + base / 2);
				y[1] = (int) ((this.getPosition().getY() - height / 2) + height);
				x[2] = (int) ((this.getPosition().getX() + base / 2) - base);
				y[2] = (int) (this.getPosition().getY() - height / 2);
			} else if (y1 > this.getPosition().getY() && x1 < this.getPosition().getX()) {
				x[0] = (int) (this.getPosition().getX() - base / 2);
				y[0] = (int) (this.getPosition().getY() + height / 2);
				x[1] = (int) (this.getPosition().getX() - base / 2);
				y[1] = (int) ((this.getPosition().getY() + height / 2) - height);
				x[2] = (int) ((this.getPosition().getX() - base / 2) + base);
				y[2] = (int) (this.getPosition().getY() + height / 2);
			}
		}
		
		if (this.getFillColor() != null) {
			g.setColor(getFillColor());
			g.fillPolygon(x, y, 3);
		}
		g.setColor(getColor());
		g.drawPolygon(x, y, 3);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Point p = this.getPosition();
		Color c = this.getColor();
		Color fillColor = this.getFillColor();
		Map<String, Double> properties = this.getProperties();
		Shape copy = new Triangle();
		copy.setColor(c);
		copy.setFillColor(fillColor);
		copy.setPosition(p);
		copy.setProperties(properties);
		return copy;

	}
}
