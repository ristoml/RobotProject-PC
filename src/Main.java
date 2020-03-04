import javafx.application.Application;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
	nu.pattern.OpenCV.loadShared();
	Application.launch(view.RobotGUI.class, args);
    }

}
