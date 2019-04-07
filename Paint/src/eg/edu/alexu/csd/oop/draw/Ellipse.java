package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

public class Ellipse extends Shapes {

	double maj, min;

	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		maj = properties.get("Semi Major");
		min = properties.get("Semi Minor");
	}

	@Override
	public Map<String, Double> getProperties() {
		// TODO Auto-generated method stub
		Map<String, Double> p = new HashMap<String, Double>();
		p.put("Semi Major", maj);
		p.put("Semi Minor", min);
		return p;
	}

	@Override
	public void draw(Object canvas) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) canvas;
		g2.setStroke(new BasicStroke(2));
		int x = (int) (this.getPosition().getX() - maj / 2);
		int y = (int) (this.getPosition().getY() - min / 2);
		Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, maj, min);
		if (this.getFillColor() != null) {
			g2.setColor(getFillColor());
			g2.fill(ellipse);
		}
		g2.setColor(getColor());
		g2.draw(ellipse);

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Point p = this.getPosition();
		Color c = this.getColor();
		Color fillColor = this.getFillColor();
		Map<String, Double> properties = this.getProperties();
		Shape copy = new Ellipse();
		copy.setColor(c);
		copy.setFillColor(fillColor);
		copy.setPosition(p);
		copy.setProperties(properties);
		return copy;

	}
}
