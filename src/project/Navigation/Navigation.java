package project.Navigation;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.PageController;
import project.Project;

public class Navigation {

    public static PageController subController;
    public static String Meeting = "Meeting";
    public static String MeetingWindow = "Meeting.fxml";
    public static String ViewMessage = "ViewMessage";
    public static String ViewMessageWindow = "ViewMessages.fxml";
    public static String SendMessage = "SendMessage";
    public static String SendMessageWindow = "SendMessages.fxml";
    public static String MovementLog = "MovementLog";
    public static String MovementLogWindow = "MovementLogs.fxml";
    public static String AssignTask = "AssignTask";
    public static String AssignTaskWindow = "AssignTask.fxml";
    public static String CreateTask = "CreateTask";
    public static String CreateTaskWindow = "CreateTask.fxml";
    public static String MonitorTask = "MonitorTask";
    public static String MonitorTaskWindow = "MonitorTask.fxml";
    public static String ViewAssignedTask = "ViewAssignedTask";
    public static String ViewAssignedTaskWindow = "ViewAssignedTask.fxml";
    public static String ViewStaffs = "ViewStaffs";
    public static String ViewStaffsWindow = "ViewStaffs.fxml";
    public static String ViewSubordinates = "ViewSubordinates";
    public static String ViewSubordinatesWindow = "ViewSubordinates.fxml";


    public Navigation() {
        subController = new PageController();
        subController.loadPage(AssignTask, AssignTaskWindow);
        subController.loadPage(ViewAssignedTask, ViewAssignedTaskWindow);
        subController.loadPage(CreateTask, CreateTaskWindow);
        subController.loadPage(MonitorTask, MonitorTaskWindow);
        subController.loadPage(Meeting, MeetingWindow);
        subController.loadPage(ViewMessage, ViewMessageWindow);
        subController.loadPage(SendMessage, SendMessageWindow);
        subController.loadPage(MovementLog, MovementLogWindow);
        subController.loadPage(ViewStaffs, ViewStaffsWindow);
        subController.loadPage(ViewSubordinates, ViewSubordinatesWindow);

    }


    public static void displaySubMenu(String subWindow) {


        subController.setPage(subWindow);

        Stage subMenu = new Stage();
        subMenu.setWidth(409);
        subMenu.setHeight(250);


        subMenu.initOwner(Project.window);
        subMenu.initModality(Modality.WINDOW_MODAL);
        subMenu.initStyle(StageStyle.UNDECORATED);

        subMenu.setX((((Stage) subMenu.getOwner()).getX() + (((Stage) subMenu.getOwner()).getWidth() / 2) - 170));
        subMenu.setY((((Stage) subMenu.getOwner()).getY() + (((Stage) subMenu.getOwner()).getHeight() / 2) - 147));

        Group root = new Group();
        root.getChildren().addAll(subController);

        Scene scene = new Scene(root);

        subMenu.setScene(scene);

        subMenu.showAndWait();

    }


    public static void closeCurrentSubMenu(Pane pane){
        Stage stage = (Stage) pane.getScene().getWindow();
//        messageContent.getChildren().clear();
        stage.close();
    }

}
