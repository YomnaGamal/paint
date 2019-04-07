package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Controller implements DrawingEngine {

	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private Stack<ArrayList<Shape>> undo = new Stack<ArrayList<Shape>>();
	private Stack<ArrayList<Shape>> redo = new Stack<ArrayList<Shape>>();
	private ArrayList<Shape> lastundo = new ArrayList<Shape>();
	private int undoLimit = 0;
	private boolean redoFlag = false;

	@Override
	public void refresh(Object canvas) {
		// TODO Auto-generated method stub
		if (!canvas.getClass().getName().equals("javax.swing.DebugGraphics")) {
			canvas = (Graphics) canvas;
			Graphics2D g2 = (Graphics2D) canvas;
			g2.setStroke(new BasicStroke(2));
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			((Graphics) canvas).setColor(Color.WHITE);
			((Graphics) canvas).fillRect(0, 0, screenSize.width, screenSize.height);
		}
		for (int i = 0; i < shapes.size(); i++) {
			shapes.get(i).draw(canvas);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addShape(Shape shape) {
		// TODO Auto-generated method stub
		if (!redo.isEmpty()) {
			redo.clear();
		}
		if (undo.size() == 0) {
			undo.add(new ArrayList<Shape>());
			undoLimit++;
		}
		if (undoLimit > 20) {
			lastundo = (ArrayList<Shape>) undo.get(0).clone();
			undo.remove(0);
			undoLimit--;
		}
		undoLimit++;
		shapes.add(shape);
		undo.push((ArrayList<Shape>) shapes.clone());

	}

	@SuppressWarnings("unchecked")
	@Override
	public void removeShape(Shape shape) {
		// TODO Auto-generated method stub
		if (!redo.isEmpty()) {
			redo.clear();
		}
		if (undoLimit > 20) {
			undo.remove(0);
			undoLimit--;
		}
		undoLimit++;
		int index = shapes.indexOf(shape);
		shapes.remove(index);
		undo.push((ArrayList<Shape>) shapes.clone());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		// TODO Auto-generated method stub
		if (!redo.isEmpty()) {
			redo.clear();
		}
		if (undoLimit > 20) {
			undo.remove(0);
			undoLimit--;
		}
		undoLimit++;
		int index = shapes.indexOf(oldShape);
		shapes.set(index, newShape);
		undo.push((ArrayList<Shape>) shapes.clone());
	}

	@Override
	public Shape[] getShapes() {
		// TODO Auto-generated method stub
		Shape[] allShapes = new Shape[shapes.size()];
		for (int i = 0; i < shapes.size(); i++) {
			allShapes[i] = shapes.get(i);
		}
		return allShapes;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		// TODO Auto-generated method stub
		File path = new File("RoundRectangle.jar");
		List<Class<? extends Shape>> classes = new LinkedList<Class<? extends Shape>>();
		JarFile jarFile = null;
		try {
			jarFile = new JarFile("RoundRectangle.jar");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Enumeration<JarEntry> e = jarFile.entries();
		ClassLoader loader = null;
		try {
			loader = URLClassLoader.newInstance(new URL[] { path.toURL() }, getClass().getClassLoader());
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		while (e.hasMoreElements()) {
			JarEntry je = e.nextElement();
			if (je.isDirectory() || !je.getName().endsWith(".class")) {
				continue;
			}
			System.out.println(je.getName());
			String className = je.getName().substring(0, je.getName().length() - 6);
			className = className.replace('/', '.');
			Class<? extends Shape> c = null;
			try {
				c = (Class<? extends Shape>) loader.loadClass(className);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			classes.add(c);
		}
		return classes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		redoFlag = true;
		if (undo.size() > 1) {
			redo.push(undo.pop());
			shapes = (ArrayList<Shape>) undo.peek().clone();
			// refresh();
			undoLimit--;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		if (redoFlag) {
			if (!redo.isEmpty()) {
				undo.push(redo.pop());
				shapes = (ArrayList<Shape>) undo.peek().clone();
				undoLimit++;
			}
		}
	}

	@Override
	public void save(String path) {
		// TODO Auto-generated method stub
		String fileType = path.substring(path.length() - 4, path.length());
		if (fileType.equals("JsOn")) {
			Json json = new Json(path);
			json.save(shapes);
		} else {
			Xml xml = new Xml(path);
			xml.save(shapes);
		}
	}

	@Override
	public void load(String path) {
		// TODO Auto-generated method stub
		String fileType = path.substring(path.length() - 4, path.length());
		if (fileType.equals("JsOn")) {
			Json json = new Json(path);
			try {
				shapes = json.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Xml xml = new Xml(path);
			try {
				shapes = xml.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		undo.clear();
		redo.clear();
	}
}
