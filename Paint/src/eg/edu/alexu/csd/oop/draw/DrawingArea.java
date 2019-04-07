package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawingArea extends JPanel implements MouseListener, MouseMotionListener {

	public Graphics g;
	private Graphics2D g2;
	private Shape s, selectedShape = null, updatedShape;
	private Point start, end, click;
	private Map<String, Double> map;
	private Shape[] shapes;
	private Color color = Color.BLACK, fillColor = null;
	char shape;
	boolean select = false, move = false, resize = false;
	Point upper, lower;
	int length, width;
	private Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);

	public Shape getSelectedShape() {
		return selectedShape;
	}

	public void setSelectedShape(Shape selectedShape) {
		this.selectedShape = selectedShape;
	}

	public Shape getS() {
		return s;
	}

	public void setS(Shape s) {
		this.s = s;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public void setShapes(Shape[] shapes) {
		this.shapes = shapes;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		if (shapes != null) {
			for (int i = 0; i < shapes.length; i++) {
				shapes[i].draw(g);
			}
		}
		if (selectedShape != null && !selectedShape.getClass().getName()
				.substring(26, selectedShape.getClass().getName().length()).equals("Line")) {
			g.setColor(Color.LIGHT_GRAY);
			g2.setStroke(dashed);
			g.drawRect(upper.x, upper.y, width, length);
		} else if (selectedShape != null && selectedShape.getClass().getName()
				.substring(26, selectedShape.getClass().getName().length()).equals("Line")) {
			g.setColor(Color.LIGHT_GRAY);
			g2.setStroke(dashed);
			Point p = (Point) selectedShape.getPosition();
			Map<String, Double> properties = selectedShape.getProperties();
			double x1 = properties.get("x");
			double y1 = properties.get("y");
			g.drawLine(p.x, p.y, (int) x1, (int) y1);
		}

	}

	private void setUpDrawingGraphics() {
		g = getGraphics();
	}

	public DrawingArea() {
		setBackground(Color.WHITE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width - 300, screenSize.height - 200);
		setLayout(null);
		g2 = (Graphics2D) g;
	}

	public void drawshape(char sh) {
		shape = sh;
	}

	public void drawing() {
		s.setColor(color);
		s.setFillColor(fillColor);
		map = new HashMap<String, Double>();
		switch (shape) {
		case 'r':
			map.put("length", Math.abs((end.getY() - start.getY())));
			map.put("width", Math.abs((end.getX() - start.getX())));
			s.setProperties(map);
			Point center = new Point();
			center.x = start.x + (end.x - start.x) / 2;
			center.y = start.y + (end.y - start.y) / 2;
			s.setPosition(center);
			setUpDrawingGraphics();
			s.draw(g);
			break;
		case 'c':
			map.put("X", (double) start.x);
			map.put("Y", (double) start.y);
			double d = Math.abs(end.getX() - start.getX());
			if (d < Math.abs(end.getY() - start.getY())) {
				d = Math.abs(end.getY() - start.getY());
			}
			map.put("Radius", d / 2);
			s.setProperties(map);
			center = new Point();
			if (start.getX() > end.getX() && start.getY() > end.getY()) {
				center.x = (int) (end.getX() + d / 2);
				center.y = (int) (end.getY() + d / 2);
			} else if (start.getX() < end.getX() && start.getY() < end.getY()) {
				center.x = (int) (start.getX() + d / 2);
				center.y = (int) (start.getY() + d / 2);
			} else if (start.getX() > end.getX() && start.getY() < end.getY()) {
				center.x = (int) (end.getX() + d / 2);
				center.y = (int) (start.getY() + d / 2);
			} else {
				center.x = (int) (start.getX() + d / 2);
				center.y = (int) (end.getY() + d / 2);
			}
			s.setPosition(center);
			setUpDrawingGraphics();
			s.draw(g);
			break;
		case 's':
			map.put("X", (double) start.x);
			map.put("Y", (double) start.y);
			d = Math.abs(end.getX() - start.getX());
			if (d < Math.abs(end.getY() - start.getY())) {
				d = Math.abs(end.getY() - start.getY());
			}
			map.put("side", d);
			s.setProperties(map);
			center = new Point();
			if (start.getX() > end.getX() && start.getY() > end.getY()) {
				center.x = (int) (end.getX() + d / 2);
				center.y = (int) (end.getY() + d / 2);
			} else if (start.getX() < end.getX() && start.getY() < end.getY()) {
				center.x = (int) (start.getX() + d / 2);
				center.y = (int) (start.getY() + d / 2);
			} else if (start.getX() > end.getX() && start.getY() < end.getY()) {
				center.x = (int) (end.getX() + d / 2);
				center.y = (int) (start.getY() + d / 2);
			} else {
				center.x = (int) (start.getX() + d / 2);
				center.y = (int) (end.getY() + d / 2);
			}
			s.setPosition(center);
			map.put("side", d);
			setUpDrawingGraphics();
			s.draw(g);
			break;
		case 'e':
			map.put("X", start.getX());
			map.put("Y", start.getY());
			map.put("Semi Minor", Math.abs((end.getY() - start.getY())));
			map.put("Semi Major", Math.abs((end.getX() - start.getX())));
			s.setProperties(map);
			center = new Point();
			center.x = start.x + (end.x - start.x) / 2;
			center.y = start.y + (end.y - start.y) / 2;
			s.setPosition(center);
			setUpDrawingGraphics();
			s.draw(g);
			break;
		case 'l':
			s.setPosition(start);
			map.put("x", end.getX());
			map.put("y", end.getY());
			s.setProperties(map);
			setUpDrawingGraphics();
			s.draw(g);
			break;
		case 't':
			map.put("x1", start.getX());
			map.put("y1", start.getY());
			map.put("type", (double) 1);
			map.put("height", Math.abs((start.getY() - end.getY())));
			map.put("base", 2 * Math.abs((start.getX() - end.getX())));
			s.setProperties(map);
			center = new Point();
			center.x = start.x;
			if (start.getY() < end.getY()) {
				center.y = start.y + Math.abs(end.y - start.y) / 2;
			} else {
				center.y = start.y - Math.abs(end.y - start.y) / 2;
			}
			s.setPosition(center);
			setUpDrawingGraphics();
			s.draw(g);
			break;
		case 'T':
			map.put("x1", start.getX());
			map.put("y1", end.getY());
			map.put("type", (double) 2);
			map.put("height", Math.abs((start.getY() - end.getY())));
			map.put("base", Math.abs(start.getX() - end.getX()));
			s.setProperties(map);
			center = new Point();
			center.x = start.x + (end.x - start.x) / 2;
			center.y = start.y + (end.y - start.y) / 2;
			s.setPosition(center);
			setUpDrawingGraphics();
			s.draw(g);
			break;
		}
	}

	public void select() {
		this.addMouseListener(this);
		select = true;
	}

	public void selectShape() {
		for (int i = 0; i < shapes.length; i++) {
			Map<String, Double> properties = shapes[i].getProperties();
			Point position = (Point) shapes[i].getPosition();
			Class<? extends Shape> c = shapes[i].getClass();
			String name = c.getName().substring(26, c.getName().length());
			if (name.equals("Rectangle")) {
				double l = properties.get("length");
				double w = properties.get("width");
				length = (int) l;
				width = (int) w;
				upper = new Point((int) (position.getX() - w / 2), (int) (position.getY() - l / 2));
				lower = new Point((int) (position.getX() + w / 2), (int) (position.getY() + l / 2));
				if (click.getX() > upper.getX() && click.getX() < lower.getX() && click.getY() > upper.getY()
						&& click.getY() < lower.getY()) {
					selectedShape = shapes[i];
					repaint();
				}
			} else if (name.equals("Square")) {
				double side = properties.get("side");
				length = (int) side;
				width = (int) side;
				upper = new Point((int) (position.getX() - side / 2), (int) (position.getY() - side / 2));
				lower = new Point((int) (position.getX() + side / 2), (int) (position.getY() + side / 2));
				if (click.getX() > upper.getX() && click.getX() < lower.getX() && click.getY() > upper.getY()
						&& click.getY() < lower.getY()) {
					selectedShape = shapes[i];
					repaint();

				}
			} else if (name.equals("Ellipse")) {
				double maj = properties.get("Semi Major");
				double min = properties.get("Semi Minor");
				length = (int) min;
				width = (int) maj;
				upper = new Point((int) (position.getX() - maj / 2), (int) (position.getY() - min / 2));
				lower = new Point((int) (position.getX() + maj / 2), (int) (position.getY() + min / 2));
				if (click.getX() > upper.getX() && click.getX() < lower.getX() && click.getY() > upper.getY()
						&& click.getY() < lower.getY()) {
					selectedShape = shapes[i];
					repaint();
				}
			} else if (name.equals("Line")) {
				double x = properties.get("x"), y = properties.get("y");
				double dx1 = x - position.getX();
				double dy1 = y - position.getY();
				double distance1 = Math.sqrt(dx1 * dx1 + dy1 * dy1);
				double dx2 = x - click.getX();
				double dy2 = y - click.getY();
				double distance2 = Math.sqrt(dx2 * dx2 + dy2 * dy2);
				double dx3 = click.getX() - position.getX();
				double dy3 = click.getY() - position.getY();
				double distance3 = Math.sqrt(dx3 * dx3 + dy3 * dy3);
				if (Math.abs(distance1 - (distance3 + distance2)) < 0.3) {
					selectedShape = shapes[i];
				}
			} else if (name.equals("Triangle")) {
				// double x1 = properties.get("x1"), y1 = properties.get("y1");
				double height = properties.get("height"), base = properties.get("base");
				position = (Point) shapes[i].getPosition();
				upper = new Point((int) (position.getX() - base / 2), (int) (position.getY() - height / 2));
				lower = new Point((int) (position.getX() + base / 2), (int) (position.getY() + height / 2));
				length = (int) height;
				width = (int) base;
				if (click.getX() > upper.getX() && click.getX() < lower.getX() && click.getY() > upper.getY()
						&& click.getY() < lower.getY()) {
					selectedShape = shapes[i];
					repaint();
				}
			} else if (name.equals("Circle")) {
				double r = properties.get("Radius");
				upper = new Point((int) (position.getX() - r), (int) (position.getY() - r));
				lower = new Point((int) (position.getX() + r), (int) (position.getY() + r));
				length = (int) (2 * r);
				width = (int) (2 * r);
				if (click.getX() > upper.getX() && click.getX() < lower.getX() && click.getY() > upper.getY()
						&& click.getY() < lower.getY()) {
					selectedShape = shapes[i];
					repaint();

				}
			}
			select = false;
			this.removeMouseListener(this);

		}
	}

	public void move() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		move = true;
	}

	public void moving() {
		try {
			updatedShape = (Shape) selectedShape.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double shiftX, shiftY;
		shiftX = end.getX() - start.getX();
		shiftY = end.getY() - start.getY();
		Point oldPosition = (Point) selectedShape.getPosition();
		Point newPosition = null;
		String name = selectedShape.getClass().getName().substring(26, selectedShape.getClass().getName().length());
		if (name.equals("Triangle")) {
			Map<String, Double> properties = selectedShape.getProperties();
			double x1 = properties.get("x1");
			double y1 = properties.get("y1");
			properties.put("x1", x1 + shiftX);
			properties.put("y1", y1 + shiftY);
			updatedShape.setProperties(properties);
			newPosition = new Point((int) (oldPosition.getX() + shiftX), (int) (oldPosition.getY() + shiftY));
		} else if (name.equals("Line")) {
			Map<String, Double> properties = selectedShape.getProperties();
			newPosition = new Point((int) (oldPosition.getX() + shiftX), (int) (oldPosition.getY() + shiftY));
			double x = properties.get("x");
			double y = properties.get("y");
			properties.put("x", x + shiftX);
			properties.put("y", y + shiftY);
			updatedShape.setProperties(properties);
		} else {
			double x, y;
			x = start.getX() - oldPosition.getX();
			y = start.getY() - oldPosition.getY();
			newPosition = new Point((int) (oldPosition.getX() + shiftX + x), (int) (oldPosition.getY() + shiftY + y));
		}
		updatedShape.setPosition(newPosition);
		updatedShape.draw(g);
		repaint();
	}

	public void resize() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		resize = true;
	}

	public void resizeShape() {
		try {
			updatedShape = (Shape) selectedShape.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Class<? extends Shape> c = updatedShape.getClass();
		String name = c.getName().substring(26, c.getName().length());
		Map<String, Double> properties = updatedShape.getProperties();
		Point position = (Point) updatedShape.getPosition();
		double deltaL = 0, deltaW = 0;
		Point realPos = new Point();
		if(name.equals("Line")) {
			realPos = (Point) position.clone();
			Point p = new Point();
			p.setLocation(Math.min(position.getX(), properties.get("x"))+Math.abs(position.getX()-properties.get("x"))/2, Math.max(position.getY(), properties.get("y"))-Math.abs(position.getY()-properties.get("y"))/2);
			position = (Point) p.clone();
		}
		if (!((start.getX() > position.getX()) && (end.getX() < position.getX())
				|| (start.getX() < position.getX()) && (end.getX() > position.getX())
				|| (start.getY() > position.getY()) && (end.getY() < position.getY())
				|| (start.getY() < position.getY()) && (end.getY() > position.getY()))) {
			if (start.getX() >= position.getX() && start.getY() >= position.getY()) {
				deltaL = end.getY() - start.getY();
				deltaW = end.getX() - start.getX();
			} else if (start.getX() < position.getX() && start.getY() < position.getY()) {
				deltaL = start.getY() - end.getY();
				deltaW = start.getX() - end.getX();
			} else if (start.getX() >= position.getX() && start.getY() < position.getY()) {
				deltaL = start.getY() - end.getY();
				deltaW = end.getX() - start.getX();
			} else if (start.getX() < position.getX() && start.getY() >= position.getY()) {
				deltaL = end.getY() - start.getY();
				deltaW = start.getX() - end.getX();
			}
			if (name.equals("Rectangle")) {
				double l = properties.get("length");
				double w = properties.get("width");
				properties.put("length", l + deltaL);
				properties.put("width", w + deltaW);
			} else if (name.equals("Circle")) {
				double r = properties.get("Radius");
				double deltaR = Math.max(deltaW, deltaL) / 2;
				properties.put("Radius", r + deltaR);
			} else if (name.equals("Square")) {
				double side = properties.get("side");
				double deltaS = Math.max(deltaW, deltaL);
				properties.put("side", side + deltaS);
			} else if (name.equals("Ellipse")) {
				double maj = properties.get("Semi Major");
				double min = properties.get("Semi Minor");
				properties.put("Semi Major", maj + deltaW);
				properties.put("Semi Minor", min + deltaL);
			} else if (name.equals("Triangle")) {
				double type = properties.get("type");
				double x1 = properties.get("x1");
				double y1 = properties.get("y1");
				double height = properties.get("height");
				double base = properties.get("base");
				properties.put("height", height + deltaL);
				properties.put("base", base + deltaW);
			} else if (name.equals("Line")) {
				double firstpx = properties.get("x"),x = realPos.getX();
				double firstpy = properties.get("y"),y;
				double m = (firstpy - realPos.getY())/(firstpx - realPos.getX());
				double con = firstpy-m*firstpx;
				if (firstpx < realPos.getX()) {
					firstpx = firstpx-deltaW/2;
					x = realPos.getX()+deltaW/2;
					y = m * x +con;
					firstpy = firstpx * m + con;
				} else {
					x = realPos.getX() - deltaW/2;
					y = m * x +con;
					firstpx = firstpx +deltaW/2;
					firstpy = firstpx * m + con;
				}
				properties.put("x", firstpx);
				properties.put("y", firstpy);
				Point newPosition = new Point((int)(x), (int)(y));
				updatedShape.setPosition(newPosition);
			}
			updatedShape.setProperties(properties);
			updatedShape.draw(g);
			repaint();
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		end = e.getPoint();
		if (move) {
			moving();

		} else if (resize) {
			resizeShape();
		} else {
			drawing();
		}
		this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		start = e.getPoint();
		if (select) {
			click = e.getPoint();
			selectShape();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (move || resize) {
			PaintUI.controller.updateShape(selectedShape, updatedShape);
			selectedShape = null;
			move = false;
			resize = false;
		} else {
			PaintUI.controller.addShape(s);
		}
		shapes = PaintUI.controller.getShapes();
		repaint();
		this.removeMouseListener(this);
		this.removeMouseMotionListener(this);
	}
}
