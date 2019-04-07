package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Json {

	String path;

	public Json(String path) {
		this.path = path;
	}

	public void save(ArrayList<Shape> shapes) {
		File f = new File(path);
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write("{\"ShapeArray\" :");
			bw.newLine();
			bw.write("[");
			bw.newLine();
			for (int i = 0; i < shapes.size(); i++) {
				Shape x = shapes.get(i);
				Point position = (Point) x.getPosition();
				Color color = (Color) x.getColor();
				Color fillColor = (Color) x.getFillColor();
				Map<String, Double> properties = x.getProperties();
				String id = x.getClass().toString();
				bw.write("{ \"className\" :  \"" + id.substring(6, id.length()) + "\",");
				bw.newLine();
				if (position != null) {
					bw.write("\"x\" : \"" + Double.toString(position.getX()) + "\",");
					bw.newLine();
					bw.write("\"y\" : \"" + Double.toString(position.getY()) + "\",");
					bw.newLine();
				}
				if (properties != null) {
					Set<String> keys = properties.keySet();
					Iterator<String> it = keys.iterator();
					while (it.hasNext()) {
						String s = it.next();
						bw.write("\"" + s + "\" : \"" + Double.toString(properties.get(s)) + "\",");
						bw.newLine();
					}
				}
				if (color != null) {
					bw.write("\"color\" : \"" + color.toString() + "\",");
					bw.newLine();
				}
				if (fillColor == null) {
					bw.write("\"fillcolor\" : \"" + "null" + "\"");
				} else {
					bw.write("\"fillcolor\" : \"" + fillColor.toString() + "\"");
				}
				bw.newLine();
				bw.write("}");
				if (i != shapes.size() - 1) {
					bw.write(",");
				}
				bw.newLine();
			}
			bw.write("]");
			bw.newLine();
			bw.write("}");
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Shape> load() throws IOException {
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		try {
			File f = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			line = br.readLine();
			while (!line.equals("}")) {
				Point position = new Point();
				Map<String, Double> properties = new HashMap<String, Double>();
				line = br.readLine();
				if (line.equals("]")) {
					break;
				}
				String className = line.substring(line.indexOf(":") + 4, line.length() - 2);
				Class<?> cl = null;
				try {
					cl = Class.forName(className);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Constructor<?> cons = null;
				try {
					cons = cl.getConstructor();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Shape shape = null;
				try {
					shape = (Shape) cons.newInstance();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				line = br.readLine();
				Pattern pattern;
				String REGEX;
				Matcher m;
				if (line.contains("x")) {
					 REGEX = "\\d+";
						pattern = Pattern.compile(REGEX);
						 m = pattern.matcher(line);
						if (m.find()) {
							String x = line.substring(m.start(), m.end());
							position.x = Integer.parseInt(x);
						}
						line = br.readLine();
						m = pattern.matcher(line);
						if (m.find()) {
							String y = line.substring(m.start(), m.end());
							position.y = Integer.parseInt(y);
						}
						shape.setPosition(position);
						line = br.readLine();
				}
				while (!(line.contains("color")) || !(line.contains("fillcolor"))) {
					String key = null;
					double value = 0;
					REGEX = "\\w+(\\s)?+(\\w+)?";
					pattern = Pattern.compile(REGEX);
					m = pattern.matcher(line);
					if (m.find()) {
						key = line.substring(m.start(), m.end());
					}
					REGEX = "\\d+(\\.)?+(\\d+)?";
					pattern = Pattern.compile(REGEX);
					m = pattern.matcher(line);
					if (m.find()) {
						String x = line.substring(m.start(), m.end());
						value = Double.parseDouble(x);
					}
					properties.put(key, value);
					line = br.readLine();
					shape.setProperties(properties);
				}
				int i = 0, r = 0, g = 0, b = 0;
				if (!line.contains("fillcolor")) {
					REGEX = "\\d+";
					pattern = Pattern.compile(REGEX);
					m = pattern.matcher(line);
					while (m.find()) {
						String x = line.substring(m.start(), m.end());
						switch (i) {
						case 0:
							r = Integer.parseInt(x);
							break;
						case 1:
							g = Integer.parseInt(x);
							break;
						case 2:
							b = Integer.parseInt(x);
							break;
						}
						i++;
					}
					Color c = new Color(r, g, b);
					shape.setColor(c);
					line = br.readLine();
				}
				
				Color fillColor;
				if (line.equals("\"fillcolor\" : \"" + "null" + "\"")) {
					fillColor = null;
				} else {
					REGEX = "\\d+";
					pattern = Pattern.compile(REGEX);
					m = pattern.matcher(line);
					i = 0;
					while (m.find()) {
						String x = line.substring(m.start(), m.end());
						switch (i) {
						case 0:
							r = Integer.parseInt(x);
							break;
						case 1:
							g = Integer.parseInt(x);
							break;
						case 2:
							b = Integer.parseInt(x);
							break;
						}
						i++;
					}
					fillColor = new Color(r, g, b);
				}
				shape.setFillColor(fillColor);
				shapes.add(shape);
				// br.readLine();
				line = br.readLine();
			}
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return shapes;
	}

	private Shape shape(String id) {
		Shape s = null;
		if (id.equals("Line")) {
			s = new Line();
		} else if (id.equals("Square")) {
			s = new Square();
		} else if (id.equals("Rectangle")) {
			s = new Rectangle();
		} else if (id.equals("Square")) {
			s = new Square();
		} else if (id.equals("Ellipse")) {
			s = new Ellipse();
		} else if (id.equals("Triangle")) {
			s = new Triangle();
		} else if (id.equals("Circle")) {
			s = new Circle();
		}
		return s;
	}
}
