package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lejos.robotics.geometry.Line;
import lejos.robotics.geometry.Rectangle;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.Pose;

public class Map extends Canvas {

    private LineMap map;
    private static final int MULTIPLIER = 3;

    private GraphicsContext gc = getGraphicsContext2D();
    private double mapH, mapW;

    public Map(LineMap map, Pose initialPose) {
	this.map = map.flip();

	Rectangle rectangle = map.getBoundingRect();
	mapH = rectangle.getMaxY();
	mapW = rectangle.getMaxX();

	double w = rectangle.getMaxX() * MULTIPLIER;
	double h = rectangle.getMaxY() * MULTIPLIER;
	setHeight(h);
	setWidth(w);

	drawMap();
	drawPose(initialPose);
    }

    public void redraw(Pose newPose) {
	drawMap();
	drawPose(newPose);
    }

    public void drawPose(Pose newPose) {
	double x1 = newPose.getX() * MULTIPLIER;
	double y1 = (mapH - newPose.getY()) * MULTIPLIER;

	double deg = newPose.getHeading();
	double rad = Math.toRadians(-deg);

	double hyp = 20;
	double x2 = Math.cos(rad) * hyp + x1;
	double y2 = Math.sin(rad) * hyp + y1;

	gc.setFill(Color.RED);
	gc.strokeLine(x1, y1, x2, y2);

	gc.fillOval(x1-7.5, y1-7.5, 15, 15);
    }

    public void drawMap() {
	Rectangle rectangle = map.getBoundingRect();
	double w = rectangle.getMaxX() * MULTIPLIER;
	double h = rectangle.getMaxY() * MULTIPLIER;
	gc.setFill(Color.WHITE);
	gc.fillRect(0, 0, w, h);

	gc.setFill(Color.BLACK);
	for (Line l : map.getLines()) {
	    gc.strokeLine(l.x1 * MULTIPLIER, l.y1 * MULTIPLIER, l.x2 * MULTIPLIER, l.y2 * MULTIPLIER);
	}
    }
}
