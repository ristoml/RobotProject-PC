import javafx.application.Application;

/**
 * Main entry point for the application.<br>
 * Loads the OpenCV-library and launches {@link view.RobotGUI}.
 */
public class Main {

    /**
     * Loads the OpenCV-library and launches {@link view.RobotGUI}.
     * @param args
     */
    public static void main(String[] args) {
	nu.pattern.OpenCV.loadShared();

	try {
	    Class.forName("org.mariadb.jdbc.Driver");
	    // optional driver: Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException ex) {
	    System.err.println("Loading JBCD-driver failed. Database not supported.");
	}

	Application.launch(view.RobotGUI.class, args);
    }

}
