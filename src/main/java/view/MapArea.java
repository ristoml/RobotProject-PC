package view;

import controller.IRobotController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lejos.robotics.navigation.DestinationUnreachableException;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.pathfinding.ShortestPathFinder;

/**
* A pane that contains a @{@link Map} and buttons for to use with navigation mode.
* Navigation mode means a mode, where user can click waypoints to the map, that can be sent
* to the robot by clicking the "go"-button.
*/
public class MapArea extends Pane {

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
    private IRobotController controller;

    public MapArea(IRobotController controller, Map map, ToggleButton togglebtn) {
	this.controller = controller;
	this.map = map;
	this.navMode = false;

	this.pathFinder = new ShortestPathFinder(map.getMapFlipped());
	pathFinder.lengthenLines(20);
	this.wpPose = map.getCurrentPose();
	this.waypoints = new Path();
	
	navButtons = new HBox();
	clear = new Button("clear");
	undo = new Button("undo");
	go = new Button("go");

	navButtons.getChildren().addAll(clear, undo, go);
	navButtons.setAlignment(Pos.CENTER_RIGHT);
	this.getChildren().addAll(map);

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
		this.map.addWayPoint(wp);
		wpPose = waypoints.get((waypoints.size()-1)).getPose();

	    } catch (DestinationUnreachableException ex) {
		System.out.println("destination unr.");
	    }
	    System.out.println(waypoints);
	});

	clear.setOnMouseClicked(e -> {
	    this.map.clearWayPoints();
	    this.waypoints.clear();
	    System.out.println(waypoints);
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
	    this.map.undoWaypoint();
	    System.out.println(waypoints);
	});

	go.setOnMouseClicked(e -> {
	    if (!navMode) {
		return;
	    }
	    if (map.getWaypoints().isEmpty()) {
		return;
	    }
	    System.out.println("send:\n"+this.waypoints);
	    controller.sendWaypoints(this.waypoints);
	    this.waypoints.clear();
	    map.clearWayPoints();
	    this.toggleNavMode();
	    togglebtn.setSelected(false);
	});
	this.navButtons.setVisible(false);
    }

    /**
     * Hides or shows the buttons for navigation mode.
     */
    public void toggleNavMode() {
	this.navButtons.setVisible(!navButtons.isVisible());
	navMode = !navMode;
    }

    /**
     * @return true if navigation mode is on (buttons visible), false otherwise.
     */
    public boolean isNavMode() {
	return navMode;
    }

    /**
     * @return A HBox containing navigation mode controlling buttons.
     */
    public HBox getButtons() {
	return this.navButtons;
    }

}
