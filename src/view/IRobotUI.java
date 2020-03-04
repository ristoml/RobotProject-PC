package view;

import javafx.scene.image.Image;
import lejos.robotics.navigation.Pose;

public interface IRobotUI {
    void updateVideo(Image image);
    void updateMap(Pose pose);
    void setErrorMessage(String msg);
}
