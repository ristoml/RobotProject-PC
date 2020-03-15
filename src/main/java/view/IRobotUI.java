package view;

import javafx.scene.image.Image;
import lejos.robotics.navigation.Pose;

/**
 * User interface for communicating with the robot.
 */
public interface IRobotUI {
    /**
     * Method for controller to update UI's video.
     * @param image
     */
    void updateVideo(Image image);
    /**
     * Method for controller to update UI's map.
     * @param pose
     */
    void updateMap(Pose pose);
    /**
     * Method for controller to update a message shown for the user in the UI.
     * @param msg
     */
    void setMessage(String msg);
}
