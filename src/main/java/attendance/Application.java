package attendance;

import attendance.controller.CrewsController;

public class Application {
    public static void main(String[] args) {
        CrewsController controller = new CrewsController();
        controller.run();
    }
}
