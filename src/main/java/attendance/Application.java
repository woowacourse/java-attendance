package attendance;

import attendance.controller.CrewsController;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {
        CrewsController controller = new CrewsController();

        LocalDate today = LocalDate.of(2024, 12, 16);
        controller.run(today);
    }
}
