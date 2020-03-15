package view;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lejos.robotics.geometry.Line;
import lejos.robotics.geometry.Rectangle;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

/**
 * Map represents an enlarged JavaFx {@link Canvas} image of a Lejos {@link LineMap}.
 * It is able to draw a position with direction, using Lejos' {@link Pose}-class.
 * It also includes a List of {@link Waypoint}s, which are automatically drawn to the map,
 * when {@link Waypoint}s are added or removed.
 *
 * @author Sami Arola
 * @author Risto Leivo
 */
public class Map extends Canvas {

    /**
     * A multiplier added to Map's dimensions in order enlarge it for the graphical user interface.
     */
    private static final int MULTIPLIER = 3;

    private LineMap map;
    private GraphicsContext gc = getGraphicsContext2D();
    private double mapH, mapW;

    private Pose currentPose;
    private ArrayList<Waypoint> waypoints;

    /**
     * Creates and draws a new Map based {@link LineMap} and {@link Pose}.<br>
     * The {@link LineMap} is stored with its y-axis flipped, since JavaFX {@link Canvas} uses<br>
     * a different coordinate system than Lejos {@link LineMap}.
     * @param map {@link LineMap}
     * @param initialPose starting position when a Map is constructed.
     */
    public Map(LineMap map, Pose initialPose) {
	this.map = map.flip();
	this.waypoints = new ArrayList<>();

	Rectangle rectangle = map.getBoundingRect();
	mapH = rectangle.getMaxY();
	mapW = rectangle.getMaxX();

	double w = rectangle.getMaxX() * MULTIPLIER;
	double h = rectangle.getMaxY() * MULTIPLIER;
	setHeight(h);
	setWidth(w);

	this.currentPose = initialPose;
	drawMap();
	drawPose(initialPose);
    }

    /**
     * {@link LineMap} is stored in the class with y-axis flipped.<br>
     * This method flips the y-axis to original state and returns a new LineMap as result.<br>
     * @return a new copy of the original LineMap.
     */
    public LineMap getMapFlipped() {
	return map.flip();
    }

    /**
     * {@link LineMap} is stored in the class with y-axis flipped.<br>
     * This method returns a new copy of the LineMap set to this Map-object.<br>
     * @return a new copy of the original LineMap with y-axis flipped.
     */
    public LineMap getMap() {
	Line[] lines = this.map.getLines();
	Rectangle rectangle = this.map.getBoundingRect();

	Line[] cloneLines = new Line[lines.length];
	for (int i = 0; i < lines.length; ++i) {
	    Line l = lines[i];
	    Line newL = new Line(l.x1, l.y1, l.x2, l.y2);
	    cloneLines[i] = newL;
	}
	Rectangle cloneRect =
	    new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

	LineMap newMap = new LineMap(cloneLines, cloneRect);
	return newMap;
    }

    /**
     * @return the current {@link Pose} object of this Map.
     */
    public Pose getCurrentPose() {
	return currentPose;
    }

    /**
     * @return the height of the original LineMap (no multiplier added)
     */
    public double originalMapHeight() {
	return mapH;
    }

    /**
     * @return the width of the original LineMap (no multiplier added)
     */
    public double originalMapWidth() {
	return mapW;
    }

    /**
     * Sets a new {@link Pose} as current position and redraws the Map with
     * the new position.
     * @param newPose {@link Pose} to represent the new position for robot.
     */
    public void redraw(Pose newPose) {
	currentPose = newPose;
	drawMap();
	drawPose(newPose);
    }

    /**
     * Adds a new waypoint to a list of waypoints, and redraws the map.
     * @param wp new waypoint.
     */
    public void addWayPoint(Waypoint wp) {
	waypoints.add(wp);
	drawWayPoints();
    }

    /**
     * Removes the most recent waypoint from a list of waypoints, and redraws the map.
     * Does nothing if the list is empty.
     */
    public void undoWaypoint() {
	if (!waypoints.isEmpty()) {
	    waypoints.remove(waypoints.size()-1);
	    drawWayPoints();
	}
    }

    /**
     * Removes all waypoints and redraws the map.
     */
    public void clearWayPoints() {
	waypoints.clear();
	Platform.runLater(() -> redraw(currentPose));
    }

    /**
     * Redraws the map, current position, and all waypoints.
     */
    public void drawWayPoints() {
	Platform.runLater(() -> {
	    redraw(currentPose);
	    for (int i = 0; i < waypoints.size(); ++i) {
		drawWayPoint(i);
	    }
	});
    }

    private void drawWayPoint(int i) {
	if (i >= 0 && i < waypoints.size()) {
	    Waypoint wp = this.waypoints.get(i);
	    double x = wp.getX() * MULTIPLIER;
	    double y = (mapH - wp.getY()) * MULTIPLIER;

	    gc.setFill(Color.RED);
	    gc.strokeText((i+1)+"", x, y);
	}
    }

    /**
     * Draws the current position set to this Map.
     * @param newPose
     */
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

    /**
     * Draws the {@link LineMap} set to this Map (no position or waypoints).
     */
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

    /*
     * @return the list of waypoints in this Map
     */
    public ArrayList<Waypoint> getWaypoints() {
	return this.waypoints;
    }
}
