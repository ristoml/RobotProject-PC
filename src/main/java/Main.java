import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
	nu.pattern.OpenCV.loadShared();

	try {
	    Class.forName("org.mariadb.jdbc.Driver");
	    //Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException ex) {
	    System.err.println("Loading JBCD-driver failed. Database not supported.");
	}

	Application.launch(view.RobotGUI.class, args);
    }

}
