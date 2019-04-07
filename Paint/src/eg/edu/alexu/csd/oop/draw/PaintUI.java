package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

@SuppressWarnings("serial")
public class PaintUI extends JFrame {

	private DrawingArea drawingArea;
	static DrawingEngine controller;
	JToggleButton line, rectangle, square, circle, ellipse, isoscTri, rightTri, removeSelection;
	JButton color, fillColor, undo, redo, refresh, remove, select, move, copy, resize, save, load;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new PaintUI(new Controller());
		frame.setVisible(true);
	}

	public PaintUI(DrawingEngine d) {
		controller = d;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width - 10, screenSize.height - 40);
		getContentPane().setLayout(null);
		drawingArea = new DrawingArea();
		drawingArea.setLocation(200, 100);
		getContentPane().add(drawingArea);

		JPanel drawingPanel = new JPanel();
		drawingPanel.setBounds(10, 100, 160, 500);
		ButtonGroup drawingButtons = new ButtonGroup();
		color = new JButton("Color");
		color.setForeground(Color.WHITE);
		color.setBackground(Color.BLACK);
		drawingPanel.add(color);
		color.addActionListener(actionListener);
		fillColor = new JButton("Fill Color");
		fillColor.setBackground(Color.WHITE);
		drawingPanel.add(fillColor);
		fillColor.addActionListener(actionListener);
		line = new JToggleButton("line");
		drawingButtons.add(line);
		drawingPanel.add(line);
		line.addActionListener(actionListener);
		rectangle = new JToggleButton("rectangle");
		drawingButtons.add(rectangle);
		drawingPanel.add(rectangle);
		rectangle.addActionListener(actionListener);
		square = new JToggleButton("square");
		drawingButtons.add(square);
		drawingPanel.add(square);
		square.addActionListener(actionListener);
		circle = new JToggleButton("circle");
		drawingButtons.add(circle);
		drawingPanel.add(circle);
		circle.addActionListener(actionListener);
		ellipse = new JToggleButton("ellipse");
		drawingButtons.add(ellipse);
		drawingPanel.add(ellipse);
		ellipse.addActionListener(actionListener);
		isoscTri = new JToggleButton("tri1");
		drawingButtons.add(isoscTri);
		drawingPanel.add(isoscTri);
		isoscTri.addActionListener(actionListener);
		rightTri = new JToggleButton("tri2");
		drawingButtons.add(rightTri);
		drawingPanel.add(rightTri);
		rightTri.addActionListener(actionListener);
		removeSelection = new JToggleButton("s");
		drawingButtons.add(removeSelection);
		drawingPanel.add(removeSelection);
		removeSelection.setVisible(false);
		getContentPane().add(drawingPanel);

		JPanel controls = new JPanel();
		controls.setBounds(50, 10, 1000, 70);
		undo = new JButton("Undo");
		controls.add(undo);
		undo.addActionListener(actionListener);
		redo = new JButton("Redo");
		controls.add(redo);
		redo.addActionListener(actionListener);
		select = new JButton("Select");
		controls.add(select);
		select.addActionListener(actionListener);
		remove = new JButton("Remove");
		controls.add(remove);
		remove.addActionListener(actionListener);
		move = new JButton("Move");
		controls.add(move);
		move.addActionListener(actionListener);
		copy = new JButton("Copy");
		controls.add(copy);
		copy.addActionListener(actionListener);
		resize = new JButton("Resize");
		controls.add(resize);
		resize.addActionListener(actionListener);
		save = new JButton("Save File");
		controls.add(save);
		save.addActionListener(actionListener);
		load = new JButton("Load File");
		controls.add(load);
		load.addActionListener(actionListener);
		refresh = new JButton("Refresh");
		controls.add(refresh);
		refresh.addActionListener(actionListener);
		getContentPane().add(controls);

	}

	ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == circle) {
				drawingArea.setS(new Circle());
				drawingArea.drawshape('c');
				drawingArea.setSelectedShape(null);
				//controller.addShape(drawingArea.getS());
			} else if (e.getSource() == rectangle) {
				drawingArea.setS(new Rectangle());
				drawingArea.drawshape('r');
				drawingArea.setSelectedShape(null);
				//controller.addShape(drawingArea.getS());
			} else if (e.getSource() == square) {
				drawingArea.setS(new Square());
				drawingArea.drawshape('s');
				drawingArea.setSelectedShape(null);
				//controller.addShape(drawingArea.getS());
			} else if (e.getSource() == ellipse) {
				drawingArea.setS(new Ellipse());
				drawingArea.drawshape('e');
				drawingArea.setSelectedShape(null);
				//controller.addShape(drawingArea.getS());
			} else if (e.getSource() == isoscTri) {
				drawingArea.setS(new Triangle());
				drawingArea.drawshape('t');
				drawingArea.setSelectedShape(null);
				//controller.addShape(drawingArea.getS());
			} else if (e.getSource() == rightTri) {
				drawingArea.setS(new Triangle());
				drawingArea.drawshape('T');
				drawingArea.setSelectedShape(null);
				//controller.addShape(drawingArea.getS());
			} else if (e.getSource() == line) {
				drawingArea.setS(new Line());
				drawingArea.drawshape('l');
				drawingArea.setSelectedShape(null);
				//controller.addShape(drawingArea.getS());
			} else if (e.getSource() == color) {
				Color c = JColorChooser.showDialog(getContentPane(), "Choose a color", Color.BLACK);
				color.setBackground(c);
				if (drawingArea.getSelectedShape() != null) {
					Shape oldShape = drawingArea.getSelectedShape();
					try {
						Shape newShape = (Shape) oldShape.clone();
						newShape.setColor(c);
						controller.updateShape(oldShape, newShape);
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					controller.refresh(drawingArea.g);
					drawingArea.setSelectedShape(null);
				}
				drawingArea.setColor(c);
			} else if (e.getSource() == fillColor) {
				Color c = JColorChooser.showDialog(getContentPane(), "Choose a color", null);
				fillColor.setBackground(c);
				if (drawingArea.getSelectedShape() != null) {
					Shape oldShape = drawingArea.getSelectedShape();
					try {
						Shape newShape = (Shape) oldShape.clone();
						newShape.setFillColor(c);
						controller.updateShape(oldShape, newShape);
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					controller.refresh(drawingArea.g);
					drawingArea.setSelectedShape(null);
				}
				drawingArea.setFillColor(c);
			} else if (e.getSource() == undo) {
				drawingArea.setSelectedShape(null);
				controller.undo();
				controller.refresh(drawingArea.g);
			} else if (e.getSource() == redo) {
				drawingArea.setSelectedShape(null);
				controller.redo();
				controller.refresh(drawingArea.g);
			} else if (e.getSource() == refresh) {
				drawingArea.setSelectedShape(null);
				controller.refresh(drawingArea.g);
			} else if (e.getSource() == select) {
				//if selected, remove select
				drawingArea.setSelectedShape(null);
				drawingArea.select();
			} else if (e.getSource() == remove) {
				if (drawingArea.getSelectedShape() != null) {
					controller.removeShape(drawingArea.getSelectedShape());
					controller.refresh(drawingArea.g);
					drawingArea.setSelectedShape(null);
				}
			} else if (e.getSource() == move) {
				if (drawingArea.getSelectedShape() != null) {
					drawingArea.move();
					controller.refresh(drawingArea.g);
					//drawingArea.setSelectedShape(null);
				}
			} else if (e.getSource() == resize) {
				if (drawingArea.getSelectedShape() != null) {
					drawingArea.resize();
					controller.refresh(drawingArea.g);
					//drawingArea.setSelectedShape(null);
				}
			} else if (e.getSource() == copy) {
				if (drawingArea.getSelectedShape() != null) {
					Shape shape = drawingArea.getSelectedShape();
					String name = drawingArea.getSelectedShape().getClass().getName();
					try {
						Shape copy = (Shape) shape.clone();
						Point p = (Point) shape.getPosition();
						Point p2;
						if (name.substring(26, name.length()).equals("Line")) {
							Map<String, Double> properties = drawingArea.getSelectedShape().getProperties();
							double x1 = properties.get("x");
							double y1 = properties.get("y");
							properties.put("x", x1 + 70);
							copy.setProperties(properties);
							p2 = new Point((int)(p.getX() + 70),(int) p.getY());
						} else {
							p2 = new Point((int)(p.getX()+20), (int)(p.getY()+20));
						}
						copy.setPosition(p2);
						controller.addShape(copy);
						controller.refresh(drawingArea.g);
						drawingArea.setSelectedShape(copy);
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else if (e.getSource() == save) {
				JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
				j.setAcceptAllFileFilterUsed(false);
				j.setFileSelectionMode(JFileChooser.FILES_ONLY);
				j.addChoosableFileFilter(new FileNameExtensionFilter(".JsOn", ".JsOn"));
				j.addChoosableFileFilter(new FileNameExtensionFilter(".XmL", ".XmL"));
	            int r = j.showSaveDialog(null); 
	            if (r == JFileChooser.APPROVE_OPTION) 
	            {
	            	FileFilter l = j.getFileFilter();
	                String path = j.getSelectedFile().getAbsolutePath() + l.getDescription();
	                controller.save(path);
	            }
				drawingArea.setSelectedShape(null);
			} else if (e.getSource() == load) {
				JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
				j.setAcceptAllFileFilterUsed(false);
				j.setFileSelectionMode(JFileChooser.FILES_ONLY);
				j.addChoosableFileFilter(new FileNameExtensionFilter(".JsOn", "JsOn"));
				j.addChoosableFileFilter(new FileNameExtensionFilter(".XmL", "XmL"));
	            int r = j.showOpenDialog(null); 
	            if (r == JFileChooser.APPROVE_OPTION) 
	            {
	                String path = j.getSelectedFile().getAbsolutePath();
	                controller.load(path);
	                drawingArea.setShapes(controller.getShapes());
	                drawingArea.repaint();
	            }
				drawingArea.setSelectedShape(null);
			}
			//copy to drawingArea
			drawingArea.setShapes(controller.getShapes());
		}

	};
}
