package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lejos.robotics.navigation.DestinationUnreachableException;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.pathfinding.ShortestPathFinder;

/**
* MapArea
*/
public class MapArea extends VBox {

    private static final int MULTIPLIER = 3;

    private ShortestPathFinder pathFinder;
    private Path waypoints;
    private Pose wpPose;
    private Map map;

    private HBox navButtons;
    private Button clear;
    private Button undo;
    private Button go;

    private boolean navMode;

    public MapArea(Map map) {
	this.map = map;
	this.navMode = true;

	this.pathFinder = new ShortestPathFinder(map.getMapFlipped());
	pathFinder.lengthenLines(10);
	this.wpPose = map.getCurrentPose();
	this.waypoints = new Path();
	
	navButtons = new HBox();
	clear = new Button("clear");
	undo = new Button("undo");
	go = new Button("go");

	navButtons.getChildren().addAll(clear, undo, go);
	navButtons.setAlignment(Pos.CENTER_RIGHT);
	this.getChildren().addAll(map, navButtons);

	map.setOnMouseClicked(e -> {
	    if (!navMode) {
		return;
	    }

	    try {		
		if (waypoints.isEmpty()) {
		    wpPose = map.getCurrentPose();
		}
		double h = map.originalMapHeight() * MULTIPLIER;
		double eventX = e.getX() / MULTIPLIER;
		double eventY = (h - e.getY()) / MULTIPLIER;

		Waypoint wp = new Waypoint(eventX, eventY);

		pathFinder.findRoute(wpPose, wp);

		waypoints.add(wp);
		wpPose = waypoints.get((waypoints.size()-1)).getPose();

		this.map.addWayPoint(wp);
	    } catch (DestinationUnreachableException ex) {
		System.out.println("destination unr.");
	    }
	    System.out.println(waypoints);
	});

	clear.setOnMouseClicked(e -> {
	    this.map.clearWayPoints();
	    this.waypoints.clear();
	});

	undo.setOnMouseClicked(e -> {
	    if (!navMode) {
		return;
	    }

	    if (waypoints.size() > 1) {
		waypoints.remove(waypoints.size()-1);
		wpPose = waypoints.get(waypoints.size()-1).getPose();
	    } else if (waypoints.size() == 1) {
		waypoints.remove(waypoints.size()-1);
		wpPose = map.getCurrentPose();
	    } else {

	    }
	    System.out.println(waypoints);
	    this.map.undoWaypoint();
	});

	go.setOnMouseClicked(e -> {
	    if (!navMode) {
		return;
	    }
	    // poistu nav modesta
	    // lähetä waypointsit
	    // nollaa waypoints-lista
	});
    }

    public void toggleNavMode() {
	navMode = !navMode;
    }
}
