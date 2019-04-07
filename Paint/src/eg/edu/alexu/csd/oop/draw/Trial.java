package eg.edu.alexu.csd.oop.draw;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.test.DummyShape;
import eg.edu.alexu.csd.oop.test.TestRunner;

public class Trial {

	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		// TODO Auto-generated method stub
		/*
		 * ArrayList<Shape> shapes = new ArrayList<Shape>(); Shape s = new Square();
		 * Point p = new Point(); p.x = 2; p.y = 3; /*Map<String, Double> prop = new
		 * HashMap<String, Double>(); prop.put("side", (double) 6); prop.put("X", 5.3);
		 * prop.put("Y", 2.6); s.setProperties(null); Color c = Color.BLACK; Color fc =
		 * Color.WHITE; s.setColor(c); s.setFillColor(fc); s.setPosition(p);
		 * //s.setProperties(prop);
		 * 
		 * 
		 * Shape l = new Line();
		 * 
		 * 
		 * Point p2 = new Point(); p2.x = 2; p2.y = 7; Map<String, Double> prop2 = new
		 * HashMap<String, Double>(); prop2.put("x", 10.9); prop2.put("y", (double) 19);
		 * Color c2 = Color.BLACK; l.setColor(c2); l.setPosition(p2);
		 * l.setProperties(prop2); shapes.add(l); shapes.add(s); Json save = new
		 * Json("x.JsOn"); save.save(shapes); shapes = save.load();
		 * System.out.println(shapes.get(0).getClass().getName());
		 */

		Controller c1 = new Controller();
		Controller c2 = new Controller();
		List<Class<? extends Shape>> supportedShapes = c1.getSupportedShapes();
		for (Class<? extends Shape> shapeClass : supportedShapes) {
			try {
				c1.addShape((Shape) shapeClass.newInstance());
				System.out.println(c1.getShapes().length);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Shape shape1 = new DummyShape();
		shape1.setColor(Color.RED);
		c1.addShape(shape1);
		System.out.println(c1.getShapes().length);
		int countBefore = 0;
		countBefore = c1.getShapes().length;

		String fileName = "x.JsOn";
		c1.save(fileName);

		supportedShapes = c1.getSupportedShapes();
		for (Class<? extends Shape> shapeClass : supportedShapes) {
			try {
				c1.addShape((Shape) shapeClass.newInstance());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		c2.load(fileName);
		int countAfter = 0;
		countAfter = c2.getShapes().length;
		System.out.println(countAfter);
	}
}
